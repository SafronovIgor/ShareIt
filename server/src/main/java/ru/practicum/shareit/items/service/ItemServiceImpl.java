package ru.practicum.shareit.items.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.bookings.repository.BookingRepository;
import ru.practicum.shareit.comments.Comment;
import ru.practicum.shareit.comments.repository.CommentRepository;
import ru.practicum.shareit.exception.NotOwnerException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.items.Item;
import ru.practicum.shareit.items.dto.ItemCreationRequestDto;
import ru.practicum.shareit.items.dto.ItemMapper;
import ru.practicum.shareit.items.dto.ItemResponseDto;
import ru.practicum.shareit.items.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.items.repository.ItemRepository;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public ItemResponseDto createItem(Long userId, ItemCreationRequestDto itemDto) {
        log.info("Creating an item for user with id {}", userId);
        var ownerItem = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Error creating user, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("User with the id '%s' does not exist", userId));
        });

        ItemRequest itemRequest = null;
        if (itemDto.getRequestId() != null) {
            itemRequest = itemRequestRepository.findById(itemDto.getRequestId()).orElseThrow(RuntimeException::new);
        }

        var item = Optional.of(itemRepository.save(ItemMapper.toItem(itemDto, ownerItem, itemRequest)))
                .orElseThrow(RuntimeException::new);

        return ItemMapper.toItemResponseDto(item);
    }

    @Override
    public ItemResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto) {
        log.info("Updating item with id {} for user with id {}", itemId, userId);
        var item = itemRepository.findById(itemId)
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
                    return itemRepository.save(i);
                })
                .orElseThrow(() -> {
                    log.error("Error updating item, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
        return ItemMapper.toItemResponseDto(item);
    }

    @Override
    public ItemResponseDto getItemById(Long itemId, Long userId) {
        log.info("Getting item with id {} for user with id {}", itemId, userId);
        var item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Error getting item, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
        var lastBooking = Optional.ofNullable(bookingRepository.findLastBooking(itemId, userId));
        var nextBooking = Optional.ofNullable(bookingRepository.findNextBooking(itemId, userId));
        var areBookingsEqual = lastBooking.equals(nextBooking);
        var comments = commentRepository.findAllByItemId(itemId)
                .stream()
                .map(Comment::getCommentText)
                .toList();

        return ItemMapper.toItemWithCommentsAndBookingsResponseDto(
                item,
                areBookingsEqual ? null : lastBooking.orElse(null),
                areBookingsEqual ? null : nextBooking.orElse(null),
                comments);
    }

    @Override
    public List<ItemResponseDto> getAllItemByIdOwner(Long userId) {
        log.info("Getting all items for user with id {}", userId);
        return itemRepository.findAllByOwnerId(userId)
                .stream()
                .map(ItemMapper::toItemResponseDto)
                .toList();
    }

    @Override
    public List<Item> searchAvailableItems(String textForSearch) {
        log.info("Searching for available items with text '{}'", textForSearch);
        return itemRepository.searchAvailableItems(textForSearch);
    }

    public boolean isOwnerItem(Item item, Long ownerId) {
        return item.getOwner().getId().equals(ownerId);
    }

}
