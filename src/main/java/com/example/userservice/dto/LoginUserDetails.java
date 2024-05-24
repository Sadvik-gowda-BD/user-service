package com.example.userservice.dto;

import com.example.userservice.entity.UserCredentialEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class LoginUserDetails implements UserDetails {
    private final String password;
    private final String userName;
    private final List<GrantedAuthority> grantedAuthorities;

    public LoginUserDetails(UserCredentialEntity userCredential) {
        this.userName = String.valueOf(userCredential.getUserId());
        this.password = userCredential.getPassword();
        this.grantedAuthorities = getGrantedAuthorities(userCredential.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private List<GrantedAuthority> getGrantedAuthorities(String userRole) {
        return List.of(new SimpleGrantedAuthority(userRole));
    }
}
