package com.example.service;

import com.example.exception.InvalidTokenException;
import com.example.util.JwtUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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

class TokenServiceTest {

    private final JwtUtils jwtUtils = mock(JwtUtils.class);
    private final TokenService tokenService = new TokenService(jwtUtils);

    @Test
    void validateJwt_shouldThrow_whenTokenInvalid() {
        when(jwtUtils.validateToken("bad-token")).thenReturn(true);

        assertThrows(InvalidTokenException.class, () -> tokenService.validateJwt("bad-token"));
    }

    @Test
    void validateJwt_shouldThrow_whenUserIdNull() {
        when(jwtUtils.validateToken("token")).thenReturn(false);
        when(jwtUtils.extractUserId("token")).thenReturn(null);

        assertThrows(InvalidTokenException.class, () -> tokenService.validateJwt("token"));
    }

    @Test
    void validateJwt_shouldReturnUserId_whenValid() {
        when(jwtUtils.validateToken("token")).thenReturn(false);
        when(jwtUtils.extractUserId("token")).thenReturn("uid-123");

        String userId = tokenService.validateJwt("token");
        assertEquals("uid-123", userId);
    }

    @Test
    void generateToken_shouldReturnToken() {
        when(jwtUtils.generateToken("uid-123")).thenReturn("token-abc");

        String token = tokenService.generateToken("uid-123");
        assertEquals("token-abc", token);
    }
}

