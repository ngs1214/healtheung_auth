package com.seung.healtheng_auth.dto;

import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.enums.Gender;
import com.seung.healtheng_auth.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JoinDTO {
    private String userId;
    private String password;

    private String userName;
    private String userBirth;
    private String nickName;
    private Gender userGender;
    private Role userRole;

    private String phone;
    private String eMail;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .userBirth(userBirth)
                .nickName(nickName)
                .userGender(userGender)
                .userRole(userRole)
                .phone(phone)
                .eMail(eMail)
                .createdAt(LocalDateTime.now())
                .build();

    }

}
