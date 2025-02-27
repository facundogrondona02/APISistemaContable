package productos.API.Model.DAO;

import org.springframework.data.repository.CrudRepository;
import productos.API.Model.Entity.Compras;

import java.util.List;

public interface IComprasDAO extends CrudRepository<Compras, Integer> {

    List<Compras> findByUser_Username(String username);
}
