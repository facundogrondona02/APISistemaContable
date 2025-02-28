package productos.API.Service.Implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import productos.API.Model.DAO.IProveedoresDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.ProveedoresDTO;
import productos.API.Model.Entity.Proveedores;
import productos.API.Model.Entity.User;
import productos.API.Service.IProveedoresService;

import java.util.List;

@Service
public class ProveedoresIMPL implements IProveedoresService {

    @Autowired
    private IProveedoresDAO proveedoresDAO;

    @Autowired
    private ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IUserDAO userDAO;


    @Override
    public Proveedores save(ProveedoresDTO proveedoresDTO){

        String username = obtenerUsernameToken.findUserByToken();
        User user = userDAO.findByUsername(username).orElseThrow();

        try{

            Proveedores proveedores = Proveedores.builder()
                    .id(proveedoresDTO.getId())
                    .nombre(proveedoresDTO.getNombre())
                    .rubro(proveedoresDTO.getRubro())
                    .contacto(proveedoresDTO.getContacto())
                    .user(user)
                    .build();

            Proveedores proveedores1 = proveedoresDAO.save(proveedores);

            return  proveedores1;

        }catch (Exception exception){
               throw exception;
        }
    }
    @Override
    public boolean existById(Integer id){

        return proveedoresDAO.existsById(id);
    }
    @Override
    public List<Proveedores> findAll(){

        String username = obtenerUsernameToken.findUserByToken();

        List<Proveedores> proveedores = proveedoresDAO.findByUser_Username(username);

      return proveedores;

    }
    @Override
    public Proveedores findById(Integer id){

        return proveedoresDAO.findById(id).orElseThrow();

    }
    @Override
    public void delete(Proveedores proveedores){
        proveedoresDAO.delete(proveedores);
    }
}
