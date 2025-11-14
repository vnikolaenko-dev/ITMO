package vnikolaenko.github.shared.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;

@ApplicationScoped
public class JwtGenerator {
    public String generateToken(String username, String role) {
        return Jwt.claims()
                .upn(username)
                .groups(role)
                .issuer("https://example.com/issuer")
                .expiresIn(Duration.ofHours(24))
                .sign(); // Ключ автоматически берется из конфигурации
    }
}
