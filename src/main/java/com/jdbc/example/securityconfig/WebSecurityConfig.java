package com.jdbc.example.securityconfig;

import com.jdbc.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;


    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/registration").permitAll();
        http.authorizeRequests().antMatchers("/converter").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        //http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/accessDenied");
        http.authorizeRequests().and().formLogin().loginPage("/login").permitAll();
        http.authorizeRequests().and().logout().permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


//    @Configuration
//    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter
//    {
//
//        @Override
//        public void init(AuthenticationManagerBuilder auth) throws Exception
//        {
//            auth
//                    .inMemoryAuthentication()
//                    .withUser("user").password("password").roles("USER");
//        }
//
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
