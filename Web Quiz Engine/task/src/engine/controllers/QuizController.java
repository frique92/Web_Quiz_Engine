package engine.controllers;

import engine.models.User;
import engine.repositories.QuizRepository;
import engine.models.AnswerQuiz;
import engine.models.Guess;
import engine.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

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
    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    @PostMapping(path = "{id}/solve")
    public AnswerQuiz solveQuiz(@PathVariable int id, @RequestBody Guess guess) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Quiz quiz = quizzes.get();
        if (quiz.isCorrectAnswer(guess.getAnswer())) return AnswerQuiz.CORRECT_ANSWER;
        else return AnswerQuiz.WRONG_ANSWER;
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

}