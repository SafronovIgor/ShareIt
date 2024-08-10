package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage extends JpaRepository<Item, Long> {
    List<Item> findAllByOwnerId(Long ownerId);

    @Query("""
            SELECT
                items
            FROM
                Item AS items
            WHERE
                (LOWER(items.name) LIKE LOWER(CONCAT('%', :text, '%'))
                    OR LOWER(items.description) LIKE LOWER(CONCAT('%', :text, '%')))
                AND items.available = TRUE
            """)
    List<Item> searchAvailableItems(@Param("text") String text);

    boolean existsByIdAndAvailableTrue(Long id);
}
