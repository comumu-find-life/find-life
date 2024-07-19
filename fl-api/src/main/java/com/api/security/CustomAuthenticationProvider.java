package com.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = customUserDetailsService.loadUserByUsername(username);
//
//        System.out.println(user.getId());
//        System.out.println(user.getId());
//        System.out.println(user.getId());
//        System.out.println(user.getId());

//        if (user == null || !user.getPassword().equals(password)) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//        if (user == null || passwordEncoder.matches(password, user.getPassword())) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
        CustomUserDetails customUserDetails = (CustomUserDetails) user;

        return new UsernamePasswordAuthenticationToken(customUserDetails, password, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
