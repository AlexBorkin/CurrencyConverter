package com.jdbc.example.service;

import com.jdbc.example.entity.Role;
import com.jdbc.example.entity.User;
import com.jdbc.example.mappers.RoleMapper;
import com.jdbc.example.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class RoleService
{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleService(DataSource dataSource)
    {
        this.dataSource   = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Role> getAllRoles()
    {
        String strQuery = "select * from public.\"role\";";
        List<Role> userList = jdbcTemplate.query(strQuery, new RoleMapper());

        return userList;
    }

    public Role getRoleByRoleName(String roleName)
    {
        String strQuery = "select * from public.\"role\" where \"roleName\" = ? limit 1;";

        Role role = jdbcTemplate.queryForObject(strQuery, new Object[]{roleName}, new RoleMapper());

        return role;
    }

}
