package productos.API.Service;

import productos.API.Model.DTO.TransaccionesDTO;
import productos.API.Model.Entity.Transacciones;

import java.text.ParseException;
import java.util.List;

public interface ITransaccionesService {
    public List<Transacciones> findAll();
}
