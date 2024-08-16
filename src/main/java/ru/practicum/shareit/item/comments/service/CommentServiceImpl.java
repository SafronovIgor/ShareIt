package ru.practicum.shareit.item.comments.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.comments.dto.CommentsDtoUtil;
import ru.practicum.shareit.item.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.item.comments.dto.CommentsResponseDto;
import ru.practicum.shareit.item.comments.storage.CommentStorage;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentStorage commentStorage;
    private final BookingStorage bookingStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    @Transactional
    public CommentsResponseDto commentPastBooking(Long userId, Long itemId, CommentsRequestDto commentsRequestDto) {

        if (!bookingStorage.existsByItemIdAndBookerId(itemId, userId)) {
            throw new IllegalStateException("No booking record found for the item by the user.");
        }

        List<Booking> bookings = bookingStorage.findByItemIdAndBookerIdOrderByEndTimeDesc(itemId, userId);
        Booking booking = bookings.getFirst();

        if (!booking.getEnd().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("The booking has not yet ended.");
        }

        var item = itemStorage.findById(itemId).orElseThrow(
                () -> new ObjectNotFoundException("Item not found id: " + itemId));
        var user = userStorage.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("User not found id: " + userId));
        var comment = CommentsDtoUtil.toComment(commentsRequestDto, user, item);

        commentStorage.save(comment);
        return CommentsDtoUtil.toCommentsResponseDto(comment);
    }
}
