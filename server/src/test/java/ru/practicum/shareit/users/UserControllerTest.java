package ru.practicum.shareit.users;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void createUser() throws Exception {
        var requestDto = UserCreationRequestDto.builder()
                .name("Test user")
                .email("e@ya.ru")
                .build();
        var expectedResponse = UserResponseDto.builder()
                .id(1L)
                .name("Test user")
                .email("e@ya.ru")
                .build();

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
        System.out.println("Response body: " + responseBody);
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUserById() {
    }

    @Test
    void deleteUserById() {
    }
}