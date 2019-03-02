package com.can.core.security;

import com.can.core.exception.CustomError;
import com.can.core.exception.CustomException;
import com.can.data.dao.InstanceUserCredentialsDAO;
import com.can.data.entity.InstanceUserCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import static com.can.core.constants.Constants.TOKEN_EXPIRE_AFTER_MS;

@Component
public class TokenProvider {


    private static final String SECRET_KEY = "osf-task-can";
    private static final String SECRET_KEY_VALUE = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

    @Autowired
    private InstanceUserCredentialsDAO systemUserCredentialsDAO;

    public TokenDTO generateToken(String claimer) {

        Claims claims = Jwts.claims().setSubject(claimer);

        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + TOKEN_EXPIRE_AFTER_MS);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_VALUE)
                .compact();

        return new TokenDTO(token, expiresAt);
    }

    public Authentication getAuthentication(String token) {
        InstanceUserCredentials userDetails = systemUserCredentialsDAO.findByUserName(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", Arrays.asList(AdminAuthority.getInstance()));
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY_VALUE).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("Authentication-Token");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY_VALUE).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw CustomException.of(CustomError.GENERIC_ERROR, "Expired or invalid JWT token");
        }
    }


}

