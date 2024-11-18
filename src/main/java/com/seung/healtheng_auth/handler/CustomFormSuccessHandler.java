package com.seung.healtheng_auth.handler;

import com.seung.healtheng_auth.dto.CustomUserDetails;
import com.seung.healtheng_auth.enums.Role;
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
public class CustomFormSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = userDetails.getUsername();

        Iterator<? extends GrantedAuthority> iterator = authentication.getAuthorities().iterator();
        GrantedAuthority auth = iterator.next();
        Role role = Role.valueOf(auth.getAuthority());

        String accessToken = jwtUtil.createJwt("access",userId, role, 600000L);
        String refreshToken = jwtUtil.createJwt("refresh",userId, role, 86400000L);
        //OAuth2 로그인 성공시에 쿠키에 토큰을 넘긴 후 리다이렉션
        response.addCookie(createCookie("refresh", refreshToken));

        response.setHeader("Authorization","Bearer "+accessToken);

//        SimpleUrlAuthenticationSuccessHandler는 기본적으로 인증이 성공하면 다음과 같은 처리를 합니다:
//        세션 생성: 인증 정보가 SecurityContext에 저장됩니다. 이 정보는 보통 SecurityContextHolder.getContext().setAuthentication(authentication)을 통해 저장됩니다.
//                리다이렉션: 설정된 URL로 리다이렉트됩니다.

    }
}
