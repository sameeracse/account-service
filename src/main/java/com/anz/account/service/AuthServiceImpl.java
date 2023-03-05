package com.anz.account.service;

import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService {

    @Override
    public String getUserIdByToken(String token) {

        if(token == null || token.isBlank()) {
            throw new IllegalArgumentException("token shouldn't be null or empty");
        }

        // this service will call to authentication server to get the access token
        // as of now bearer token is return a user id
        return token;
    }
}
