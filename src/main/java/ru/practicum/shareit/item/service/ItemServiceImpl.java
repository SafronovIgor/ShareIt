package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotOwnerException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.*;
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

    @Override
    public ItemCreationResponseDto createItem(Long userId, ItemCreationRequestDto itemDto) {
        var ownerItem = userStorage.findById(userId).orElseThrow(() -> {
            log.warn("Error creating user, user with id {} was not found.", userId);
            return new ObjectNotFoundException(String.format("User with the id '%s' does not exist", userId));
        });
        var item = Optional.of(itemStorage.save(ItemDtoUtil.toItem(itemDto, ownerItem)))
                .orElseThrow(RuntimeException::new);
        return ItemCreationResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .ownerId(userId)
                .build();
    }

    @Override
    public ItemUpdateResponseDto updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto) {
        var item = itemStorage.findById(itemId)
                .map(i -> {
                    var ownerItem = i.getOwner();
                    if (ownerItem != null && !(ownerItem.getId() == userId)) {
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
                    log.warn("Error updating item, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
        return ItemUpdateResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    @Override
    public ItemUpdateResponseDto getItemById(Long itemId) {
        var item = itemStorage.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("Error getting item, item with id {} was not found", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
        return ItemUpdateResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    @Override
    public List<ItemUpdateResponseDto> getAllItemByIdOwner(Long userId) {
        return itemStorage.findAllByOwnerId(userId).stream()
                .map(item -> ItemUpdateResponseDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .available(item.getAvailable())
                        .build())
                .toList();
    }

    @Override
    public List<Item> searchAvailableItems(String textForSearch) {
        return itemStorage.searchAvailableItems(textForSearch);
    }
}
