package productos.API.Model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.Ventas;

import java.util.List;

@Repository
public interface IVentasDAO extends CrudRepository<Ventas, Integer> {

    List<Ventas> findByUser_Username(String username);

}
