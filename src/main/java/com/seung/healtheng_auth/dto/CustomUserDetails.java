package com.seung.healtheng_auth.dto;

import com.seung.healtheng_auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {

    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return String.valueOf(userDTO.getUserRole());
                    }
                }
        );
        return collection;
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }
    @Override
    public String getUsername() {
        return userDTO.getUserId();

    }

    @Override
    public String getName() {
        return userDTO.getUsername();
    }
}
