package com.example.service;

import com.example.entity.UserDO;
import com.example.exception.InvalidPasswordException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


/**
 *
 * <p>
 *
 * </p>
 *
 * @author Lee
 * @version 1.0
 * @since 2025/8/6
 */
class AuthServiceTest {

    private UserMapper userMapper;
    private TokenService tokenService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userMapper = mock(UserMapper.class);
        tokenService = mock(TokenService.class);
        authService = new AuthService(userMapper, tokenService);
    }

    @Test
    void login_shouldThrowUserNotFoundException_whenUserNotExists() {
        when(userMapper.findByUsername("test")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> authService.Login("test", "123"));
    }

    @Test
    void login_shouldThrowInvalidPasswordException_whenPasswordIncorrect() {
        UserDO user = new UserDO("uid-1", "test", "hashed");
        when(userMapper.findByUsername("test")).thenReturn(user);

        try (MockedStatic<PasswordUtil> mockedStatic = mockStatic(PasswordUtil.class)) {
            mockedStatic.when(() -> PasswordUtil.Md5Encrypt("wrong", "hashed")).thenReturn("wrongHash");

            assertThrows(InvalidPasswordException.class, () -> authService.Login("test", "wrong"));
        }
    }

    @Test
    void login_shouldReturnToken_whenUsernameAndPasswordCorrect() {
        UserDO user = new UserDO("uid-1", "test", "hashed");
        when(userMapper.findByUsername("test")).thenReturn(user);
        when(tokenService.generateToken("uid-1")).thenReturn("jwt-token");

        try (MockedStatic<PasswordUtil> mockedStatic = mockStatic(PasswordUtil.class)) {
            mockedStatic.when(() -> PasswordUtil.Md5Encrypt("123", "hashed")).thenReturn("hashed");

            String result = authService.Login("test", "123");
            assertThat(result).isEqualTo("jwt-token");
        }
    }

    @Test
    void getUsernameFromJwt_shouldReturnUsername() {
        when(tokenService.validateJwt("jwt")).thenReturn("uid-1");
        when(userMapper.findByUserId("uid-1")).thenReturn(new UserDO("uid-1", "john", "pwd"));

        String username = authService.getUsernameFromJwt("jwt");
        assertThat(username).isEqualTo("john");
    }
}