package ru.practicum.shareit.item.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.exception.BookingTimeException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.comments.dto.CommentsDtoUtil;
import ru.practicum.shareit.item.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.item.comments.dto.CommentsResponseDto;
import ru.practicum.shareit.item.comments.storage.CommentStorage;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentStorage commentStorage;
    private final BookingStorage bookingStorage;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public CommentsResponseDto commentPastBooking(Long userId, Long itemId, CommentsRequestDto commentsRequestDto) {
        var user = userStorage.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("User not found id: " + userId));
        var item = itemStorage.findById(itemId).orElseThrow(
                () -> new ObjectNotFoundException("Item not found id: " + itemId));

        if (bookingStorage.findAllByBookerIdAndItemIdAndStatusAndEndBefore(userId, itemId, Status.APPROVED,
                LocalDateTime.now()).isEmpty()) {
            throw new BookingTimeException("Пользователь с id = " + userId + " не получал item с id = " + itemId);
        }

        var comment = CommentsDtoUtil.toComment(commentsRequestDto, user, item);
        commentStorage.save(comment);
        return CommentsDtoUtil.toCommentsResponseDto(comment);
    }
}
