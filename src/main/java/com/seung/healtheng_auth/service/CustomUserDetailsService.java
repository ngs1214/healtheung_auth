package com.seung.healtheng_auth.service;


import com.seung.healtheng_auth.dto.CustomUserDetails;
import com.seung.healtheng_auth.dto.UserDTO;
import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

//        UserEntity findUser = userRepository.findByUsername(username);
        UserDTO findUser = userRepository.findByUserId(userId).orElse(null).toDto();


        if (findUser != null) {
            return new CustomUserDetails(findUser);
        }
        return null;
    }
}
