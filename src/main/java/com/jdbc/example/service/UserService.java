package com.jdbc.example.service;

import com.jdbc.example.entity.ExchRate;
import com.jdbc.example.entity.Role;
import com.jdbc.example.entity.User;
import com.jdbc.example.entity.UserRoleRef;
import com.jdbc.example.mappers.ExchRateMapper;
import com.jdbc.example.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

@Service
public class UserService implements UserDetailsService
{
    private DataSource   dataSource;
    private JdbcTemplate jdbcTemplate;

    @Value("${UserRoleDefault}")
    private String userRoleDefault;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UseRoleRefService useRoleRefService;

    @Autowired
    public UserService(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> getAllUsers()
    {
        String strQuery = "select * from public.\"user\";";
        List<User> userList = jdbcTemplate.query(strQuery, new UserMapper());

        return userList;
    }

    public User getUserByUserName(String userName)
    {
        String strQuery = "select * from public.\"user\" where \"userName\" = ? limit 1;";

        User user = null;

        try
        {
            user = jdbcTemplate.queryForObject(strQuery, new Object[]{userName}, new UserMapper());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return user;
    }

    @Transactional
    public void create(String userName, String password)
    {
        String sqlQuery = "insert into public.\"user\" (\"userName\", password, active) values (?,?,?);";

        jdbcTemplate.update(sqlQuery, userName, passwordEncoder.encode(password), true);

        useRoleRefService.createUserRoleRef(userName, userRoleDefault);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        User user = this.getUserByUserName(s);

        if (user == null)
        {
            throw  new UsernameNotFoundException("Пользователь не найден");
        }
        else
        {
            user.setRoles(useRoleRefService.getRolesByUserName(s));
        }

        return user;
    }
}
