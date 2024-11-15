package com.seung.healtheng_auth.dto;

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
}
