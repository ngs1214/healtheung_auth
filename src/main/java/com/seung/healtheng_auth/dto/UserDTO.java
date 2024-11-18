package com.seung.healtheng_auth.dto;

import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String userId;
    private Role userRole;
    private String username;
    private String password;

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .password(this.password)
                .userName(this.username)
                .userRole(this.userRole)
                .build();
    }
}
