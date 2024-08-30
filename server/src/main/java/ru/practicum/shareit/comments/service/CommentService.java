package ru.practicum.shareit.comments.service;

import ru.practicum.shareit.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.comments.dto.CommentsResponseDto;

public interface CommentService {
    CommentsResponseDto commentPastBooking(Long userId, Long itemId, CommentsRequestDto commentsRequestDto);
}