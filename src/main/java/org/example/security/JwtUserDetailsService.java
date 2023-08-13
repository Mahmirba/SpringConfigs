package org.example.security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Transactional
@FieldDefaults(makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class JwtUserDetailsService implements UserDetailsService {

    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginInfo) throws UsernameNotFoundException {
        return null;
    }

}
