package productos.API.Service;

import productos.API.Model.DTO.ProveedoresDTO;
import productos.API.Model.Entity.Proveedores;

import java.util.List;

public interface IProveedoresService {
    public Proveedores save(ProveedoresDTO proveedoresDTO);
    public boolean existById(Integer id);
    public List<Proveedores> findAll();
    public Proveedores findById(Integer id);
    public void delete(Proveedores proveedores);


}
