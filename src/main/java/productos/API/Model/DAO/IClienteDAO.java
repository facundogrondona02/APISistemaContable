package productos.API.Model.DAO;

import org.springframework.data.repository.CrudRepository;
import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.User;

import java.util.List;

public interface IClienteDAO extends CrudRepository<Cliente, Integer> {

    List<Cliente> findByUser_Username(String username);

}
