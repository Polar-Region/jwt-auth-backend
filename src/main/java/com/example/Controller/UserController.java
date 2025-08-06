package com.example.Controller;

import com.example.request.EditPasswordRequest;
import com.example.request.RegisterRequest;
import com.example.response.ApiResponse;
import com.example.service.UserService;
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
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResponse<String> register(@RequestBody RegisterRequest registerRequest) {
        String userId = userService.register(registerRequest.getUsername(), registerRequest.getPassword());
        return new ApiResponse<>(userId, "Register success", HttpStatus.OK);
    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public ApiResponse<String> editPassword(
            @RequestBody EditPasswordRequest editPasswordRequest,
            @RequestHeader("Authorization") String token){
        userService.EditPassword(token, editPasswordRequest.getOldPassword(), editPasswordRequest.getNewPassword());
        return new ApiResponse<>("", "Edit password success", HttpStatus.OK);
    }
}
