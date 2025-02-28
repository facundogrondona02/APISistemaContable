package productos.API.Controllers;

import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DTO.CategoriaDTO;
import productos.API.Model.DTO.ProductoDTO;
import productos.API.Model.Entity.Categoria;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Payload.Response;
import productos.API.Service.IProductoService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new  ResponseEntity<>(Response.builder().mensaje(errorMessage).object(null).build(), HttpStatus.BAD_REQUEST);
    }


    @PostMapping("producto")
    public ResponseEntity<?> create (@RequestBody @Valid ProductoDTO productoDTO){

        ProductoEntity productoEntityVer = null;

        try {
            productoDTO.setProducto(productoDTO.getProducto().toUpperCase());

            productoEntityVer = productoService.save(productoDTO);

            Categoria categoria = productoEntityVer.getCategoria();

            CategoriaDTO categoriaDTO = new CategoriaDTO(
                    categoria.getID_Categoria(),
                    categoria.getCategoria()
            );


            ProductoDTO producto = ProductoDTO.builder()
                    .Id(productoEntityVer.getId())
                    .Producto(productoEntityVer.getProducto())
                    .Stock(productoEntityVer.getStock())
                    .Stock_Min(productoEntityVer.getStock_Min())
                    .Estado(productoEntityVer.isEstado())
                    .Precio(productoEntityVer.getPrecio())
                    .Descripcion(productoEntityVer.getDescripcion())
                    .Categoria(categoriaDTO)
                    .build();

            return new ResponseEntity<>(Response.builder()
                    .mensaje("Producto creado correctamente!!!")
                    .object(producto)
                    .build(), HttpStatus.CREATED);

        } catch (DataAccessException dtx) {
            return new ResponseEntity<>(
                    Response.builder()
                            .mensaje("")
                            .object(null)
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("producto/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid ProductoDTO productoDTO, @PathVariable Integer id) {
        try {
            if (productoService.existProById(id)) {
                    productoDTO.setProducto(productoDTO.getProducto().toUpperCase());
                    productoDTO.setId(id);
                    ProductoEntity productoEntity = productoService.save(productoDTO);
                    CategoriaDTO categoria = productoDTO.getCategoria();
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
                                    .mensaje("Producto actualizado correctamente!!!")
                                    .object(producto)
                                    .build(),
                            HttpStatus.OK);

            } else {
                return new ResponseEntity<>(
                        Response.builder()
                                .mensaje("No se encontro este producto")
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


    @DeleteMapping("producto")
    public ResponseEntity<?> delete(@RequestBody ArrayList<Integer> ids ){

     ArrayList<ProductoEntity> productos = new ArrayList<>();
        try{
            ids.forEach(id ->{
                  productos.add(productoService.findProById(id));
            });
            productos.forEach(producto -> {
                if(producto != null) {
                    productoService.delete(producto);
                }
            });
            return new ResponseEntity<>(Response.builder().mensaje("Producto borrado correctamente!!!").object(null).build(), HttpStatus.OK);

        }catch (DataAccessException dtx){

            return new ResponseEntity<>(Response.builder().mensaje(dtx.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);

        }


    }


    @GetMapping("productos")
    public ResponseEntity<?> findProAll(){

        Iterable<ProductoEntity> productos = productoService.findProAll();
        if(productos != null){
            return new ResponseEntity<>(Response.builder().mensaje("Productos encontrados con exito!!!").object(productos).build(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Response.builder().mensaje("No hay productos existentes").object(null).build(),HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    @GetMapping("producto/{id}")
    public ResponseEntity<?> findProId(@PathVariable Integer id){
        ProductoEntity producto = null;

        try{
            producto = productoService.findProById(id);
            Categoria categoria = producto.getCategoria();

            // Crear CategoriaDTO sin usar builder
            CategoriaDTO categoriaDTO = new CategoriaDTO(
                    categoria.getID_Categoria(),
                    categoria.getCategoria()
            );


            ProductoDTO productoDTO = ProductoDTO.builder()
                    .Id(producto.getId())
                    .Producto(producto.getProducto())
                    .Stock(producto.getStock())
                    .Stock_Min(producto.getStock_Min())
                    .Estado(producto.isEstado())
                    .Precio(producto.getPrecio())
                    .Descripcion(producto.getDescripcion())
                    .Categoria(categoriaDTO)
                    .build();

            return new ResponseEntity<>(Response.builder()
                    .mensaje("Encontrado correctamente")
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

                Categoria categoria = producto.getCategoria();
                // Crear CategoriaDTO sin usar builder
                CategoriaDTO categoriaDTO = new CategoriaDTO(
                        categoria.getID_Categoria(),
                        categoria.getCategoria()
                );




                ProductoDTO  producctosDTO = ProductoDTO.builder()
                        .Id(producto.getId())
                        .Producto(producto.getProducto())
                        .Stock(producto.getStock())
                        .Stock_Min(producto.getStock_Min())
                        .Estado(producto.isEstado())
                        .Precio(producto.getPrecio())
                        .Descripcion(producto.getDescripcion())
                        .Categoria(categoriaDTO)
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
