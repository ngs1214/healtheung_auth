package com.seung.healtheng_auth.dto.oauth2;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
