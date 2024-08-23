package ru.practicum.shareit.item.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.comments.dto.CommentMapper;
import ru.practicum.shareit.item.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.item.comments.dto.CommentsResponseDto;
import ru.practicum.shareit.item.comments.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public CommentsResponseDto commentPastBooking(Long userId, Long itemId, CommentsRequestDto commentsRequestDto) {
        log.info("Commenting past booking for user with id {} for item with id {}", userId, itemId);
        var user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Error commenting past booking, user with id {} was not found.", userId);
            return new ObjectNotFoundException("User not found id: " + userId);
        });
        var item = itemRepository.findById(itemId).orElseThrow(() -> {
            log.error("Error commenting past booking, item with id {} was not found.", itemId);
            return new ObjectNotFoundException("Item not found id: " + itemId);
        });

        if (bookingRepository.findAllByBookerIdAndItemIdAndStatusAndEndBefore(userId, itemId, Status.APPROVED,
                LocalDateTime.now()).isEmpty()) {
            throw new RuntimeException("Пользователь с id = " + userId + " не получал item с id = " + itemId);
        }

        var comment = CommentMapper.toComment(commentsRequestDto, user, item);
        commentRepository.save(comment);
        return CommentMapper.toCommentsResponseDto(comment);
    }

}
