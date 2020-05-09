package engine.repositories;

import engine.models.Quiz;
import engine.models.QuizCompleted;
import engine.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query(
            value = "SELECT * FROM quizzes LEFT JOIN user ON quizzes.author_id = user.id " +
                    "WHERE quizzes.completed = TRUE AND user.id = ? ORDER BY completed_at DESC",
            nativeQuery = true)
    Page<QuizCompleted> findAllCompletedQuizzesWithPagination(int id, Pageable pageable);

}