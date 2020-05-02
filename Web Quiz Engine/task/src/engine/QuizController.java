package engine;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class QuizController {

    List<Quiz> quizzes = new ArrayList<>();

    private int getNextId() {
        return quizzes.size() + 1;
    }

    @PostMapping(path = "api/quiz", consumes = "application/json")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        quiz.setId(getNextId());
        quizzes.add(quiz);
        return quiz;
    }

    @GetMapping(path = "api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return quizzes.get(id - 1);
    }

    @GetMapping(path = "api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizzes;
    }
}
