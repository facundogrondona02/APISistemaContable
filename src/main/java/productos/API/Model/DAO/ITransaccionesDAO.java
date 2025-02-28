package productos.API.Model.DAO;

import org.springframework.data.repository.CrudRepository;
import productos.API.Model.Entity.Transacciones;

import java.util.List;

public interface ITransaccionesDAO extends CrudRepository<Transacciones, String> {

    List<Transacciones> findByUser_Username(String username);

}
