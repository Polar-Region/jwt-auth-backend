package com.example.service;

import com.example.entity.UserDO;
import com.example.exception.InvalidPasswordException;
import com.example.exception.InvalidTokenException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.util.PasswordUtil;
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
public class AuthService {
    private final UserMapper userMapper;
    private final TokenService tokenService;

    @Autowired
    public AuthService(UserMapper userMapper, TokenService tokenService) {
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Transactional
    public String Login(String username, String password) {
        UserDO userDO = userMapper.findByUsername(username);
        if (userDO == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!PasswordUtil.Md5Encrypt(password, "").equals(userDO.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return tokenService.generateToken(userDO.getUserId());
    }

    @Transactional
    public String getUsernameFromJwt(String token) {

        String userId = tokenService.validateJwt(token);
        return userMapper.findByUserId(userId).getUsername();
    }
}
