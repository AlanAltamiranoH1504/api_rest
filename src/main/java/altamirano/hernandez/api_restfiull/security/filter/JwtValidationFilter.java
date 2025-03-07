package altamirano.hernandez.api_restfiull.security.filter;

import altamirano.hernandez.api_restfiull.security.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //Sacamos el token del header
        String token = header.replace("Bearer", "");
        //Sacamos los claims del jwt
        try {
            Claims claims = Jwts.parser().verifyWith((SecretKey) JWT_SECRET).build().parseSignedClaims(token).getPayload();

            //Sacamos datos del cuerpo del token
            String nombre = claims.getSubject();
            Object rolesClaims = claims.get("roles");

            //Tratamos los roles
            Collection<? extends GrantedAuthority> roles = new ObjectMapper()
                    .addMixIn(Collection.class, SimpleGrantedAuthorityJsonCreator.class)
                    .readValue(rolesClaims.toString().getBytes(), Collection.class);

            //Iniciamos sesion y autenticamos
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(nombre, null, roles);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            Map<String, Object> json = new HashMap<>();
            json.put("error", e.getMessage());
            json.put("msg", "El jwt es invalido");

            response.getWriter().write(json.toString());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
