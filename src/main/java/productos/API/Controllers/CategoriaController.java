package productos.API.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DTO.CategoriaDTO;
import productos.API.Model.Entity.Categoria;
import productos.API.Model.Payload.Response;
import productos.API.Service.ICategoriaService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new  ResponseEntity<>(Response.builder().mensaje(errorMessage).object(null).build(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("categoria")
    public ResponseEntity<?> crearCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO){

        Categoria categoriaFinal = null;

        try {
            String nombreMayuscula = categoriaDTO.getCategoria().toUpperCase();
            categoriaDTO.setCategoria(nombreMayuscula);
            categoriaFinal = categoriaService.save(categoriaDTO);
            Categoria categoria = (Categoria) Categoria.builder()
                    .Categoria(categoriaFinal.getCategoria())
                    .user(categoriaFinal.getUser())
                    .build();

            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje("Categoria creada correctamente!!!")
                    .object(categoria)
                    .build(), HttpStatus.CREATED);

        }catch (DataAccessException ex){

            return new ResponseEntity<>( Response
                    .builder()
                    .mensaje(ex.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }


    }

    @PutMapping("categoria")
    public ResponseEntity<?> actualizarCategoria(@RequestBody @Valid  CategoriaDTO categoriaDTO){

        Categoria categoriaFinal = null;

        try {if(categoriaService.existsById(categoriaDTO.getID_Categoria())){

            String nombreMayuscula = categoriaDTO.getCategoria().toUpperCase();
            categoriaDTO.setCategoria(nombreMayuscula);
            categoriaFinal = categoriaService.save(categoriaDTO);
            Categoria categoria = (Categoria)Categoria.builder()
                    .Categoria(categoriaFinal.getCategoria())
                    .build();

            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje("Categoria actualizado correctamente!!!")
                    .object(categoria)
                    .build(), HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>( Response
                    .builder()
                    .mensaje("No se encontro esta categoria")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND
            );
        }
        }catch (DataAccessException ex){

            return new ResponseEntity<>( Response
                    .builder()
                    .mensaje(ex.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @DeleteMapping("categoria")
    public ResponseEntity<?> deleteCategoria(@RequestBody ArrayList<Integer> ids){

        ArrayList<Categoria> categorias = new ArrayList<>();

        try{
            ids.forEach( id ->{
                  categorias.add(categoriaService.findById(id));
            });
            categorias.forEach(categoria -> {
                categoriaService.delete(categoria);
            });
            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje("Categoria borrada correctamente!!!")
                    .object(null)
                    .build(), HttpStatus.OK
            );
        }catch (DataAccessException ex){
            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje(ex.getMessage())
                    .object(null)
                .build(), HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("categorias")
    public ResponseEntity<?> findAll(){
        try {
            Iterable<Categoria> lista   = categoriaService.findAll();
            return new ResponseEntity<>(Response.builder().mensaje("Categorias encontradas con exito!!!").object(lista).build(), HttpStatus.OK);
        }catch (DataAccessException ex){
            return  new ResponseEntity<>(Response.builder().mensaje(ex.getMessage()).object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
//    @GetMapping("categorias/{categoria}")
//    public ResponseEntity<?> findByName(@PathVariable String categoria){
//        try {
//            String catMayus = categoria.toUpperCase();
//            ArrayList<Categoria> listaCate = categoriaService.findByName(catMayus);
//            ArrayList<CategoriaDTO> lista   = new ArrayList<>();
//            for (Categoria cat: listaCate){
//                CategoriaDTO categoriaDTO = CategoriaDTO.builder().ID_Categoria(cat.getID_Categoria()).Categoria(cat.getCategoria()).build();
//                lista.add(categoriaDTO);
//            }
//            return new ResponseEntity<>(Response.builder().mensaje("Buscado con exito").object(lista).build(), HttpStatus.OK);
//        }catch (DataAccessException ex){
//            return  new ResponseEntity<>(Response.builder().mensaje(ex.getMessage()).object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
//        }
//    }

    @GetMapping("categoria/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Categoria categoria = categoriaService.findById(id);

            if (categoria == null) {
                return new ResponseEntity<>(Response.builder()
                        .mensaje("Categoría no encontrada")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

            // Crear CategoriaDTO sin usar builder
            CategoriaDTO categoriaDTO = new CategoriaDTO(
                    categoria.getID_Categoria(),
                    categoria.getCategoria()
            );


            return new ResponseEntity<>(Response.builder()
                    .mensaje("Categoría encontrada con éxito!!!")
                    .object(categoriaDTO)
                    .build(), HttpStatus.OK);
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(Response.builder()
                    .mensaje("Error al acceder a los datos: " + ex.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(Response.builder()
                    .mensaje("Error inesperado: " + ex.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
