package ru.practicum.shareit.server.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.server.request.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequestorIdIsNot(Long itemId);

    List<ItemRequest> findAllByRequestorId(Long userId);
}
