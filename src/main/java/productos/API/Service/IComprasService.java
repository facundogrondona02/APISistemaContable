package productos.API.Service;

import productos.API.Model.DTO.ComprasDTO;
import productos.API.Model.Entity.Compras;

import java.util.List;

public interface IComprasService {

    public Compras save(ComprasDTO comprasDTO);
    public boolean existById(Integer id);
    public List<Compras> findAll();
    public void delete(Compras compras);
    public Compras findById(Integer id);

}
