package engine;

public class AnswerQuiz {

    public static AnswerQuiz CORRECT_ANSWER = new AnswerQuiz(true, "Congratulations, you're right!");
    public static AnswerQuiz WRONG_ANSWER = new AnswerQuiz(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    public AnswerQuiz(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

}
