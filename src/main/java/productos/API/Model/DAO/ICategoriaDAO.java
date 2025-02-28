package productos.API.Model.DAO;

import org.springframework.data.repository.CrudRepository;
import productos.API.Model.Entity.Categoria;

import java.util.List;

public interface ICategoriaDAO extends CrudRepository<Categoria,Integer> {

    List<Categoria> findByUser_Username(String username);

}
