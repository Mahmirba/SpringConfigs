package org.example.business;

import lombok.AllArgsConstructor;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.repo.UserEntity;
import org.example.security.JwtTokenUtil;
import org.example.security.JwtUserDetailsService;
import org.example.security.UserDetail;
import org.example.utils.UtilityService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;


    public LoginResponse login(LoginRequest request) {

        UserEntity user;
        UserDetail userDetail = new UserDetail();
        try {
            user = userService.findByUsername(request.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("user not found  username: " + request.getUsername(), new Throwable(""));
            }

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            userDetail.setUsername(authenticate.getName());
            userDetail.setClientIp(UtilityService.getHttpServletRequestRemoteAddress());
            userDetail.setUserId(user.getId());


            return new LoginResponse(jwtTokenUtil.generateToken(userDetail));
        } catch (DisabledException e) {
            throw e;
        }
    }
}
