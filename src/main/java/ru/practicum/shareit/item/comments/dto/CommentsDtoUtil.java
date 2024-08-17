package ru.practicum.shareit.item.comments.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.comments.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentsDtoUtil {
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
                .commentText(comment.getCommentText())
                .item(comment.getItem())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

}
