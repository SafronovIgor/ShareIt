package ru.practicum.shareit.item.comments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.item.comments.dto.CommentsResponseDto;
import ru.practicum.shareit.item.comments.service.CommentService;

@Slf4j
@RestController
@RequestMapping(path = "/items/{itemId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final String OWNER_USER_ID = "X-Sharer-User-Id";
    private final CommentService commentService;

    @PostMapping
    public CommentsResponseDto commentPastBooking(
            @RequestHeader(value = OWNER_USER_ID) Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid CommentsRequestDto commentsRequestDto) {
        return commentService.commentPastBooking(userId, itemId, commentsRequestDto);
    }
}
