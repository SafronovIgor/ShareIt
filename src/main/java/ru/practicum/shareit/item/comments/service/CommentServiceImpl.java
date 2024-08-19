package ru.practicum.shareit.item.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.enums.Status;
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
    @Transactional(rollbackFor = Exception.class)
    public CommentsResponseDto commentPastBooking(Long userId, Long itemId, CommentsRequestDto commentsRequestDto) {
        log.info("Commenting past booking for user with id {} for item with id {}", userId, itemId);
        var user = userStorage.findById(userId).orElseThrow(() -> {
            log.error("Error commenting past booking, user with id {} was not found.", userId);
            return new ObjectNotFoundException("User not found id: " + userId);
        });
        var item = itemStorage.findById(itemId).orElseThrow(() -> {
            log.error("Error commenting past booking, item with id {} was not found.", itemId);
            return new ObjectNotFoundException("Item not found id: " + itemId);
        });

        if (bookingStorage.findAllByBookerIdAndItemIdAndStatusAndEndBefore(userId, itemId, Status.APPROVED,
                LocalDateTime.now()).isEmpty()) {
            throw new RuntimeException("Пользователь с id = " + userId + " не получал item с id = " + itemId);
        }

        var comment = CommentsDtoUtil.toComment(commentsRequestDto, user, item);
        commentStorage.save(comment);
        return CommentsDtoUtil.toCommentsResponseDto(comment);
    }

}
