package ru.yandex.practicum.comment.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAuthorId(int userId, Pageable pageable);

    List<Comment> findByEventOrderByCreatedDesc(int eventId);
}
