package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

public class Quiz {

    private int id;

    @NotNull(message = "Title is mandatory!")
    private String title;

    @NotNull(message = "Text is mandatory!")
    private String text;

    @NotNull(message = "Option is mandatory!")
    @Size(min = 2)
    private String[] options;

    @JsonIgnore
    private int[] answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    @JsonIgnore
    public int[] getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public boolean isCorrectAnswer(int[] answer) {
        return Arrays.equals(this.answer, answer);
    }
}
