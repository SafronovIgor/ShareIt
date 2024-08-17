package ru.practicum.shareit.item.comments.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentsResponseDto {
    Long id;
    String commentText;
    Item item;
    String authorName;
    LocalDateTime created;
}
