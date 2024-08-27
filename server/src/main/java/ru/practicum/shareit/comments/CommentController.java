package ru.practicum.shareit.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.comments.dto.CommentsResponseDto;
import ru.practicum.shareit.comments.service.CommentService;

import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@Slf4j
@RestController
@RequestMapping(path = "/items/{itemId}/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommentsResponseDto commentPastBooking(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                                  @PathVariable Long itemId,
                                                  @RequestBody CommentsRequestDto commentsRequestDto) {
        log.info("Received request to comment past booking for user with id {} for item with id {}", userId, itemId);
        return commentService.commentPastBooking(userId, itemId, commentsRequestDto);
    }
}
