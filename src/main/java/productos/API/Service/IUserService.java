package productos.API.Service;

import productos.API.Model.DTO.UserDTO;
import productos.API.Model.Entity.User;

import java.util.List;

public interface IUserService {

    User signup(UserDTO userDTO);
    Iterable<User> findAll();
}
