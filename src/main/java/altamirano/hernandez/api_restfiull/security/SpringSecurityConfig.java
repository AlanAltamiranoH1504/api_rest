package altamirano.hernandez.api_restfiull.security;

import altamirano.hernandez.api_restfiull.security.filter.JwtAuthenticationFilter;
import altamirano.hernandez.api_restfiull.security.filter.JwtValidationFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                .csrf(csrf -> csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth ->
                        auth
                                //Definimos configuracion de rutas segun permisos de usuario
                                .requestMatchers(HttpMethod.GET, "/security/prueba", "/security/findAll").permitAll()
                                .requestMatchers(HttpMethod.POST, "/security/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/security/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/productos/findAll").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/productos/findById/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/productos/save").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/productos/update/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/productos/delete/{id}").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).build();
    }

    //Habilitamos los CORSS
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    FilterRegistrationBean<CorsFilter> filterRegistrationBean(){
//        FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
//        corsFilterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return corsFilterFilterRegistrationBean;
//    }
}
