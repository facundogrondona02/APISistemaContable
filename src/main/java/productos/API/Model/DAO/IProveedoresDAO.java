package productos.API.Model.DAO;

import org.springframework.data.repository.CrudRepository;
import productos.API.Model.Entity.Proveedores;

import java.util.List;

public interface IProveedoresDAO extends CrudRepository<Proveedores,Integer> {

    List<Proveedores> findByUser_Username(String username);
}
