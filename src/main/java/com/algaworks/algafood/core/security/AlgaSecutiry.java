package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecutiry {

    public Authentication getAuthentication(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public Long getUsuarioId(){
        Jwt userAuth = (Jwt) getAuthentication().getPrincipal();

        return userAuth.getClaim("user_id");
    }

}
