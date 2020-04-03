package com.jdbc.example.service;

import com.jdbc.example.entity.Role;
import com.jdbc.example.entity.User;
import com.jdbc.example.entity.UserRoleRef;
import com.jdbc.example.mappers.UserMapper;
import com.jdbc.example.mappers.UserRoleRefMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UseRoleRefService
{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleService roleService;

    @Autowired
    public UseRoleRefService(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<UserRoleRef> getByUserName(String userName)
    {
        String strQuery = "select * from public.\"userRoleRef\" where \"userName\" = ?;";
        List<UserRoleRef> userRoleRefList = jdbcTemplate.query(strQuery, new Object[]{userName}, new UserRoleRefMapper());

        return userRoleRefList;
    }

    public List<UserRoleRef> getByRoleName(String roleName)
    {
        String strQuery = "select * from public.\"userRoleRef\" where roleName;";
        List<UserRoleRef> userRoleRefList = jdbcTemplate.query(strQuery, new Object[]{roleName}, new UserRoleRefMapper());

        return userRoleRefList;
    }

    //Возвращает Set всех ролей, которые сопоставлены текущему пользователю!
    public Set<Role> getRolesByUserName(String userName)
    {
        String strQuery = "select * from public.\"userRoleRef\" where \"userName\" = ?;";
        List<UserRoleRef> userRoleRefList = jdbcTemplate.query(strQuery, new Object[]{userName}, new UserRoleRefMapper());

        Set<Role> setRoles = new HashSet<>();

        for (UserRoleRef curr: userRoleRefList)
        {
            setRoles.add(roleService.getRoleByRoleName(curr.getRoleName()));
        }

        return setRoles;
    }

    public void createUserRoleRef(String userName, String roleName)
    {
        String strQuery = "insert into public.\"userRoleRef\" (\"userName\", \"roleName2\") values(?,?);";

        jdbcTemplate.update(strQuery, userName, roleName);
    }
}
