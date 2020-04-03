package com.jdbc.example.mappers;

import com.jdbc.example.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User>
{
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException
    {
        User user = new User();

        user.setUserName(resultSet.getString("userName"));
        user.setPassword(resultSet.getString("password"));
        user.setActive(resultSet.getBoolean("active"));

        return user;
    }
}
