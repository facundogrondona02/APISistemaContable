package productos.API.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.ClienteDTO;
import productos.API.Model.Payload.Response;
import productos.API.Service.IClienteService;
import productos.API.Service.Implementaciones.ClienteIMPL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDTO clienteDto){
        Cliente clienteCreate = null;
        try{

            String nombreEnMayusculas = clienteDto.getNombre_Completo().toUpperCase();
            clienteDto.setNombre_Completo(nombreEnMayusculas);
             clienteCreate = clienteService.save(clienteDto);
            ClienteDTO cliente = ClienteDTO.builder()
                    .id(clienteCreate.getId())
                    .Mail(clienteCreate.getMail())
                    .Nombre_Completo(clienteCreate.getNombre_Completo())
                    .Telefono(clienteCreate.getTelefono())
                    .Direccion(clienteCreate.getDireccion())
                    .DNI(clienteCreate.getDNI())
                    .build();

            return new ResponseEntity<>(
                    Response.builder()
                            .mensaje("creado correctamente")
                            .object(cliente)
                            .build()
                    , HttpStatus.CREATED);

        }catch (DataAccessException dtx){

            return  new ResponseEntity<>(
                    Response.builder().
                            mensaje(dtx.getMessage()).
                            object(null)
                            .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDTO clienteDto,@PathVariable Integer id){
        Cliente clienteUpdate = null;

        try{
            if(clienteService.existsById(id)) {
                clienteDto.setId(id);
                clienteUpdate = clienteService.save((clienteDto));
                ClienteDTO cliente = ClienteDTO.builder()
                        .id(clienteUpdate.getId())
                        .Mail(clienteUpdate.getMail())
                        .Nombre_Completo(clienteUpdate.getNombre_Completo())
                        .Telefono(clienteUpdate.getTelefono())
                        .Direccion(clienteUpdate.getDireccion())
                        .DNI(clienteUpdate.getDNI())
                        .build();

                return new ResponseEntity<>(
                        Response.builder()
                                .mensaje("actualizado correctamente")
                                .object(cliente)
                                .build()
                        , HttpStatus.CREATED);
            }else{
                return  new ResponseEntity<>(
                        Response.builder().
                                mensaje("no se encontro nada gil").
                                object(null)
                                .build(),
                        HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException dtx){

            return  new ResponseEntity<>(
                    Response.builder().
                            mensaje(dtx.getMessage()).
                            object(null)
                            .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @DeleteMapping("cliente")
    public ResponseEntity<?> delete(@RequestBody ArrayList<Integer> ids){

        ArrayList<Cliente> clientes = new  ArrayList<Cliente>();
        try{
             ids.forEach(id -> {
                     clientes.add(clienteService.findById(id));

            });
             clientes.forEach(cliente -> {
                 if(cliente != null) {
                     clienteService.delete(cliente);
                 }
             });
            return new ResponseEntity<>(Response.builder().mensaje("borrado correctamente").object(null).build(), HttpStatus.NO_CONTENT);

        }catch (DataAccessException dtx){
            return new ResponseEntity<>(
                    Response.builder()
                    .mensaje(dtx.getMessage())
                    .object(null)
                    .build(),HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> select(@PathVariable Integer id){

         Cliente clienteSearch = clienteService.findById(id);

         if(clienteSearch == null){
             return new ResponseEntity<>(
                     Response.builder()
                             .mensaje("no se encontro nada")
                             .object(null)
                             .build(),HttpStatus.NOT_FOUND);
         }

            ClienteDTO cliente = ClienteDTO.builder()
                    .id(clienteSearch.getId())
                    .Mail(clienteSearch.getMail())
                    .Nombre_Completo(clienteSearch.getNombre_Completo())
                    .Telefono(clienteSearch.getTelefono())
                    .build();
            return new ResponseEntity<>(
                    Response.builder()
                            .mensaje("buscado correctamente")
                            .object(cliente)
                            .build()
                    , HttpStatus.OK);

    }
    @GetMapping("clientes")
    public ResponseEntity<?> findAll() {
        Iterable<Cliente> clientes = clienteService.findAll();
        if (clientes != null && clientes.iterator().hasNext()) {
            return new ResponseEntity<>(Response.builder()
                    .mensaje("")
                    .object(clientes)
                    .build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Response.builder()
                    .mensaje("No se encontró nada")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("clienteByName")
    public ResponseEntity<?> findByName(@RequestBody Cliente cliente){

//        Cliente cliente = clienteService.findByName(nombre);
          Cliente cli = clienteService.findByName(cliente.getNombre_Completo());
        if(cli == null){
            return new ResponseEntity<>(
                    Response.builder()
                            .mensaje("no se encontro nada")
                            .object(null)
                            .build(),HttpStatus.NOT_FOUND);
        }



        ClienteDTO clienteDTO = ClienteDTO.builder()
                .id(cli.getId())
                .Mail(cli.getMail())
                .Nombre_Completo(cli.getNombre_Completo())
                .Telefono(cli.getTelefono())
                .build();

       return new ResponseEntity<>(Response.builder().mensaje("").object(clienteDTO).build(), HttpStatus.OK);

   }
}
