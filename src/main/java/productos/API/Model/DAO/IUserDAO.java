package productos.API.Model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import productos.API.Model.Entity.User;

import java.util.Optional;

@Repository
public interface IUserDAO extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
