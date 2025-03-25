package com.cryfirock.msvc.users.msvc_users.interceptors;

/**
 * Dependencies
 */
import com.cryfirock.msvc.users.msvc_users.security.config.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.springframework.stereotype.Component;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Component("userOperationsInterceptor")
public class UserOperationsInterceptor implements HandlerInterceptor {

    /**
     * Attributes
     */
    private static final Logger logger = LoggerFactory.getLogger(UserOperationsInterceptor.class);

    /**
     * 
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {

        // Solo interceptar métodos de controlador
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod controller = (HandlerMethod) handler;
        String method = request.getMethod();
        String endpoint = request.getRequestURI();

        // Medición de tiempo
        long start = System.currentTimeMillis();
        request.setAttribute("start", start);

        // Validar token JWT para rutas protegidas
        if (!endpoint.equals("/api/users") || !method.equals("POST")) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Intento de acceso no autorizado a {}", endpoint);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT requerido");
                return false;
            }

            try {
                String token = authHeader.substring(7);
                Claims claims = Jwts.parser()
                        .verifyWith(TokenJwtConfig.SECRET_KEY)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // Verificar expiración del token
                if (claims.getExpiration().before(new Date())) {
                    logger.warn("Token expirado para el usuario {}", claims.getSubject());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
                    return false;
                }

                // Registrar información del usuario
                request.setAttribute("username", claims.getSubject());

            } catch (Exception e) {
                logger.error("Error validando token JWT: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return false;
            }
        }

        logger.info("Iniciando operación {} en {} - Controlador: {}",
                method,
                endpoint,
                controller.getMethod().getName());

        return true;
    }

    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        HandlerMethod controller = (HandlerMethod) handler;
        long end = System.currentTimeMillis();
        long start = (long) request.getAttribute("start");
        long duration = end - start;

        String username = (String) request.getAttribute("username");
        String logMessage = String.format(
                "Operación %s completada en %d ms - Usuario: %s - Status: %d",
                request.getMethod(),
                duration,
                username != null ? username : "anon",
                response.getStatus());

        // Clasificar logs por nivel según el método HTTP
        switch (request.getMethod()) {
            case "POST":
            case "PUT":
            case "DELETE":
                logger.warn(logMessage);
                break;
            default:
                logger.info(logMessage);
        }

        // Registrar especialmente operaciones sensibles
        if (response.getStatus() >= 400) {
            logger.error("Error en operación {}: {} - {}",
                    controller.getMethod().getName(),
                    response.getStatus(),
                    request.getRequestURI());
        }
    }
}