package org.example.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    private String username;
    private Long corporateId;
    private Long userId;
    private String clientIp;
    private String userAgent;
    private Long generateDate;
    private String firstName;
    private String lastName;
}
