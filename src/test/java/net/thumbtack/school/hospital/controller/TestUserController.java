package net.thumbtack.school.hospital.controller;

import net.thumbtack.school.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class TestUserController {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testGetSettings() throws Exception {
        mvc.perform(get("/api/settings")
                .cookie(new Cookie("JAVASESSIONID", "value")))
                .andExpect(status().isOk());
        verify(userService, times(1)).getServerSettings(eq("value"));
    }

    @Test
    void testGetSettingsNoCookie() throws Exception {
        mvc.perform(get("/api/settings"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getServerSettings(null);
    }

    @Test
    void testGetAccountInfo() throws Exception {
        mvc.perform(get("/api/account/")
                .cookie(new Cookie("JAVASESSIONID", "value")))
                .andExpect(status().isOk());
        verify(userService, times(1)).getAccountInfo(eq("value"));
    }

    @Test
    void testFailGetAccountInfo() throws Exception {
        mvc.perform(get("/api/account/")
                .cookie(new Cookie("cookie", "value")))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.errors", hasSize(1)));
    }
}
