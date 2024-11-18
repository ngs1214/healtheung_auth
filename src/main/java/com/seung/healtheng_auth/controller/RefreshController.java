package com.seung.healtheng_auth.controller;

import com.seung.healtheng_auth.enums.Role;
import com.seung.healtheng_auth.service.RefreshService;
import com.seung.healtheng_auth.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.seung.healtheng_auth.util.CookieUtil.createCookie;

@RestController
@RequiredArgsConstructor
public class RefreshController {
    private final RefreshService refreshService;
    private final JWTUtil jwtUtil;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                String value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                refreshToken = value.replace("Bearer ", "");
            }
        }
        if (refreshToken == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }
        //expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refreshToken);

        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String userId = jwtUtil.getUserId(refreshToken);
        Role role = jwtUtil.getRole(refreshToken);


        //make new JWT
        String newAccess = jwtUtil.createJwt("access", userId, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", userId, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshService.deleteRefreshToken(userId);
        refreshService.saveRefreshToken(userId,newRefresh,86400000L);

        //response
        response.setHeader("Authorization","Bearer "+ newAccess);
        response.addCookie(createCookie("refreshToken","Bearer "+ newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);


    }
}
