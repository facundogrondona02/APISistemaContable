package productos.API.Model.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
public class ClienteDTO implements Serializable {

    private Integer id;

    private String Nombre_Completo;

    private String Mail;

    private String Telefono;

    private String Direccion;

    private String Dni;

}
