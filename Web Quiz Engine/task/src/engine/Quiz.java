package engine;

public class Quiz {

    private String title;
    private String text;
    private String[] options;
    private static final int indexOfCorrectAnswer = 2;

    public Quiz(String title, String text, String[] options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrectAnswer(int answer) {
        return answer == indexOfCorrectAnswer;
    }
}
