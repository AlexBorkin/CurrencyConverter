package com.jdbc.example.mappers;

import com.jdbc.example.entity.User;
import com.jdbc.example.entity.UserRoleRef;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleRefMapper implements RowMapper<UserRoleRef>
{
    @Override
    public UserRoleRef mapRow(ResultSet resultSet, int i) throws SQLException
    {
        UserRoleRef userRoleRef = new UserRoleRef();

        userRoleRef.setUserName(resultSet.getString("userName"));
        userRoleRef.setRoleName(resultSet.getString("roleName"));

        return userRoleRef;
    }
}
