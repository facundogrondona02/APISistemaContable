package productos.API.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO implements Serializable {


    private Integer ID_Categoria;

    @NonNull
    @NotBlank(message = "No puedo ser nulo!!!")
    private String Categoria;

}
