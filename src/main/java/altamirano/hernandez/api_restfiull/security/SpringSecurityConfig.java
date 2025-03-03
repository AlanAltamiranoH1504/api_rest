package altamirano.hernandez.api_restfiull.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Clase Bean que se enccarga del encriptado de passwords
@Configuration
public class SpringSecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Configuramos seguridad de endpoints - (Habilitamos rutas e ignoramos seguridad de csrf)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                csrf(csrf-> csrf.disable())
//                csrf(csrf-> csrf.ignoringRequestMatchers("/security/save", "/security/register"))
                .authorizeHttpRequests(auth ->
                    auth
                    .requestMatchers(HttpMethod.GET, "/security/prueba", "/security/findAll").permitAll()
//                        "/security/save" → esa ruta no esta permitida
                    .requestMatchers(HttpMethod.POST, "/security/register").permitAll()

                        .anyRequest().authenticated()
        ).build();
    }
}
