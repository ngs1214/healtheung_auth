package com.seung.healtheng_auth.controller;


import com.seung.healtheng_auth.dto.JoinDTO;
import com.seung.healtheng_auth.service.form.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(JoinDTO joinDTO) {
        return ResponseEntity.ok(userService.join(joinDTO));
    }

}
