package productos.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productos.API.Model.Entity.Transacciones;
import productos.API.Model.Payload.Response;
import productos.API.Service.ITransaccionesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class TransaccionesController {

    @Autowired
    private ITransaccionesService transaccionesService;

    @GetMapping("transacciones")
    public ResponseEntity<?> findAll(){

        try{
            List<Transacciones> transacciones = transaccionesService.findAll();

            return  new ResponseEntity<>(Response.builder().mensaje("Transiciones!!!").object(transacciones).build(), HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.OK);

        }
    }

}
