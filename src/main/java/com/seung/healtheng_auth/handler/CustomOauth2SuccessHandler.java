package com.seung.healtheng_auth.handler;

import com.seung.healtheng_auth.dto.CustomUserDetails;
import com.seung.healtheng_auth.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;

import static com.seung.healtheng_auth.util.CookieUtil.createCookie;

@Component
@RequiredArgsConstructor
public class CustomOauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = userDetails.getUsername();

        Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt("access",userId, role, 600000L);
        String refreshToken = jwtUtil.createJwt("refresh",userId, role, 86400000L);
        //OAuth2 로그인 성공시에 쿠키에 토큰을 넘긴 후 리다이렉션
        response.addCookie(createCookie("refresh", refreshToken));

        response.setHeader("Authorization","Bearer "+accessToken);

        response.sendRedirect("http://localhost:5173");

    }
}
