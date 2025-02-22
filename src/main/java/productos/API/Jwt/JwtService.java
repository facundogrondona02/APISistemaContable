package productos.API.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "123ASJA213O23L3LP3L3L3P23L12M121123ASJA213O23L3LP3L3L3P23L12M121";


    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ((1000 * 60 * 24) * 50)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsernameFromToken(String token) {
       return getClaim(token, Claims::getSubject);
    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
//
//    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){

        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
            final Claims claims = getAllClaims(token);
            return claimsResolver.apply(claims);
    }

    public Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){

        return getExpiration(token).before(new Date());

    }
}
