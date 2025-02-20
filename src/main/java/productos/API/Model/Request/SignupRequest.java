package productos.API.Model.Request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    String username;
    String password;
    String firstname;
    String lastname;
    String email;
    String roles;
}
