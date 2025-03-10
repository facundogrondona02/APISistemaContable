package productos.API.Model.DAO;

import org.springframework.data.repository.CrudRepository;
import productos.API.Model.Entity.ProductoEntity;

import java.util.ArrayList;
import java.util.List;

public interface IProductoDAO extends CrudRepository<ProductoEntity, Integer> {

    List<ProductoEntity> findByUser_Username(String username);
}
