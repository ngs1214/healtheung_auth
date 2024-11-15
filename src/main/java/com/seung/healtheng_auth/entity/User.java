package com.seung.healtheng_auth.entity;

import com.seung.healtheng_auth.enums.Gender;
import com.seung.healtheng_auth.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;

    private String userName;
    private String userBirth;
    private String nickName;
    @Enumerated(EnumType.STRING)
    private Gender userGender;
    @Enumerated(EnumType.STRING)
    private Role userRole;

    private String phone;
    private String eMail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public User(String userId, String password, String userName, String userBirth,
                String nickName, Gender userGender, Role userRole, String phone, String eMail,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userBirth = userBirth;
        this.nickName = nickName;
        this.userGender = userGender;
        this.userRole = userRole;
        this.phone = phone;
        this.eMail = eMail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
