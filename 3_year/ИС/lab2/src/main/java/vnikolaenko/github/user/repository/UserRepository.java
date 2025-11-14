package vnikolaenko.github.user.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import vnikolaenko.github.user.domain.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }

    @Transactional
    public void save(User userEntity) {
        persist(userEntity);
    }
}
