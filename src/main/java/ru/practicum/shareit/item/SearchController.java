package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items/search")
@RequiredArgsConstructor
public class SearchController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> search(@RequestParam String text) {
        log.info("Received a search request for available items with text '{}'", text);
        return itemService.searchAvailableItems(text);
    }
}
