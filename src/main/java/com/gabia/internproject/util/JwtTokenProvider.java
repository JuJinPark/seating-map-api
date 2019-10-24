package com.gabia.internproject.util;


import com.gabia.internproject.config.security.CurrentUser;
import com.gabia.internproject.dto.response.EmployeeResponseDTO;
import com.gabia.internproject.exception.customExceptions.JwtTokenException;
import com.gabia.internproject.service.OAuth.OAuthAPIProvider;
import com.gabia.internproject.service.OAuth.OAuthConstants;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${app.auth.tokenSecret}")
    private String tokenSecret;
    @Value("${app.auth.tokenExpirationMsec}")
    private long tokenExpirationMsec;

    private static final String ROLE_PREFIX = "ROLE_";


    public String createToken(EmployeeResponseDTO employee, OAuthAPIProvider provider) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationMsec);

        return Jwts.builder()
                .setSubject(Long.toString(employee.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("name", employee.getEmployeeName())
                .claim("role", employee.getRole().getRoleType())
                .claim("provider", provider.name())
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();

    }

    public String getEmployeeId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Object getClaims(String token, String name) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get(name);
    }

    public String resolveToken(HttpServletRequest req) {

        return getJwtFromCookie(req).orElse(null);
//                getJwtFromCookie(req);
    }

    private Optional<String> getJwtFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Cookie jwt = Arrays.stream(cookies)
                    .filter(c -> OAuthConstants.SEAT_API_JWT.getText().equals(c.getName()))
                    .findFirst().orElse(null);
            return Optional.ofNullable(jwt).map(Cookie::getValue);
        }
        return Optional.empty();

    }

    public Authentication getAuthentication(HttpServletRequest httpServletRequest) {

        String token = resolveToken(httpServletRequest);


        if(token != null){
            if (validateToken(token)) {
                String employeeId = getEmployeeId(token);
                String role = (String) getClaims(token, "role");
                String name = (String) getClaims(token, "name");
                String provider = (String) getClaims(token, "provider");
                return createAuthToken(employeeId, name, role, provider);
            }
        }



        return null;
    }

    public UsernamePasswordAuthenticationToken createAuthToken(String employeeId, String name, String role, String provider) {

        CurrentUser user = new CurrentUser(Long.parseLong(employeeId), name, getAuthority(role), provider);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    }

    private List<GrantedAuthority> getAuthority(String roleType) {

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(ROLE_PREFIX + roleType));
        return roles;
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            throw new JwtTokenException("Invalid JWT signature");

        } catch (MalformedJwtException ex) {
            throw new JwtTokenException("Invalid JWT token");

        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException("Expired JWT token");

        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenException("Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            throw new JwtTokenException("JWT claims string is empty.");

        }

    }

//    public JwtResponseDTO createTokenAsDTO(EmployeeResponseDTO employee) {
//        String jwt = createToken(employee);
//        return new JwtResponseDTO(jwt);
//
//    }

}
