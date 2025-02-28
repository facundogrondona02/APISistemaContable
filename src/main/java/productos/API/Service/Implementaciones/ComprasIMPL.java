package productos.API.Service.Implementaciones;

import jakarta.websocket.OnClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.IComprasDAO;
import productos.API.Model.DAO.IProductoDAO;
import productos.API.Model.DAO.IProveedoresDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.ComprasDTO;
import productos.API.Model.DTO.TransaccionesDTO;
import productos.API.Model.Entity.Compras;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.Proveedores;
import productos.API.Model.Entity.User;
import productos.API.Service.IComprasService;
import productos.API.Service.ITransaccionesService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComprasIMPL implements IComprasService {

    @Autowired
    private IComprasDAO comprasDAO;

    @Autowired
    private ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IProveedoresDAO proveedoresDAO;

    @Autowired
    private IProductoDAO productoDAO;

    @Autowired
    private IUserDAO userDAO;


    public static Date convertirFecha(String fechaStr) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.parse(fechaStr);
    }

    @Transactional
    @Override
    public Compras save(ComprasDTO comprasDTO){

        try{
            Proveedores proveedores = proveedoresDAO.findById(comprasDTO.getProveedores().getId()).orElseThrow();
            System.out.println(productoDAO.findById(comprasDTO.getProducto().getId()).orElse(null));


            ProductoEntity   product = productoDAO.findById(comprasDTO.getProducto().getId()) .orElseThrow();



            String username = obtenerUsernameToken.findUserByToken();
            User user = userDAO.findByUsername(username).orElseThrow();

            Compras compras = Compras.builder()
                    .id(comprasDTO.getId())
                    .proveedores(proveedores)
                    .producto(product)
                    .cantidad(comprasDTO.getCantidad())
                    .precioUnidad(comprasDTO.getPrecioUnidad())
                    .costoEnvio(comprasDTO.getCostoEnvio())
                    .costoTotal(comprasDTO.getCostoTotal())
                    .fecha(convertirFecha(comprasDTO.getFecha()))
                    .pendiente(comprasDTO.getPendiente())
                    .tipoTransaccion(comprasDTO.getTipoTransaccion())
                    .user(user)
                    .build();



            return comprasDAO.save(compras);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean existById(Integer id){
        return comprasDAO.existsById(id);
    }

    @Override
    public List<Compras> findAll(){

        String username = obtenerUsernameToken.findUserByToken();


        return comprasDAO.findByUser_Username(username);

    }

    @Override
    public void delete(Compras compras){
        comprasDAO.delete(compras);
    }

    @Override
    public Compras findById(Integer id){
        return comprasDAO.findById(id).orElseThrow();
    }

}
