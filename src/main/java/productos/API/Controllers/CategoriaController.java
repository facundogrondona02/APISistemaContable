package productos.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("categoria")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDTO categoriaDTO){

        Categoria categoriaFinal = null;

        try {
            String nombreMayuscula = categoriaDTO.getCategoria().toUpperCase();
            categoriaDTO.setCategoria(nombreMayuscula);
            categoriaFinal = categoriaService.save(categoriaDTO);
            Categoria categoria = Categoria.builder()
                    .Categoria(categoriaFinal.getCategoria())
                    .build();

            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje("Creado con exito")
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
    public ResponseEntity<?> actualizarCategoria(@RequestBody CategoriaDTO categoriaDTO){

        Categoria categoriaFinal = null;

        try {if(categoriaService.existsById(categoriaDTO.getId())){

            String nombreMayuscula = categoriaDTO.getCategoria().toUpperCase();
            categoriaDTO.setCategoria(nombreMayuscula);
            categoriaFinal = categoriaService.save(categoriaDTO);
            Categoria categoria = Categoria.builder()
                    .Categoria(categoriaFinal.getCategoria())
                    .build();

            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje("Actualizado con exito")
                    .object(categoria)
                    .build(), HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>( Response
                    .builder()
                    .mensaje("no existe el produccto que queres modificar")
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
    public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaDTO categoriaDTO){

        try{
            Categoria categoria = categoriaService.findById(categoriaDTO.getId());
            categoriaService.delete(categoria);
            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje("Eliminado con exito")
                    .object(null)
                    .build(), HttpStatus.NO_CONTENT
            );
        }catch (DataAccessException ex){
            return new ResponseEntity<>(Response
                    .builder()
                    .mensaje(ex.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("categorias")
    public ResponseEntity<?> findAll(){
        try {
            Iterable<Categoria> lista   = categoriaService.findAll();
            return new ResponseEntity<>(Response.builder().mensaje("Buscado con exito").object(lista).build(), HttpStatus.OK);
        }catch (DataAccessException ex){
            return  new ResponseEntity<>(Response.builder().mensaje(ex.getMessage()).object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
    @GetMapping("categorias/{categoria}")
    public ResponseEntity<?> findByName(@PathVariable String categoria){
        try {
            String catMayus = categoria.toUpperCase();
            ArrayList<Categoria> listaCate = categoriaService.findByName(catMayus);
            ArrayList<CategoriaDTO> lista   = new ArrayList<>();
            for (Categoria cat: listaCate){
                CategoriaDTO categoriaDTO = CategoriaDTO.builder().id(cat.getID_Categoria()).Categoria(cat.getCategoria()).build();
                lista.add(categoriaDTO);
            }
            return new ResponseEntity<>(Response.builder().mensaje("Buscado con exito").object(lista).build(), HttpStatus.OK);
        }catch (DataAccessException ex){
            return  new ResponseEntity<>(Response.builder().mensaje(ex.getMessage()).object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
