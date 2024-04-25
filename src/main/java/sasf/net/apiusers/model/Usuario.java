package sasf.net.apiusers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String rol;
}
