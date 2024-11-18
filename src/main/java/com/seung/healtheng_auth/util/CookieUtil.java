package com.seung.healtheng_auth.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class CookieUtil {
    public static Cookie createCookie(String key, String value) {
        try {
            // 쿠키 값 URL 인코딩 (공백을 %20으로 처리)
            String encodedValue = URLEncoder.encode(value, "UTF-8");

            Cookie cookie = new Cookie(key, encodedValue);
            cookie.setMaxAge(60 * 60 * 60);  // 쿠키 유효 기간 (60시간, 필요에 맞게 설정)
            cookie.setPath("/");
            // HTTPS일 경우 사용 (필요 시 주석 해제)
            // cookie.setSecure(true);

            cookie.setHttpOnly(true);  // 자바스크립트에서 쿠키 접근 방지

            return cookie;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  // 인코딩 예외 처리
            return null;
        }
    }
}
