package com.seung.healtheng_auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seung.healtheng_auth.dto.CustomUserDetails;
import com.seung.healtheng_auth.dto.LoginDTO;
import com.seung.healtheng_auth.dto.UserDTO;
import com.seung.healtheng_auth.enums.Role;
import com.seung.healtheng_auth.service.RefreshService;
import com.seung.healtheng_auth.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static com.seung.healtheng_auth.util.CookieUtil.createCookie;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshService refreshService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        try {
            LoginDTO loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getPassword(), null);
            //매니저가 검증 진행
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //성공시에
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt("access", userId, role, 600000L);
        String refreshToken = jwtUtil.createJwt("refresh", userId, role, 86400000L);

        //Refresh 토큰 저장
        refreshService.saveRefreshToken(userId,refreshToken,86400000L);

        //응답 설정
        response.setHeader("Authorization","Bearer "+ accessToken);
        response.addCookie(createCookie("refreshToken","Bearer "+ refreshToken));
        response.setStatus(HttpStatus.OK.value());

    }
    //실패시에
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
