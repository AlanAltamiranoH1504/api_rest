package altamirano.hernandez.api_restfiull.security;

import altamirano.hernandez.api_restfiull.security.filter.JwtAuthenticationFilter;
import altamirano.hernandez.api_restfiull.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Clase que se encarga de la configuracion de Spring Security
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    //Metodo autenticationManager
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Metodo encargado del hasheo de passwords
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Configuramos seguridad de endpoints - (Habilitamos rutas e ignoramos seguridad de csrf)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(csrf-> csrf.disable())
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
