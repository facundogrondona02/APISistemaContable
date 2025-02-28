package productos.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DTO.ComprasDTO;
import productos.API.Model.Entity.Compras;
import productos.API.Model.Payload.Response;
import productos.API.Service.IComprasService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ComprasController {

    @Autowired
    private IComprasService comprasService;

    @PostMapping("compra")
    public ResponseEntity<?> create(@RequestBody ComprasDTO comprasDTO){

        try{
            Compras compras = comprasService.save(comprasDTO);

            return  new ResponseEntity<>(Response.builder().mensaje("Compra credad correctamente!!!").object(compras).build(), HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);

        }

    }

    @PutMapping("compra/{id}")
    public ResponseEntity<?> update(@RequestBody ComprasDTO comprasDTO, @PathVariable Integer id){

        try{
            Compras compras = null;
           if(comprasService.existById(id)){
               comprasDTO.setId(id);
                compras = comprasService.save(comprasDTO);
           }else{
               return  new ResponseEntity<>(Response.builder().mensaje("No se encontro esta compra").object(null).build(), HttpStatus.OK);

           }
            return  new ResponseEntity<>(Response.builder().mensaje("Compra credad correctamente!!!").object(compras).build(), HttpStatus.OK);

        }catch (Exception e){
            return  new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("compras")
    public ResponseEntity<?> findAll(){
        List<Compras> compras = comprasService.findAll();
//        if(!compras.isEmpty()){
            return new ResponseEntity<>(Response.builder().mensaje("Compras encontradas correctamente!!!").object(compras).build(), HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(Response.builder().mensaje("No se encontraron las compras!!!").object(null).build(), HttpStatus.BAD_REQUEST);
//        }
    }


    @DeleteMapping("compras")
    public ResponseEntity<?> delete(@RequestBody ArrayList<Integer> ids){

        try{
            ArrayList<Compras> compras = new ArrayList<>();

            ids.forEach(id -> {
                Compras compras1 = comprasService.findById(id);
                compras.add(compras1);
            });

            compras.forEach(compra ->{
                comprasService.delete(compra);

            });

            return new ResponseEntity<>(Response.builder().mensaje("Compra/s eliminadas correctamente").object(null).build(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);

        }

    }
}
