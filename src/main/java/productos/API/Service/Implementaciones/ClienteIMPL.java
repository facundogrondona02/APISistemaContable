package productos.API.Service.Implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.IClienteDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.ClienteDTO;
import productos.API.Model.Entity.User;
import productos.API.Service.IClienteService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class ClienteIMPL implements IClienteService {

    @Autowired
    private IClienteDAO IclienteDao;

    @Autowired
    private ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IUserDAO userDAO;

    @Transactional
    @Override
    public Cliente save(ClienteDTO clienteDto) {
        String username =obtenerUsernameToken.findUserByToken();
        User user = userDAO.findByUsername(username).orElseThrow();
        Cliente cliente = Cliente.builder()
                .id(clienteDto.getId())
                .Mail(clienteDto.getMail())
                .Nombre_Completo(clienteDto.getNombre_Completo())
                .Telefono(clienteDto.getTelefono())
                .Direccion(clienteDto.getDireccion())
                .Dni(clienteDto.getDni())
                .user(user)
                .build();

        return IclienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Integer id) {
        return IclienteDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Cliente cliente) {
        IclienteDao.delete((cliente));
    }

    @Override
    public boolean existsById(Integer id) {
        return IclienteDao.existsById(id);
    }

    @Override
    public Iterable<Cliente> findAll() {
         String username =obtenerUsernameToken.findUserByToken();
        return IclienteDao.findByUser_Username(username);
    }

    @Override
    public Cliente findByName(String nombre) {

        Iterable<Cliente> clientes = IclienteDao.findAll();
        Cliente clienteEncontrado = null;
        for(Cliente cliente : clientes){

            if(cliente.getNombre_Completo().equals(nombre)){
                clienteEncontrado =cliente;
                break;
            }
        }
       return clienteEncontrado;
    }
}
