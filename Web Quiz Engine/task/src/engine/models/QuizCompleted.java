package engine.models;

import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "quizzes")
public class QuizCompleted {
    private int id;
    private LocalDateTime completedAt;

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
}
