package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class QuizController {

    List<Quiz> quizzes = new ArrayList<>();

    private int getNextId() {
        return quizzes.size() + 1;
    }

    @PostMapping(path = "api/quizzes", consumes = "application/json")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        quiz.setId(getNextId());
        quizzes.add(quiz);
        return quiz;
    }

    @GetMapping(path = "api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        if (id < 0 || id > quizzes.size()){
            throw new UserNotFoundException("id: " + id);
        }

        return quizzes.get(id - 1);
    }

    @GetMapping(path = "api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    @PostMapping(path = "api/quizzes/{id}/solve")
    public AnswerQuiz solveQuiz(@PathVariable int id, @RequestParam(name = "answer") int answer) {
        if (id < 0 || id > quizzes.size()){
            throw new UserNotFoundException("id: " + id);
        }

        Quiz quiz = quizzes.get(id - 1);

        System.out.println(quiz.getAnswer());
        System.out.println(answer);

        if (quiz.isCorrectAnswer(answer)) return AnswerQuiz.CORRECT_ANSWER;
        else return AnswerQuiz.WRONG_ANSWER;
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String message)
    {
        super(message);
    }
}