package com.jdbc.example.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Role implements GrantedAuthority
{
    private String roleName;
    private String description;

    public Role()
    {
    }

    public Role(String roleName)
    {
        this.roleName = roleName;
    }

    public Role(String roleName, String description)
    {
        this.roleName    = roleName;
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String getAuthority()
    {
        return getRoleName();
    }
}
