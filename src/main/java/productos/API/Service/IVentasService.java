package productos.API.Service;

import productos.API.Model.DTO.VentasDTO;
import productos.API.Model.Entity.Ventas;

import java.text.ParseException;
import java.util.List;

public interface IVentasService {

    Ventas save(VentasDTO ventasDTO) throws ParseException;
    void delete(Ventas ventas) ;
    Iterable<Ventas> findAll();
    public boolean existById(Integer id);
    public Ventas findProById(Integer id);

}
