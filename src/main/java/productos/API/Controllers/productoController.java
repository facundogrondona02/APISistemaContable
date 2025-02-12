package productos.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DTO.ProductoDTO;
import productos.API.Model.Entity.Categoria;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Payload.Response;
import productos.API.Service.IProductoService;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class productoController {

    @Autowired
    private IProductoService productoService;


    @PostMapping("producto")
    public ResponseEntity<?> create (@RequestBody ProductoDTO productoDTO){


        ProductoEntity productoEntityVer = null;

        try{
            productoDTO.setProducto(productoDTO.getProducto().toUpperCase());


           if(productoDTO.getProducto() != "" && productoDTO.getPrecio() != null && productoDTO.getStock() != null) {
               productoEntityVer = productoService.save(productoDTO);

               Categoria categoria = productoEntityVer.getCategoria();
               ProductoDTO producto = ProductoDTO.builder()
                       .Id(productoEntityVer.getId())
                       .Producto(productoEntityVer.getProducto())
                       .Stock(productoEntityVer.getStock())
                       .Stock_Min(productoEntityVer.getStock_Min())
                       .Estado(productoEntityVer.isEstado())
                       .Precio(productoEntityVer.getPrecio())
                       .Descripcion(productoEntityVer.getDescripcion())
                       .Categoria(categoria )
                       .build();

               return new ResponseEntity<>(Response.builder()
                       .mensaje("Guardado con exito")
                       .object(producto)
                       .build(), HttpStatus.CREATED);
           }else{
               return new ResponseEntity<>(Response.builder()
                       .mensaje("Hay campos obligatorios vacios")
                       .object(null)
                       .build(), HttpStatus.NOT_FOUND);
           }
        }catch (DataAccessException dtx){

            return new ResponseEntity<>(
                    Response.builder().
                            mensaje(dtx.getMessage()).
                            object(null)
                            .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);

        }

    }


    @PutMapping("producto/{id}")
    public ResponseEntity<?> update(@RequestBody ProductoDTO productoDTO, @PathVariable Integer id) {
        try {
            if (productoService.existProById(id)) {
                if(productoDTO.getProducto() != ""  && productoDTO.getPrecio() != null && productoDTO.getStock() != null ) {
                    productoDTO.setProducto(productoDTO.getProducto().toUpperCase());
                    productoDTO.setId(id);
                    ProductoEntity productoEntity = productoService.save(productoDTO);
                    Categoria categoria = productoEntity.getCategoria();
                    ProductoDTO producto = ProductoDTO.builder()
                            .Id(productoEntity.getId())
                            .Producto(productoEntity.getProducto())
                            .Stock(productoEntity.getStock())
                            .Stock_Min(productoEntity.getStock_Min())
                            .Estado(productoEntity.isEstado())
                            .Precio(productoEntity.getPrecio())
                            .Descripcion(productoEntity.getDescripcion())
                            .Categoria(categoria)
                            .build();

                    return new ResponseEntity<>(
                            Response.builder()
                                    .mensaje("Actualizado con éxito")
                                    .object(producto)
                                    .build(),
                            HttpStatus.OK);
                } else{
                    return new ResponseEntity<>(
                            Response.builder()
                                    .mensaje("Hay campos obligatorios vacios")
                                    .object(null)
                                    .build(),
                            HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(
                        Response.builder()
                                .mensaje("Producto no encontrado")
                                .object(null)
                                .build(),
                        HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException dtx) {
            return new ResponseEntity<>(
                    Response.builder()
                            .mensaje("Error de acceso a datos: " + dtx.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Response.builder()
                            .mensaje("Error inesperado: " + e.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("producto/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id ){


        try{
            ProductoEntity product = productoService.findProById(id);
            productoService.delete(product);

            return new ResponseEntity<>(Response.builder().mensaje("Borrado con exito").object(null).build(), HttpStatus.NO_CONTENT);

        }catch (DataAccessException dtx){

            return new ResponseEntity<>(Response.builder().mensaje(dtx.getMessage()).object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);

        }


    }


    @GetMapping("productos")
    public ResponseEntity<?> findProAll(){

        Iterable<ProductoEntity> productos = productoService.findProAll();
        if(productos != null){
            return new ResponseEntity<>(Response.builder().mensaje("Encontrados con exito").object(productos).build(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Response.builder().mensaje("No se encontraron productos").object(null).build(),HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    @GetMapping("producto/{id}")
    public ResponseEntity<?> findProId(@PathVariable Integer id){
        ProductoEntity producto = null;

        try{
            producto = productoService.findProById(id);
            Categoria categoria = producto.getCategoria();
            ProductoDTO productoDTO = ProductoDTO.builder()
                    .Id(producto.getId())
                    .Producto(producto.getProducto())
                    .Stock(producto.getStock())
                    .Stock_Min(producto.getStock_Min())
                    .Estado(producto.isEstado())
                    .Precio(producto.getPrecio())
                    .Descripcion(producto.getDescripcion())
                    .Categoria(producto.getCategoria())
                    .build();

            return new ResponseEntity<>(Response.builder()
                    .mensaje("Encontrado con exito")
                    .object(productoDTO)
                    .build(), HttpStatus.OK);

        }catch (DataAccessException dtx){
            return new ResponseEntity<>(Response.builder()
                    .mensaje(dtx.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }


    }

    @GetMapping("productoName/{nombre}")
    public ResponseEntity<?> findPorByName(@PathVariable String nombre){


        try{
           String nombren = nombre.toUpperCase();
            ArrayList<ProductoEntity> productosEntity = productoService.findProByName(nombren);
            ArrayList<ProductoDTO> productosFinal = new ArrayList<ProductoDTO>();
            for(ProductoEntity producto: productosEntity){

                ProductoDTO  producctosDTO = ProductoDTO.builder()
                        .Id(producto.getId())
                        .Producto(producto.getProducto())
                        .Stock(producto.getStock())
                        .Stock_Min(producto.getStock_Min())
                        .Estado(producto.isEstado())
                        .Precio(producto.getPrecio())
                        .Descripcion(producto.getDescripcion())
                        .Categoria(producto.getCategoria())
                        .build();

                productosFinal.add(producctosDTO);
          }


            return new ResponseEntity<>(Response.builder().mensaje("Encontrado con exito").object(productosFinal).build(), HttpStatus.OK);


        }catch (DataAccessException dtx){

                return new ResponseEntity<>(Response.builder()
                        .mensaje("No se encontro" + dtx.getMessage())
                        .object(null)
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }
}
