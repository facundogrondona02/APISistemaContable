package productos.API.Service.Implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productos.API.Model.DAO.ITransaccionesDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.TransaccionesDTO;
import productos.API.Model.Entity.Transacciones;
import productos.API.Model.Entity.User;
import productos.API.Service.ITransaccionesService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransaccionesIMPL implements ITransaccionesService {

    @Autowired
    private ITransaccionesDAO transaccionesDAO;

    @Autowired
    private ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IUserDAO userDAO;

    public static Date convertirFecha(String fechaStr) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.parse(fechaStr);
    }

    @Override
    public List<Transacciones> findAll(){

        String username = obtenerUsernameToken.findUserByToken();

        List<Transacciones> transacciones = transaccionesDAO.findByUser_Username(username);
        return transacciones;


    }





}
