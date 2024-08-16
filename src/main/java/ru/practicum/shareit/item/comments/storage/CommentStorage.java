package ru.practicum.shareit.item.comments.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.comments.model.Comment;

import java.util.List;

public interface CommentStorage extends JpaRepository<Comment, Long> {

    List<Comment> findAllByItemId(Long itemId);
}
