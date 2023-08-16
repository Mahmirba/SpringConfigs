package org.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@FieldDefaults(makeFinal = true)
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 24 * 60;
    static String UserId_Prefix = "userId=";
    static String Username_Prefix = "username=";
    static String RemoteAddr_Prefix = "remoteAddr=";
    static String UserAgent_Prefix = "userAgent=";
    static String GenerateDate_Prefix = "generateDate=";
    static String FLName_Prefix = "flName=";
    static String Splitter = "-##-";

    @NonFinal
    @Value("${jwt.secret}")
    private String secret;


    public String getUsernameFromToken(String token) {
        String subject = getClaimFromToken(token, Claims::getSubject);
        String[] subjects = subject.split(Splitter);
        for (String str : subjects) {
            if (str.startsWith(Username_Prefix)) {
                return str.substring(Username_Prefix.length());
            }
        }
        return "";
    }

    public Long getGenerateDateFromToken(String token) {
        String subject = getClaimFromToken(token, Claims::getSubject);
        String[] subjects = subject.split(Splitter);
        for (String str : subjects) {
            if (str.startsWith(GenerateDate_Prefix)) {
                return Long.valueOf(str.substring(GenerateDate_Prefix.length()));
            }
        }
        return null;
    }

    public String getRemoteAddrFromToken(String token) {
        String subject = getClaimFromToken(token, Claims::getSubject);
        String[] subjects = subject.split(Splitter);
        for (String str : subjects) {
            if (str.startsWith(RemoteAddr_Prefix)) {
                return str.substring(RemoteAddr_Prefix.length());
            }
        }
        return null;
    }

    private Boolean isTokenRemoteAddrValid(String token) {
        final String remoteAddrFromToken = getRemoteAddrFromToken(token);
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs == null)
            return false;
        HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
        String remoteAddr = request.getRemoteAddr();

        return !StringUtils.isEmpty(remoteAddr) && remoteAddr.equalsIgnoreCase(remoteAddrFromToken);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isTokenGenerateDateValid(String token, Long lastGenDate) {
        final Long generateDateFromToken = getGenerateDateFromToken(token);
        return lastGenDate != null && lastGenDate.equals(generateDateFromToken);
    }

    public String generateToken(UserDetail userDetail) {
        Map<String, Object> claims = new HashMap<>();
        StringBuilder subject = new StringBuilder();
        subject.append(Username_Prefix).append(userDetail.getUsername());
        subject.append(Splitter);
        if (userDetail.getUserId() != null && userDetail.getUserId() != 0) {
            subject.append(UserId_Prefix).append(userDetail.getUserId());
            subject.append(Splitter);
        }
        subject.append(RemoteAddr_Prefix).append(userDetail.getClientIp());
        subject.append(Splitter);

        subject.append(UserAgent_Prefix).append(userDetail.getUserAgent());
        subject.append(Splitter);

        subject.append(GenerateDate_Prefix).append(userDetail.getGenerateDate());
        subject.append(Splitter);

        subject.append(FLName_Prefix).append(userDetail.getFirstName()).append(" ").append(userDetail.getLastName());
        subject.append(Splitter);

        return doGenerateToken(claims, subject.toString());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }

    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equalsIgnoreCase(username) && !isTokenExpired(token)
                && isTokenRemoteAddrValid(token)
        );

    }


}
