package engine;

public class AnswerQuiz {

    private final boolean success;
    private final String feedback;

    public AnswerQuiz(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
