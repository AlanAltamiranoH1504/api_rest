package altamirano.hernandez.api_restfiull.security;

import io.jsonwebtoken.Jwts;

import java.security.Key;

public class TokenJWTConfig {
    public static final Key JWT_SECRET = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
}
