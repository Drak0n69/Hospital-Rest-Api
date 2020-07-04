package net.thumbtack.school.hospital.controller;

import com.google.gson.Gson;
import net.thumbtack.school.hospital.dto.request.LoginRequestDto;
import net.thumbtack.school.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
public class TestSessionController {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    private final Gson GSON = new Gson();

    @Test
    void testLoginUser() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto("Denx847a1e", "drak0n6912");
        String requestJson = GSON.toJson(requestDto);
        mvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("JAVASESSIONID"));
        verify(userService, times(1)).login(eq(requestDto), anyString());
    }

    @Test
    void testFailedLoginUser() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto(null, null);
        String requestJson = GSON.toJson(requestDto);
        mvc.perform(post("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    @Test
    void testLogoutUser() throws Exception {
        mvc.perform(delete("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("JAVASESSIONID", "str")))
                .andExpect(status().isOk());
        verify(userService, times(1)).logout(eq("str"));
    }

    @Test
    void testFailedLogoutUser() throws Exception {
        mvc.perform(delete("/api/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("invalidCookie", "str")))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }
}
