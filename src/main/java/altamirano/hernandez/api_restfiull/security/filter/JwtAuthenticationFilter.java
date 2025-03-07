package altamirano.hernandez.api_restfiull.security.filter;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Importamos las constantes para la firma del jwt
import static altamirano.hernandez.api_restfiull.security.TokenJWTConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Sobreescribimos el metodo attemAuthentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Usuario usuario = null;
        String nombre = null;
        String password = null;

        //Obtenemos los datos que vienen como json y los pasamos a objeto
        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            nombre = usuario.getNombre();
            password = usuario.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nombre, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    //Sobreescribimos el metodo successfulAuthentication
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //Sacamos el usuario de authResult
        User usuario = (User) authResult.getPrincipal();
        //Definimos claims (mas datos para el cuerpo del jwt)
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims().add("roles", new ObjectMapper().writeValueAsString(roles)).build();

        //Generamos el token
        String jwt = Jwts.builder()
                .subject(usuario.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(JWT_SECRET)
                .compact();

        //Devolvemos el token y el username
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jwt);
        Map<String, Object> json = new HashMap<>();
        json.put("token", jwt);
        json.put("nombre", usuario.getUsername());
        json.put("msg", "Has iniciado sesion con exito");
        response.getWriter().write(new ObjectMapper().writeValueAsString(json));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);
    }

    // Sobreescribimos el metodo usSuccess
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //Devolvemos hashMap
        Map<String, Object> json = new HashMap<>();
        json.put("msg", "Error en la autenticacion. Usuario o password incorrecto");

        response.getWriter().write(new ObjectMapper().writeValueAsString(json));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }
}
