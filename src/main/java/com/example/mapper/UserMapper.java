package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    UserDO findByUserId(String userId);

    @Select("SELECT * FROM users WHERE username = #{username}")
    UserDO findByUsername(String username);
}
