package ru.practicum.shareit.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.items.repository.ItemRepository;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemResponseDto;
import ru.practicum.shareit.requests.dto.RequestMapper;
import ru.practicum.shareit.requests.repository.ItemRequestRepository;
import ru.practicum.shareit.users.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponseDto createItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("User with id " + userId + " not found"));
        var request = RequestMapper.toItemRequest(itemRequestDto, user);
        var itemRequest = Optional.of(itemRequestRepository.save(request)).orElseThrow(RuntimeException::new);

        return RequestMapper.toItemResponseDto(itemRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getItemRequests(Long userId) {
        return itemRequestRepository
                .findAllByRequestorId(userId)
                .stream()
                .map(RequestMapper::toItemResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getAllItemRequestsOtherUsers(Long userId) {
        return itemRequestRepository
                .findAllByRequestorIdIsNot(userId)
                .stream()
                .map(RequestMapper::toItemResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto getItemRequestsById(Long userId, Long requestId) {
        var itemsByUser = itemRepository.findAllByOwnerId(userId);
        var itemRequest = itemRequestRepository.findById(requestId).orElseThrow(
                () -> new ObjectNotFoundException("Item request with id " + requestId + " not found"));
        return RequestMapper.toItemResponseDto(itemRequest, itemsByUser);
    }
}
