package productos.API.Model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import productos.API.Model.Entity.User;

import java.util.Date;

@Data
@Builder
public class TransaccionesDTO {
    String Id;

    String TipoTransaccion;

    String Entidad;

    String Objeto;

    Integer Cantidad;

    Integer Precio;

    String Fecha;

    Boolean Pendiente;

     User User;
}
