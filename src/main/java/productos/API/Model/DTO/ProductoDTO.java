package productos.API.Model.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

    private String Producto;

    private CategoriaDTO Categoria;

    private Integer Stock;

    private Integer Stock_Min;

    private boolean Estado;

    private Integer Precio;

    private String Descripcion;

}
