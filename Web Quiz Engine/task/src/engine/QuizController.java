package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @PostMapping(path = "api/quizzes", consumes = "application/json")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @GetMapping(path = "api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return quizzes.get();
    }

    @GetMapping(path = "api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    @PostMapping(path = "api/quizzes/{id}/solve")
    public AnswerQuiz solveQuiz(@PathVariable int id, @RequestBody Guess guess) {
        Optional<Quiz> quizzes = quizRepository.findById(id);
        if (quizzes.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Quiz quiz = quizzes.get();
        if (quiz.isCorrectAnswer(guess.getAnswer())) return AnswerQuiz.CORRECT_ANSWER;
        else return AnswerQuiz.WRONG_ANSWER;
    }
}