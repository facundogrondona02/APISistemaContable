package productos.API.Service.Implementaciones;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.ICategoriaDAO;
import productos.API.Model.DAO.IProductoDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.CategoriaDTO;
import productos.API.Model.DTO.ProductoDTO;
import productos.API.Model.Entity.Categoria;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.User;
import productos.API.Service.ICategoriaService;
import productos.API.Service.IProductoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoIMPL implements IProductoService {

    @Autowired
    private IProductoDAO productoDAO;
    @Autowired
    private ICategoriaDAO categoriaDAO;

    @Autowired
    private ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IUserDAO userDAO;

    @Transactional
    @Override
    public ProductoEntity save(ProductoDTO productoDTO) {
        try {
            String username = obtenerUsernameToken.findUserByToken();
            User user = userDAO.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

            Categoria categoria = null;
            if (productoDTO.getCategoria() != null) {
                categoria = categoriaDAO.findById(productoDTO.getCategoria().getId()).orElse(null);
                if (categoria == null) {
                    throw new RuntimeException("Categoría no encontrada: " + productoDTO.getCategoria().getId());
                }
            }

            ProductoEntity productoEntity = ProductoEntity.builder()
                    .Id(productoDTO.getId())
                    .Producto(productoDTO.getProducto())
                    .Stock(productoDTO.getStock())
                    .Stock_Min(productoDTO.getStock_Min())
                    .Estado(productoDTO.isEstado())
                    .Precio(productoDTO.getPrecio())
                    .Descripcion(productoDTO.getDescripcion())
                    .Categoria(categoria)
                    .user(user)
                    .build();

            System.out.println("Guardando producto: " + productoEntity);
            ProductoEntity savedProduct = productoDAO.save(productoEntity);
            System.out.println("Producto guardado con ID: " + savedProduct.getId());

            return savedProduct;
        } catch (Exception e) {
            System.err.println("Error guardando producto: " + e.getMessage());
            throw e;
        }
    }


    @Transactional(readOnly = true)
    @Override
    public ProductoEntity findProById(Integer id) {
       return productoDAO.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void delete(ProductoEntity productoEntity) {

        productoDAO.delete(productoEntity);

    }
    @Override
    public ArrayList<ProductoEntity> findProByName(String productoNombre) {
        String nombreEnMayusculas = productoNombre.toUpperCase();
        String username = obtenerUsernameToken.findUserByToken();
        Iterable<ProductoEntity> productos =  productoDAO.findByUser_Username(username);

        ArrayList<ProductoEntity> productosArray = new ArrayList<>();

        for (ProductoEntity producto : productos) {
            // Asegúrate de que getProducto() no devuelva null
            String nombreProducto = producto.getProducto();
            if (nombreProducto != null && nombreProducto.contains(nombreEnMayusculas)) {
                productosArray.add(producto);
            }
        }
        return productosArray;
    }


    @Override
    public boolean existProById(Integer id){
       return productoDAO.existsById(id);
    }


    @Override
    public Iterable<ProductoEntity> findProAll() {
        String username = obtenerUsernameToken.findUserByToken();
        return productoDAO.findByUser_Username(username);
    }


}
