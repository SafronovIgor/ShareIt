package ru.practicum.shareit.server.comments.service;

import ru.practicum.shareit.server.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.server.comments.dto.CommentsResponseDto;

public interface CommentService {
    CommentsResponseDto commentPastBooking(Long userId, Long itemId, CommentsRequestDto commentsRequestDto);
}
