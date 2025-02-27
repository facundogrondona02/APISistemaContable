package productos.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DTO.ProveedoresDTO;
import productos.API.Model.Entity.Proveedores;
import productos.API.Model.Payload.Response;
import productos.API.Service.IProveedoresService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProveedoresContrroller {

    @Autowired
    private IProveedoresService proveedoresService;

    @PostMapping("proveedor")
    public ResponseEntity<?> create(@RequestBody ProveedoresDTO proveedoresDTO){

        try{

            Proveedores proveedores = proveedoresService.save(proveedoresDTO);

            return new ResponseEntity<>(Response.builder().mensaje("Proveedor creado con exito!!!").object(proveedores).build(), HttpStatus.OK);


        }catch (Exception e){

            return new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.OK);


        }
    }

    @PutMapping("proveedor/{id}")
    public ResponseEntity<?> update(@RequestBody ProveedoresDTO proveedoresDTO, @PathVariable Integer id){

        try{
            Proveedores proveedores = null;
            if(proveedoresService.existById(id)){
                proveedoresDTO.setId(id);

                 proveedores = proveedoresService.save(proveedoresDTO);
            }else{
            return new ResponseEntity<>(Response.builder().mensaje("No se encontro este proveedor!!!").object(null).build(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(Response.builder().mensaje("Proveedor actualizado correctament!!!").object(proveedores).build(), HttpStatus.OK);

        }catch (Exception e){
            new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(Response.builder().mensaje("Hubo un error inesperado, intentelo nuevamente o intentelo mas tarde").object(null).build(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("proveedores")
    public ResponseEntity<?> findAll(){
        try{

            Iterable<Proveedores> proveedores = proveedoresService.findAll();
            return  new ResponseEntity<>(Response.builder().mensaje("Porveedores encontrados correctamente!!!").object(proveedores).build(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("proveedores")
    public ResponseEntity<?> delete(@RequestBody ArrayList<Integer> ids){

        try{
            ArrayList<Proveedores> proveedores = new ArrayList<>();

            ids.forEach(id ->{
              Proveedores proveedores1 = proveedoresService.findById(id);
              proveedores.add(proveedores1);
            });

            proveedores.forEach(proveedor -> {
                proveedoresService.delete(proveedor);
            });
            return new ResponseEntity<>(Response.builder().mensaje("Proveedor/es eliminados con exito").object(null).build(), HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>(Response.builder().mensaje(e.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);
        }
    }

}
