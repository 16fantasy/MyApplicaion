package com.fantasy.mapper;

import com.fantasy.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jingc
 * @description UserMapper
 * @since 2022/2/17
 */
@Mapper
public interface UserMapper {

    User getUserByUsername(String username);
}
