package ru.practicum.shareit.item.comments.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.comments.model.Comment;

public interface CommentStorage extends JpaRepository<Comment, Long> {
}
