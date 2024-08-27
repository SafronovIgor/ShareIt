package ru.practicum.shareit.search;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Validated
@Controller
@RequestMapping(path = "/items/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchClient searchClient;

    @GetMapping
    public ResponseEntity<Object> search(@NotBlank @RequestParam String text) {
        return searchClient.search(text);
    }
}
