package ru.practicum.shareit.server.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.server.booking.Booking;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.request.ItemRequest;
import ru.practicum.shareit.server.user.User;

import java.util.List;

@UtilityClass
public class ItemMapper {
    public Item toItem(ItemCreationRequestDto itemDto, User ownerItem, ItemRequest itemRequest) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(ownerItem)
                .request(itemRequest)
                .build();
    }

    public ItemResponseDto toItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
    }

    public ItemResponseDto toItemWithCommentsAndBookingsResponseDto(
            Item item,
            Booking lastBooking,
            Booking nextBooking,
            List<String> comments) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(lastBooking)
                .nextBooking(nextBooking)
                .comments(comments)
                .build();
    }
}
