package com.seung.healtheng_auth.service.form;

import com.seung.healtheng_auth.dto.JoinDTO;
import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Long join(JoinDTO joinDTO) {

        joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));

        User entity = joinDTO.toEntity();
        User save = userRepository.save(entity);
        return save.getId();
    }
}
