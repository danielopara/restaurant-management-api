package com.user.restaurantapp.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;


public class LoggingUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggingUtil.class);

    public static void logRequest(HttpServletRequest request, String message) {
        String endpoint = request.getRequestURI();
        logger.info("Endpoint: {}, Message: {}", endpoint, message);
    }
}
