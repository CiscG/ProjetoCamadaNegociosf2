package com.travelagency.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitário para Logging de Sistema
 * Requisito: Logging de Sistema
 */
public class LoggerUtil {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String LOG_PREFIX = "[TRAVEL-AGENCY]";
    
    public void info(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println(String.format("%s [INFO] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
    
    public void warn(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println(String.format("%s [WARN] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
    
    public void error(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.err.println(String.format("%s [ERROR] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
    
    public void debug(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println(String.format("%s [DEBUG] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
}
