package org.example.security;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.repo.PrivilegeEntity;
import org.example.repo.PrivilegeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@FieldDefaults(makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class SecurityConfiguration {


  private final JwtUserDetailsService userDetailsService;

  private final JwtRequestFilter JwtRequestFilter;

  private final PrivilegeRepository privilegeRepository;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    List<PrivilegeEntity> privileges = privilegeRepository.findAll();
    for (PrivilegeEntity privilege : privileges) {
      httpSecurity.authorizeHttpRequests(
              request -> request.requestMatchers(privilege.getUri()).hasAuthority(privilege.getName())
      );
    }

    httpSecurity.authorizeHttpRequests(
            request -> request.requestMatchers("/user/create", "/auth/login").permitAll().anyRequest().authenticated()
    )
            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider()).addFilterBefore(
                    JwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    return httpSecurity.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
