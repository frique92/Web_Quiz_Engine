package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    @NotNull(message = "Title is mandatory!")
    private String title;

    @Column
    @NotNull(message = "Text is mandatory!")
    private String text;

    @Column
    @NotNull()
    @Size(min = 2)
    private String[] options;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    private User author;

    @Column
    @JsonIgnore
    private LocalDateTime completedAt;

    @Column
    @JsonIgnore
    private boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

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

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public boolean isCorrectAnswer(int[] answer) {
        int[] currentAnswer = this.answer == null ? new int[0] : this.answer;
        return Arrays.equals(currentAnswer, answer);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + Arrays.toString(options) +
                ", answer=" + Arrays.toString(answer) +
                ", author=" + author +
                ", completedAt=" + completedAt +
                ", completed=" + completed +
                '}';
    }
}
