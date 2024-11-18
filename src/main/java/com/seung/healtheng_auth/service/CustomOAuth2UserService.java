package com.seung.healtheng_auth.service;

import com.seung.healtheng_auth.dto.*;
import com.seung.healtheng_auth.dto.oauth2.GoogleResponse;
import com.seung.healtheng_auth.dto.oauth2.NaverResponse;
import com.seung.healtheng_auth.dto.oauth2.OAuth2Response;
import com.seung.healtheng_auth.dto.oauth2.SeungResponse;
import com.seung.healtheng_auth.entity.User;
import com.seung.healtheng_auth.enums.Role;
import com.seung.healtheng_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("seung")) {
            oAuth2Response = new SeungResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String userId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        Role role = null;
        User findUser = userRepository.findByUserId(userId).orElse(null);
        if (findUser != null) {
            role = findUser.getUserRole();

            findUser.builder()
                    .userName(oAuth2Response.getName())
                    .eMail(oAuth2Response.getEmail())
                    .userRole(role)
                    .build();
            userRepository.save(findUser);

            UserDTO userDTO = UserDTO.builder()
                    .userId(userId)
                    .username(oAuth2Response.getName())
                    .userRole(Role.ROLE_CONSUMER)
                    .build();

            return new CustomUserDetails(userDTO);
        }else{
            User saveUser = User.builder()
                    .userId(userId)
                    .userName(oAuth2Response.getName())
                    .eMail(oAuth2Response.getEmail())
                    .userRole(Role.valueOf("ROLE_CONSUMER"))
                    .build();
            userRepository.save(saveUser);

            UserDTO userDTO = UserDTO.builder()
                    .userId(userId)
                    .username(oAuth2Response.getName())
                    .userRole(Role.ROLE_CONSUMER)
                    .build();


            return new CustomUserDetails(userDTO);
        }

    }
}
