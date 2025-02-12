package productos.API.Model.Entity;

import jakarta.validation.constraints.NotNull;
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
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    private Integer id;

    @NotNull
    private String Nombre_Completo;

    @NotNull
    private String Mail;

    private String Telefono;

    private String Direccion;

    private String Dni;

}
