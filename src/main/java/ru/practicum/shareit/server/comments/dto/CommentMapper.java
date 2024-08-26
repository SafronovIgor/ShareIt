package ru.practicum.shareit.server.comments.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.server.comments.model.Comment;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.User;

import java.time.LocalDateTime;

import static ru.practicum.shareit.gateway.src.main.java.Constants.FORMATTER;

@UtilityClass
public class CommentMapper {
    public Comment toComment(CommentsRequestDto commentsRequestDto, User user, Item item) {
        return Comment.builder()
                .commentText(commentsRequestDto.getText())
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();
    }

    public CommentsResponseDto toCommentsResponseDto(Comment comment) {
        return CommentsResponseDto.builder()
                .id(comment.getId())
                .text(comment.getCommentText())
                .item(comment.getItem())
                .authorName(comment.getAuthor().getName())
                .created(formatDateTime(comment.getCreated()))
                .build();
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
