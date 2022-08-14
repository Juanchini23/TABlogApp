package com.blog.Apiblog.seguridad;

import com.blog.Apiblog.excepciones.BlogAppExcepcion;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-miliseconds}")
    private int jwtExpirationInMs;

    public String generarTokenDeAcceso(Authentication authentication){
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime()+jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

        return token;
    }

    public String obtenerUsernameJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Boolean validarToken(String token){

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "Firma JWT no valida");
        }
        catch (MalformedJwtException ex){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "Token JWT no valida");
        }
        catch (ExpiredJwtException ex){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "Token JWT expirado");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
        }
        catch (IllegalArgumentException ex){
            throw new BlogAppExcepcion(HttpStatus.BAD_REQUEST, "La cadena claims JWT esta vacia");
        }

    }

}
