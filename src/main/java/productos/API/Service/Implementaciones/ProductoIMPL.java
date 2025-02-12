package productos.API.Service.Implementaciones;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.ICategoriaDAO;
import productos.API.Model.DAO.IProductoDAO;
import productos.API.Model.DTO.ProductoDTO;
import productos.API.Model.Entity.Categoria;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Service.ICategoriaService;
import productos.API.Service.IProductoService;

import java.util.ArrayList;

@Service
public class ProductoIMPL implements IProductoService {

    @Autowired
    private IProductoDAO productoDAO;
    @Autowired
    private ICategoriaDAO categoriaDAO;

    @Transactional
    @Override
    public ProductoEntity save(ProductoDTO productoDTO) {

        Categoria categoria = null;

        if(productoDTO.getCategoria() != null) {
             categoria = categoriaDAO.findById(productoDTO.getCategoria().getID_Categoria()).orElse(null);
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
               .build();

       return productoDAO.save(productoEntity);

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
        Iterable<ProductoEntity> productos = productoDAO.findAll();
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
        return productoDAO.findAll();
    }


}
