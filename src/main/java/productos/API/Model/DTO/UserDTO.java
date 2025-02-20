package productos.API.Model.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {

    Integer Id;

    String username;

    String firstname;

    String lastname;

    String password;

    String email;

    String roles;

}
