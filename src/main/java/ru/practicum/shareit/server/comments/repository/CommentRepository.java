package ru.practicum.shareit.server.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.server.comments.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByItemId(Long itemId);
}
