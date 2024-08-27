package ru.practicum.shareit.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.shareit.items.dto.ItemRequestDto;

import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(value = OWNER_USER_ID) Long userId,
                                             @RequestBody @Valid ItemRequestDto itemDto) {
        return itemClient.createItem(userId, itemDto);
    }
}
