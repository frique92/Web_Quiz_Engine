package engine;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class QuizController {

    ArrayList<Quiz> quizzes = new ArrayList<>();

    public QuizController() {

        String title = "The Java Logo";
        String text = "What is depicted on the Java logo?";
        String[] options = new String[]{
                "Robot",
                "Tea leaf",
                "Cup of coffee",
                "Bug"
        };

        Quiz quiz = new Quiz(title, text, options);
        quizzes.add(quiz);
    }

    @GetMapping(path = "api/quiz")
    public Quiz getQuiz() {
        return quizzes.get(0);
    }

    @PostMapping(path = "api/quiz")
    public AnswerQuiz solveQuiz(@RequestParam Map<String, String> param) {

        int answer = Integer.parseInt(param.get("answer"));

        boolean success = quizzes.get(0).isCorrectAnswer(answer);
        String feedback = "";
        if (success) {
            feedback = "Congratulations, you're right!";
        } else {
            feedback = "Wrong answer! Please, try again.";
        }

        return new AnswerQuiz(success, feedback);
    }

}
