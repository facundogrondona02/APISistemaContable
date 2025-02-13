package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import productos.API.Model.DTO.CategoriaDTO;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

    @Id
    @Column(name = "ID_Categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_Categoria;

    @Column(name = "Categoria")
    private String Categoria;

//    @OneToMany(targetEntity = ProductoEntity.class)
//    private List<ProductoEntity> ListaProductos;

}
