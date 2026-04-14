package e_commers.com.example.e_commers.security;

import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import java.util.stream.DoubleStream;
@Component
public class JwtUtil {
    private static DoubleStream Jwts;
    private final String SECRET = "mysecretkeymysecretkeymysecretkey";



    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    public  static String getToken(String email){

        return Jwts.builder()
                 .setSubject(email)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                 .signWith( HS256)
                 .compact();
     }


}
}
