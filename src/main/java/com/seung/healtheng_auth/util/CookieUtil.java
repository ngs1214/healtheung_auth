package com.seung.healtheng_auth.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;


public class CookieUtil {
    public static  Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        //HTTPS일 경우 사용
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
