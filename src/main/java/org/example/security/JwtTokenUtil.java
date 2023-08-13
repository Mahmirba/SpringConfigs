package org.example.security;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @NonFinal
    @Value("${app.mode}")
    private String appMode;

}
