package productos.API.Service.Implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.UserDTO;
import productos.API.Model.Entity.User;
import productos.API.Model.Payload.Response;
import productos.API.Service.IUserService;
import productos.API.User.Role;

import java.util.List;


@Service
public class UserIMPL implements IUserService {

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User signup(UserDTO userDTO) {
        String role = userDTO.getRoles() != null ? userDTO.getRoles() : "USER"; // 👈 Asigna "USER" por defecto si está vacío

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .email(userDTO.getEmail())
                .roles(Role.ADMIN)
                .build();

        return userDAO.save(user);
    }



    @Override
    public Iterable<User> findAll() {
        return userDAO.findAll();
    }
}
