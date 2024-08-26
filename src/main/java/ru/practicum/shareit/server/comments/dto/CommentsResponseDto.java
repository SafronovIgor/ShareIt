package ru.practicum.shareit.server.comments.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.server.item.model.Item;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentsResponseDto {
    Long id;

    String text;

    Item item;

    String authorName;

    String created;

}
