package productos.API.Service.Implementaciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.IClienteDAO;
import productos.API.Model.DAO.IProductoDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DAO.IVentasDAO;
import productos.API.Model.DTO.VentasDTO;
import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.User;
import productos.API.Model.Entity.Ventas;
import productos.API.Service.IVentasService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class VentasIMPL implements IVentasService {


    @Autowired
    private IVentasDAO ventasDAO;

    @Autowired
    private  ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IProductoDAO productoDAO;

    @Autowired
    private IClienteDAO clienteDAO;

    public static Date convertirFecha(String fechaStr) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.parse(fechaStr);
    }


    @Transactional
    @Override
    public Ventas save(VentasDTO ventasDTO) throws ParseException {

        ProductoEntity producto = null;
        if(ventasDTO.getProducto() != null){
            producto = productoDAO.findById(ventasDTO.getProducto().getId()) .orElseThrow();

        }
        Cliente cliente = null;
        if(ventasDTO.getCliente() != null){
            cliente = clienteDAO.findById(ventasDTO.getCliente().getId()).orElseThrow();
        }

        String username = obtenerUsernameToken.findUserByToken();

        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ventas ventas = Ventas.builder()
                .Id(ventasDTO.getId())
                .producto(producto)
                .cliente(cliente)
                .cantidad(ventasDTO.getCantidad())
                .precioTotal(ventasDTO.getPrecioTotal())
                .fecha(convertirFecha(ventasDTO.getFecha()))
                .modoDePago(ventasDTO.getModoDePago())
                .pendiente(ventasDTO.getPendiente())
                .tipoTransaccion(ventasDTO.getTipoTransaccion())
                .user(user)
                .build();
         ventasDAO.save(ventas);
        return ventas;

    }

    @Transactional
    @Override
    public void delete(Ventas ventas)  {


        ventasDAO.delete(ventas);
    }
    @Override
    public boolean existById(Integer id){
        return ventasDAO.existsById(id);
    }
    @Transactional
    @Override
    public Iterable<Ventas> findAll() {

        String username = obtenerUsernameToken.findUserByToken();

        return ventasDAO.findByUser_Username(username);

    }

    @Override
    public Ventas findProById(Integer id){
        return ventasDAO.findById(id).orElseThrow();
    }
}
