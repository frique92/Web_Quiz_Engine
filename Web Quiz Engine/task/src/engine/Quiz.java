package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "quizzes")
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

    @Id
    @GeneratedValue()
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column
    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    @JsonIgnore
    @Column
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
