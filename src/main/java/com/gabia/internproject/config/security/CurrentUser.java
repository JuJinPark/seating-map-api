package com.gabia.internproject.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CurrentUser implements UserDetails {
    private List<GrantedAuthority> roles;
    private String userName;
    private long employeeId;
    private String provider;




    public CurrentUser(long employeeId, String userName, List<GrantedAuthority> roles,String provider){
        this.userName=userName;
        this.employeeId=employeeId;
        this.roles=roles;
        this.provider=provider;

    }
    public long getEmployeeId(){
        return employeeId;
    }
    public String getProvider() {return provider;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
