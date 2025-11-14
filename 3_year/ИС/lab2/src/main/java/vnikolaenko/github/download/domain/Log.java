package vnikolaenko.github.download.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "human_being_log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean isSuccess;
    @Column(nullable = false)
    private String username;
    private int count;

    public Log(boolean isSuccess, String username, int count) {
        this.isSuccess = isSuccess;
        this.username = username;
        this.count = count;
    }
}
