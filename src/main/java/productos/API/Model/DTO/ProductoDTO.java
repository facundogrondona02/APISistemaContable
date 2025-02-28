package productos.API.Model.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import productos.API.Model.Entity.Categoria;

import java.io.Serializable;

@Builder
@ToString
@Data
public class ProductoDTO implements Serializable {


    private Integer Id;

    @NotNull
    @NotBlank(message = "El nombre del producto es obligatorio!!!")
    private String Producto;

    private CategoriaDTO Categoria;

    @NotNull(message = "El stock no puede ser nulo!!!")
    private Integer Stock;

    private Integer Stock_Min;

    private boolean Estado;

    @NotNull(message = "El precio no puede ser nulo!!!")
    private Integer Precio;

    private String Descripcion;

}
