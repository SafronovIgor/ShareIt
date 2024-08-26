package ru.practicum.shareit.items.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.items.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
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

}
