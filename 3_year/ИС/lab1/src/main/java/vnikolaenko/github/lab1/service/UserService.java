package vnikolaenko.github.lab1.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import vnikolaenko.github.lab1.db.repository.UserRepository;
import vnikolaenko.github.lab1.model.User;

@ApplicationScoped
@NoArgsConstructor
public class UserService {
    @Inject
    private UserRepository userRepository;

    public boolean login(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    public boolean register(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null) {
            userRepository.create(user);
            return true;
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
