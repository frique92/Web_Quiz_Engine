package engine.controllers;

import engine.models.*;
import engine.repositories.QuizRepository;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private QuizService quizService;

    @PostMapping(consumes = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal User user) {
        quiz.setAuthor(user);
        return quizRepository.save(quiz);
    }

    @GetMapping(path = "{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quizzes.get();
    }

    @GetMapping
    public Page<Quiz> getQuizzes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String SortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
//        List<Quiz> list = quizService.getAllQuizzes(pageNo, pageSize, SortBy);
//
//        return new ResponseEntity<>(list, HttpStatus.OK);
        return quizRepository.findAll(pageable);
    }

    @PostMapping(path = "{id}/solve")
    public AnswerQuiz solveQuiz(@PathVariable int id, @RequestBody Guess guess) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Quiz quiz = quizzes.get();
        if (quiz.isCorrectAnswer(guess.getAnswer())) {
            quiz.setCompleted(true);
            quiz.setCompletedAt(LocalDateTime.now());

            quizRepository.save(quiz);

            return AnswerQuiz.CORRECT_ANSWER;
        }
        else {
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

    @GetMapping("/completed")
    public Page<QuizCompleted> getCompletedQuizzes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @AuthenticationPrincipal User user) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<QuizCompleted> quiz = quizRepository.findAllCompletedQuizzesWithPagination(user.getId(), pageable);
        System.out.println(quiz);

        return quiz;

    }

}