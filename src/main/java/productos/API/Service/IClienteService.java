package productos.API.Service;

import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.ClienteDTO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public interface IClienteService {

    Cliente save(ClienteDTO cliente);
    Cliente findById(Integer id);
    void delete(Cliente cliente);
    boolean existsById(Integer id);
    Iterable<Cliente>  findAll();
    Cliente findByName(String nombre);
}
