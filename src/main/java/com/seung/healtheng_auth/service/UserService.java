package com.seung.healtheng_auth.service;

import com.seung.healtheng_auth.dto.JoinDTO;
import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long join(JoinDTO joinDTO) {
        User entity = joinDTO.toEntity();
        User save = userRepository.save(entity);
        return save.getId();
    }
}
