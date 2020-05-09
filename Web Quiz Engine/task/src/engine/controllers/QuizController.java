package engine.controllers;

import engine.models.*;
import engine.repositories.QuizCompletedRepository;
import engine.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizCompletedRepository quizCompletedRepository;

    @GetMapping(path = "{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quizzes.get();
    }

    @GetMapping
    public Page<Quiz> getQuizzes(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return quizRepository.findAll(pageable);
    }

    @GetMapping("/completed")
    public Page<QuizCompleted> getCompletedQuizzes(@RequestParam int page, @AuthenticationPrincipal User user) {
        Pageable pageable = PageRequest.of(page, 10);

        return quizCompletedRepository.findAllCompletedQuizzesWithPagination(user.getId(), pageable);
    }


    @PostMapping(consumes = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal User user) {
        quiz.setAuthor(user);
        return quizRepository.save(quiz);
    }

    @PostMapping(path = "{id}/solve")
    public AnswerQuiz solveQuiz(@PathVariable int id, @RequestBody Guess guess, @AuthenticationPrincipal User user) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Quiz quiz = quizzes.get();
        if (quiz.isCorrectAnswer(guess.getAnswer())) {
            createQuizCompleted(user, quiz);

            return AnswerQuiz.CORRECT_ANSWER;
        } else {
            return AnswerQuiz.WRONG_ANSWER;
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable int id, @AuthenticationPrincipal User user) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            if (quiz.get().getAuthor().equals(user)) {
                quizRepository.delete(quiz.get());
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private void createQuizCompleted(@AuthenticationPrincipal User user, Quiz quiz) {
        ArrayList<QuizCompleted> completions = new ArrayList<QuizCompleted>();
        completions.addAll(quiz.getQuizCompleteds());

        QuizCompleted quizCompleted = new QuizCompleted();
        quizCompleted.setQuiz(quiz);
        quizCompleted.setAuthor(user);
        quizCompleted.setCompletedAt(LocalDateTime.now());
        quizCompleted = quizCompletedRepository.save(quizCompleted);

        completions.add(quizCompleted);
        quiz.setQuizCompleteds(completions);
        quizRepository.save(quiz);
    }

}