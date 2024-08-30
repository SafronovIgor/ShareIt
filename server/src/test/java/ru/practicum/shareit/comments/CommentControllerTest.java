package ru.practicum.shareit.comments;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.shareit.comments.dto.CommentsRequestDto;
import ru.practicum.shareit.comments.dto.CommentsResponseDto;
import ru.practicum.shareit.comments.service.CommentService;
import ru.practicum.shareit.items.ItemController;
import ru.practicum.shareit.items.dto.ItemCreationRequestDto;
import ru.practicum.shareit.items.dto.ItemResponseDto;
import ru.practicum.shareit.items.service.ItemService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.Constants.OWNER_USER_ID;

@WebMvcTest({CommentController.class, ItemController.class})
@ActiveProfiles("test")
class CommentControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private ItemService itemService;
    private ItemCreationRequestDto itemDto;
    private ItemResponseDto itemResponseDto;
    private CommentsRequestDto commentsRequestDto;
    private CommentsResponseDto commentsResponseDto;

    @BeforeEach
    void setupTestData() {
        itemDto = ItemCreationRequestDto.builder()
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .build();

        itemResponseDto = ItemResponseDto.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Description")
                .available(true)
                .ownerId(1L)
                .build();

        commentsRequestDto = CommentsRequestDto.builder().text("ssss").build();

        commentsResponseDto = CommentsResponseDto.builder().id(8L).text("ssss").build();
    }

    @Test
    void commentPastBooking() throws Exception {
        var userId = 1L;
        var itemId = 1L;

        when(itemService.createItem(eq(userId), any(ItemCreationRequestDto.class)))
                .thenReturn(itemResponseDto);

        mockMvc.perform(post("/items")
                        .header(OWNER_USER_ID, userId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk());

        when(commentService.commentPastBooking(eq(userId), eq(itemId), any(CommentsRequestDto.class)))
                .thenReturn(commentsResponseDto);

        mockMvc.perform(post("/items/" + itemId + "/comment")
                        .header(OWNER_USER_ID, userId)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(commentsRequestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id")
                        .value(commentsResponseDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                        .value(commentsResponseDto.getText()));
    }
}