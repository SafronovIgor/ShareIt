package ru.practicum.shareit.server.comments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.server.item.model.Item;
import ru.practicum.shareit.server.user.User;

import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String commentText;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_user_id")
    User author;

    @NotNull
    @Temporal(TIMESTAMP)
    @Column(name = "start_time_created")
    LocalDateTime created;
}
