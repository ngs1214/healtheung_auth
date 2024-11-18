package com.seung.healtheng_auth.controller;


import com.seung.healtheng_auth.dto.JoinDTO;
import com.seung.healtheng_auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDTO joinDTO) {
        System.out.println("joinDTO = " + joinDTO);
        return ResponseEntity.ok(userService.join(joinDTO));
    }

}
