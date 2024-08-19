package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.exception.NotOwnerException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.comments.model.Comment;
import ru.practicum.shareit.item.comments.storage.CommentStorage;
import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemDtoUtil;
import ru.practicum.shareit.item.dto.ItemResponseDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;
    private final CommentStorage commentStorage;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemResponseDto createItem(Long userId, ItemCreationRequestDto itemDto) {
        log.info("Creating an item for user with id {}", userId);
        var ownerItem = userStorage.findById(userId).orElseThrow(() -> {
            log.warn("Error creating user, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("User with the id '%s' does not exist", userId));
        });
        var item = Optional.of(itemStorage.save(ItemDtoUtil.toItem(itemDto, ownerItem)))
                .orElseThrow(RuntimeException::new);
        return ItemDtoUtil.toItemResponseDto(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto) {
        log.info("Updating item with id {} for user with id {}", itemId, userId);
        var item = itemStorage.findById(itemId)
                .map(i -> {
                    var ownerItem = i.getOwner();
                    if (ownerItem != null && !(ownerItem.getId().equals(userId))) {
                        log.warn("Error updating i, attempted edit by non-owner");
                        throw new NotOwnerException();
                    }
                    if (itemDto.getName() != null
                        && !itemDto.getName().isBlank()
                        && !i.getName().equals(itemDto.getName())) {
                        i.setName(itemDto.getName());
                    }
                    if (itemDto.getDescription() != null
                        && !itemDto.getDescription().isBlank()
                        && !i.getDescription().equals(itemDto.getDescription())) {
                        i.setDescription(itemDto.getDescription());
                    }
                    if (itemDto.getAvailable() != null && !i.getAvailable() == itemDto.getAvailable()) {
                        i.setAvailable(itemDto.getAvailable());
                    }
                    return itemStorage.save(i);
                })
                .orElseThrow(() -> {
                    log.error("Error updating item, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
        return ItemDtoUtil.toItemResponseDto(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public ItemResponseDto getItemById(Long itemId, Long userId) {
        log.info("Getting item with id {} for user with id {}", itemId, userId);
        var item = itemStorage.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Error getting item, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
        var lastBooking = Optional.of(bookingStorage.findLastBooking(itemId, userId)).orElseThrow(
                () -> {
                    log.warn("Error getting last booking, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Booking with the id '%s' does not exist", itemId));
                });
        var nextBooking = Optional.of(bookingStorage.findNextBooking(itemId, userId)).orElseThrow(
                () -> {
                    log.warn("Error getting next booking, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Booking with the id '%s' does not exist", itemId));
                });

        if (userId != null) {
            if (lastBooking.equals(nextBooking)) {
                lastBooking = null;
                nextBooking = null;
            }

            var comments = commentStorage.findAllByItemId(itemId)
                    .stream()
                    .map(Comment::getCommentText)
                    .toList();

            return ItemDtoUtil.toItemWithCommentsAndBookingsResponseDto(
                    item,
                    lastBooking,
                    nextBooking,
                    comments);
        } else {
            return ItemDtoUtil.toItemResponseDto(item);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getAllItemByIdOwner(Long userId) {
        log.info("Getting all items for user with id {}", userId);
        return itemStorage.findAllByOwnerId(userId)
                .stream()
                .map(ItemDtoUtil::toItemResponseDto)
                .toList();
    }

    @Override
    public List<Item> searchAvailableItems(String textForSearch) {
        log.info("Searching for available items with text '{}'", textForSearch);
        return itemStorage.searchAvailableItems(textForSearch);
    }

    public boolean isOwnerItem(Item item, Long ownerId) {
        return item.getOwner().getId().equals(ownerId);
    }

}
