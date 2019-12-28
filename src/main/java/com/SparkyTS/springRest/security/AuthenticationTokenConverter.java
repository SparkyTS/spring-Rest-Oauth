package com.SparkyTS.springRest.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class AuthenticationTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        User user=(User)authentication.getPrincipal();
        info.put("username",user.getUsername());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setTokenType("Bearer ");
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }
}
