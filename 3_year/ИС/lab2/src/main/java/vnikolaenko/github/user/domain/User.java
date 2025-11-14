package vnikolaenko.github.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "human_being_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable = false)
    private String username;
    @Column(unique=true, nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
}
