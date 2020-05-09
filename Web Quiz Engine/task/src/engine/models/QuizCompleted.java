package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes_completed")
public class QuizCompleted {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @JsonIgnore
    @ManyToOne
    private Quiz quiz;

    @Column
    private LocalDateTime completedAt;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    private User author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @JsonProperty("id")
    private Integer getQuizId() {
        return quiz.getId();
    }

    @Override
    public String toString() {
        return "QuizCompleted{" +
                "id=" + id +
                ", quiz=" + quiz +
                ", completedAt=" + completedAt +
                ", author=" + author +
                '}';
    }
}
