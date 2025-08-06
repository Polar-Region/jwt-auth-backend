package com.example.util;

import org.springframework.util.DigestUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author Lee
 * @version 1.0
 * @since 2025/6/19
 */
public class PasswordUtil {
    public static String Md5Encrypt(String password, String salt) {
        String saltedPassword = password + (salt != null ? salt : "");
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }

    public static boolean matches(String password, String cryptedPassword) {
        return DigestUtils.md5DigestAsHex(password.getBytes()).equals(cryptedPassword);
    }
}
