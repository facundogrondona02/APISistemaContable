package productos.API.Model.DTO;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO implements Serializable {


    private Integer ID_Categoria;
    private String Categoria;

}
