package productos.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productos.API.Model.DTO.VentasDTO;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.Ventas;
import productos.API.Model.Payload.Response;
import productos.API.Service.IVentasService;
import productos.API.Service.Implementaciones.VentasIMPL;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class VentasController {

    @Autowired
    private IVentasService ventasService;


    @PostMapping("venta")
    public ResponseEntity<?> create(@RequestBody VentasDTO ventasDTO) {

     try {
         Ventas ventas = ventasService.save(ventasDTO);
         return new ResponseEntity<>(Response.builder().mensaje("Venta creada correctamente").object(ventas).build(), HttpStatus.OK);

     }catch (Exception exception){
       return new ResponseEntity<>(Response.builder().mensaje("Error: " + exception.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);

     }
    }
    @PutMapping("venta/{id}")
    public ResponseEntity<?> update(@RequestBody VentasDTO ventasDTO, @PathVariable Integer id) {
        try {
            // Verificamos si la venta existe por ID
            if (ventasService.existById(id)) {
                ventasDTO.setId(id); // Asignamos el ID para asegurar que estamos actualizando la venta correcta
                Ventas venta = ventasService.save(ventasDTO);  // Usamos save, como en el código de productos

                return new ResponseEntity<>(Response.builder()
                        .mensaje("Venta actualizada correctamente!")
                        .object(venta)
                        .build(), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(Response.builder()
                        .mensaje("Venta no encontrada")
                        .object(null)
                        .build(), HttpStatus.NOT_FOUND);
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(Response.builder()
                    .mensaje("Error inesperado: " + exception.getMessage())
                    .object(null)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Date convertirFecha(String fechaStr) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.parse(fechaStr);
    }
    @GetMapping("ventas")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false)String start,
            @RequestParam(required = false)String  end
            ){
        try{
            List<Ventas> ventas = StreamSupport
                    .stream(ventasService.findAll().spliterator(), false)
                    .collect(Collectors.toList());

            if(start != "" && end != ""){
            Date dateStart = convertirFecha(start);
            Date dateEnd = convertirFecha(end);
              if(dateStart != null && dateEnd != null) {
                  ventas = ventasService.fincBytwoDates(dateStart, dateEnd);
              }
            }
            return new ResponseEntity<>(Response.builder().mensaje("Ventas encontradas correctamente").object(ventas).build(), HttpStatus.OK);

        }catch (Exception exception){
            return new ResponseEntity<>(Response.builder().mensaje("No se enxontraron las ventas").object(null).build(), HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("venta")
    public ResponseEntity<?> delete(@RequestBody ArrayList<Integer> ids ){

        ArrayList<Ventas> ventas = new ArrayList<>();
        try{
            ids.forEach(id ->{
                ventas.add(ventasService.findProById(id));
            });
            ventas.forEach(venta -> {
                if(venta != null) {
                    ventasService.delete(venta);
                }
            });
            return new ResponseEntity<>(Response.builder().mensaje("Ventas eliminadas correctamente!!!").object(null).build(), HttpStatus.OK);

        }catch (DataAccessException dtx){

            return new ResponseEntity<>(Response.builder().mensaje(dtx.getMessage()).object(null).build(), HttpStatus.BAD_REQUEST);

        }


    }
}
