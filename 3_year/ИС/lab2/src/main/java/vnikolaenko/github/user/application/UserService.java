package vnikolaenko.github.user.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vnikolaenko.github.shared.exception.WrongUsernameOrPassword;
import vnikolaenko.github.user.domain.User;
import vnikolaenko.github.user.repository.UserRepository;
import vnikolaenko.github.shared.security.JwtGenerator;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;
    @Inject
    JwtGenerator jwtGenerator;

    public String login(String username, String password) {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new WrongUsernameOrPassword("Invalid username or password");
        } else {
            if (!userEntity.getPassword().equals(password)) {
                throw new WrongUsernameOrPassword("Wrong username or password");
            }
        }
        return jwtGenerator.generateToken(username, userEntity.getRole());
    }

    public String register(String username, String password, String role) {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            userEntity = new User();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setRole(role);
            userRepository.save(userEntity);
        } else {
            throw new WrongUsernameOrPassword("Username already exists");
        }
        return jwtGenerator.generateToken(username, role);
    }
}
