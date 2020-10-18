package me.liiot.snsserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.liiot.snsserver.model.user.User;
import me.liiot.snsserver.util.PasswordEncryptor;
import me.liiot.snsserver.util.SessionKeys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionLoginServiceTest {

    @Mock
    private MockHttpSession mockHttpSession;

    @InjectMocks
    private SessionLoginService sessionLoginService;

    private User testUser;

    @BeforeEach
    void setUpEach() {

        testUser = User.builder()
                .userId("test1")
                .password(PasswordEncryptor.encrypt("1234"))
                .name("Sarah")
                .phoneNumber("01012345678")
                .email("test1@test.com")
                .birth(Date.valueOf("1990-04-13"))
                .build();
    }

    @DisplayName("로그인")
    @Test
    void loginUserTest() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(testUser);

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockHttpSession).setAttribute(eq(SessionKeys.USER), valueCapture.capture());

        sessionLoginService.loginUser(testUser);

        verify(mockHttpSession).setAttribute(SessionKeys.USER, jsonStr);
        assertEquals(jsonStr, valueCapture.getValue());
    }

    @DisplayName("로그아웃")
    @Test
    void logoutUserTest() {

        mockHttpSession.setAttribute(SessionKeys.USER, testUser);

        sessionLoginService.logoutUser();

        verify(mockHttpSession).invalidate();
        assertNull(mockHttpSession.getAttribute(SessionKeys.USER));
    }
}