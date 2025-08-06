package com.example.Controller;

import com.example.request.LoginRequest;
import com.example.response.ApiResponse;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.Login(loginRequest.getUsername(), loginRequest.getPassword());
        return new ApiResponse<String>(token, "Login success", HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public ApiResponse<String> authenticate(@RequestHeader("Authorization") String token) {
        String username = authService.getUsernameFromJwt(token);
        return new ApiResponse<>(username, "Authentication successful", HttpStatus.OK);
    }
}
