    package com.example.service;

    import com.example.entity.UserDO;
    import com.example.exception.*;
    import com.example.mapper.UserMapper;
    import com.example.util.PasswordUtil;
    import org.junit.jupiter.api.Test;
    import org.mockito.MockedStatic;

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

    class UserServiceTest {

        private final UserMapper userMapper = mock(UserMapper.class);
        private final TokenService tokenService = mock(TokenService.class);
        private final UserService userService = new UserService(userMapper, tokenService);

        @Test
        void register_shouldThrow_whenUserAlreadyExists() {
            when(userMapper.findByUsername("john")).thenReturn(new UserDO());

            assertThrows(UserAlreadyExistsException.class, () -> userService.register("john", "123"));
        }

        @Test
        void register_shouldReturnUserId_whenNewUser() {
            when(userMapper.findByUsername("newUser")).thenReturn(null);

            try (MockedStatic<PasswordUtil> mocked = mockStatic(PasswordUtil.class)) {
                mocked.when(() -> PasswordUtil.Md5Encrypt("123", null)).thenReturn("encrypted");

                doAnswer(invocation -> {
                    UserDO user = invocation.getArgument(0);
                    assertEquals("newUser", user.getUsername());
                    assertEquals("encrypted", user.getPassword());
                    return null;
                }).when(userMapper).insert(any(UserDO.class));

                String userId = userService.register("newUser", "123");
                assertNotNull(userId);
            }
        }


        @Test
        void editPassword_shouldThrow_whenUserNotFound() {
            when(tokenService.validateJwt("token")).thenReturn("uid");
            when(userMapper.findByUsername("uid")).thenReturn(null);

            assertThrows(UserNotFoundException.class, () -> userService.EditPassword("token", "old", "new"));
        }

        @Test
        void editPassword_shouldThrow_whenOldPasswordIncorrect() {
            UserDO user = new UserDO("uid", "john", "oldpwd");
            when(tokenService.validateJwt("token")).thenReturn("uid");
            when(userMapper.findByUsername("uid")).thenReturn(user);

            assertThrows(InvalidPasswordException.class, () -> userService.EditPassword("token", "wrong", "new"));
        }

        @Test
        void editPassword_shouldUpdatePassword_whenOldPasswordMatches() {
            UserDO user = new UserDO("uid", "john", "oldpwd");
            when(tokenService.validateJwt("token")).thenReturn("uid");
            when(userMapper.findByUsername("uid")).thenReturn(user);

            try (MockedStatic<PasswordUtil> mocked = mockStatic(PasswordUtil.class)) {
                mocked.when(() -> PasswordUtil.Md5Encrypt("new", null)).thenReturn("newpwd");

                userService.EditPassword("token", "oldpwd", "new");

                assertEquals("newpwd", user.getPassword());
                verify(userMapper).updateById(user);
            }
        }

    }

