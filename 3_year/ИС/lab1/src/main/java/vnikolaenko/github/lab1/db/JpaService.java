package vnikolaenko.github.lab1.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

@Getter
@ApplicationScoped
public class JpaService {
    @PersistenceContext(unitName = "my-persistence-unit")
    private EntityManager entityManager;
}