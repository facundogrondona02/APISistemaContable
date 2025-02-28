package productos.API.Model.Entity;

import jakarta.validation.constraints.*;
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
    @NotBlank(message = "El nombre es obligatorio!!!")
    private String Nombre_Completo;

    @NotNull
    @Email(message = "El mail tiene que ser valido!!!")
    private String Mail;

    @Size(min = 10,max = 10, message = "El telefono tiene que tener 10 caracteres!!!")
    private String Telefono;

    private String Direccion;

    @Size(min = 8,max = 8, message = "El telefono tiene que tener 10 caracteres!!!")
    private String Dni;

}
