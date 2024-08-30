package ru.practicum.shareit.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.shareit.users.dto.UserCreationRequestDto;
import ru.practicum.shareit.users.dto.UserResponseDto;
import ru.practicum.shareit.users.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private UserCreationRequestDto requestDto;
    private UserResponseDto expectedResponse;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = UserCreationRequestDto.builder()
                .name("Test user")
                .email("e@ya.ru")
                .build();
        expectedResponse = UserResponseDto.builder()
                .id(1L)
                .name("Test user")
                .email("e@ya.ru")
                .build();
        responseDto = UserResponseDto.builder()
                .id(5L)
                .name("lkjh")
                .email("oi@ya.ru")
                .build();
    }

    @Test
    void createUser() throws Exception {
        Mockito.when(userService.createUser(ArgumentMatchers.any(UserCreationRequestDto.class)))
                .thenReturn(expectedResponse);
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedResponse.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedResponse.getEmail()))
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();
        System.out.println("Test createUser: response body: " + responseBody);
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserResponseDto> expectedList = List.of(new UserResponseDto(1L, "wd", "sd@ya.ru"));
        Mockito.when(userService.getAllUsers())
                .thenReturn(expectedList);
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var responseBody = result.getResponse().getContentAsString();
        System.out.println("Test getAllUsers: response body: " + responseBody);
        var actualList = new ObjectMapper().readValue(responseBody, new TypeReference<List<UserResponseDto>>() {
        });
        assertEquals(actualList.size(), expectedList.size());
    }

    @Test
    void getUserById() throws Exception {
        Mockito.when(userService.getUserById(ArgumentMatchers.any(Long.class)))
                .thenReturn(responseDto);
        var url = String.format("/users/%d", responseDto.getId());
        var result = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(responseDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(responseDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(responseDto.getEmail()))
                .andReturn();
        var responseBody = result.getResponse().getContentAsString();
        System.out.println("Test getUserById: response body: " + responseBody);
    }
}