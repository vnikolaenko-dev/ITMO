package vnikolaenko.github.lab1.db.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.db.JpaService;
import vnikolaenko.github.lab1.model.User;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.TypedQuery;

@ApplicationScoped
@NoArgsConstructor
public class UserRepository {
    @Inject
    private JpaService jpaService;

    @Transactional
    // Создание пользователя
    public User create(User user) {
        try {
            jpaService.getEntityManager().persist(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }

    @Transactional
    // Удаление пользователя по ID
    public void delete(Long id) {
        try {
            User user = jpaService.getEntityManager().find(User.class, id);
            if (user != null) {
                jpaService.getEntityManager().remove(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user with id: " + id, e);
        }
    }

    // Поиск пользователя по ID
    public Optional<User> findById(Long id) {
        try {
            User user = jpaService.getEntityManager().find(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find user by id: " + id, e);
        }
    }

    // Поиск всех пользователей
    public List<User> findAll() {
        try {
            TypedQuery<User> query = jpaService.getEntityManager()
                    .createQuery("SELECT u FROM human_being_user u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all users", e);
        }
    }

    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = jpaService.getEntityManager()
                    .createQuery("SELECT u FROM human_being_user u where u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}