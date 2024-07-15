package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotOwnerException;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.ItemCreationRequestDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public Item createItem(Long userId, ItemCreationRequestDto itemDto) {
        itemDto.setOwner(userService.getUserById(userId));
        return Optional.of(itemStorage.save(ItemCreationRequestDto.toItem(itemDto)))
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Item updateItemById(Long userId, Long itemId, ItemUpdateRequestDto itemDto) {
        return itemStorage.findById(itemId)
                .map(item -> {
                    var ownerItem = item.getOwner();
                    if (ownerItem != null && !(ownerItem.getId() == userId)) {
                        log.warn("При обновлении вещи произошла ошибка, попытка редактирования вещи не владельцем");
                        throw new NotOwnerException();
                    }
                    if (itemDto.getName() != null && !item.getName().equals(itemDto.getName())) {
                        item.setName(itemDto.getName());
                    }
                    if (itemDto.getDescription() != null && !item.getDescription().equals(itemDto.getDescription())) {
                        item.setDescription(itemDto.getDescription());
                    }
                    if (itemDto.getAvailable() != null && !item.getAvailable() == itemDto.getAvailable()) {
                        item.setAvailable(itemDto.getAvailable());
                    }
                    itemDto.setOwner(item.getOwner());
                    itemDto.setId(itemId);
                    return itemStorage.save(item);
                })
                .orElseThrow(() -> {
                    log.warn("При обновлении вещи произошла ошибка, вещь с id {} не была найден",
                            itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemStorage.findById(itemId)
                .orElseThrow(() -> {
                    log.warn("При получении вещи произошла ошибка, вещь с id {} не была найден", itemId);
                    return new ObjectNotFoundException(String.format("Item with the id '%s' does not exist", itemId));
                });
    }

    @Override
    public List<Item> getAllItemByIdOwner(Long userId) {
        return itemStorage.findAllByOwnerId(userId);
    }
}
