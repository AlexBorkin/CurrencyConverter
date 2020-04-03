package com.jdbc.example.mappers;

import com.jdbc.example.entity.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role>
{
    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Role role = new Role();

        role.setRoleName(resultSet.getString("roleName"));
        role.setDescription(resultSet.getString("description"));

        return role;
    }
}
