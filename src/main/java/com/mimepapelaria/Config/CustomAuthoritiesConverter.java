package com.mimepapelaria.Config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<String> authorities = (Collection<String>) jwt.getClaims().get("role");
        return authorities.stream()
                .map(role -> (GrantedAuthority) () -> role)
                .collect(Collectors.toList());
    }
}
