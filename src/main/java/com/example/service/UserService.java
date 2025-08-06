package com.example.service;

import com.example.entity.UserDO;
import com.example.exception.InvalidPasswordException;
import com.example.exception.UserAlreadyExistsException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
public class UserService {
    private final UserMapper userMapper;
    private final TokenService tokenService;

    @Autowired
    public UserService(UserMapper userMapper, TokenService tokenService) {
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Transactional
    public String register(String username, String password) {
        if (userMapper.findByUsername(username) != null) {
            throw new UserAlreadyExistsException("User already exists");
        }

        String userId = UUID.randomUUID().toString();
        userMapper.insert(new UserDO(userId, username, PasswordUtil.Md5Encrypt(password, null)));
        return userId;
    }

    @Transactional
    public void EditPassword(String token, String oldPassword, String newPassword) {
        String userId = tokenService.validateJwt(token);
        UserDO userDO = userMapper.findByUsername(userId);
        if (userDO == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!userDO.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException("Password does not match");
        }

        userDO.setPassword(PasswordUtil.Md5Encrypt(newPassword, null));
        userMapper.updateById(userDO);
    }
}
