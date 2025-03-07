package altamirano.hernandez.api_restfiull.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

import static altamirano.hernandez.api_restfiull.security.TokenJWTConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //Sobreescribimos metodo doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Sacamos el header
        String header = request.getHeader("Authorization");

        //Verificamos que el header no sea null y que si contenga la palabra prefijo Bearer
        if (header == null || !header.startsWith("Bearer ")){
            return;
        }

        //Sacamos el token del header
        String token = header.replace("Bearer", "");
        //Sacamos los claims del jwt
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) JWT_SECRET).build().parseSignedClaims(token).getPayload();
        }catch (JwtException e){

        }
    }
}
