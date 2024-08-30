package ru.practicum.shareit.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.dto.CommentsRequestDto;

import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@Controller
@RequestMapping(path = "/items/{itemId}/comment")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentController {
    private final CommentClient commentClient;

    @PostMapping
    public ResponseEntity<Object> createComment(@RequestHeader(OWNER_USER_ID) Long userId,
                                                @PathVariable Long itemId,
                                                @RequestBody CommentsRequestDto commentsRequestDto) {
        log.info("Received a request to create a comment for item with id: {} by user with id: {}", itemId, userId);
        return commentClient.createComment(userId, itemId, commentsRequestDto);
    }
}
