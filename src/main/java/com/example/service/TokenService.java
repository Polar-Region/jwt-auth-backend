package com.example.service;

import com.example.exception.InvalidTokenException;
import com.example.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class TokenService {
    private final JwtUtils jwtUtils;

    @Autowired
    public TokenService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public String validateJwt(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid Authorization header");
        }
        String jwtToken = token.substring(7); // 去掉 "Bearer "

        if (!jwtUtils.validateToken(jwtToken)) {
            throw new InvalidTokenException("Token is invalid or expired");
        }

        String userId = jwtUtils.extractUserId(jwtToken);
        if (userId == null) {
            throw new InvalidTokenException("Token does not contain user ID");
        }

        return userId;
    }

    public String generateToken(String userId) {
        return jwtUtils.generateToken(userId);
    }
}
