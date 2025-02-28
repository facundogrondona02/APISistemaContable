package productos.API.Model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import productos.API.Model.Entity.Categoria;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO extends productos.API.Model.Entity.Categoria implements Serializable {


    private Integer ID_Categoria;

    @NonNull
    @NotBlank(message = "El nombre de la categoria es obligatorio!!!")
    private String Categoria;

}
