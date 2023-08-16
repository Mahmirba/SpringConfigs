package org.example.utils;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@AllArgsConstructor
@Component
public class UtilityService {

    public static String getHttpServletRequestRemoteAddress() {
        try {
            RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
            if (attribs != null) {
                HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
                return request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error(String.format("error in get remote Address from HttpServletRequest %s ", e.getMessage()));
        }
        return "";
    }
}
