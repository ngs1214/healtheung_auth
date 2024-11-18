package com.seung.healtheng_auth.service;

import com.seung.healtheng_auth.dto.JoinDTO;
import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.exception.CustomException;
import com.seung.healtheng_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Long join(JoinDTO joinDTO) {

        String userId = joinDTO.getUserId();
        boolean isExist = userRepository.existsByUserId(userId);
        if(isExist) {
            throw new CustomException("존재하는 아이디입니다");
        }
        joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));

        User entity = joinDTO.toEntity();
        User save = userRepository.save(entity);
        return save.getId();
    }
}
