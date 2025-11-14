package vnikolaenko.github.user.resource.dto;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
