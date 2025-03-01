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
     Integer Id;

    @Column(name = "Categoria")
     String Categoria;

    @ManyToOne
    @JoinColumn(name= "Id_User", nullable = true)
    User user;

//    @OneToMany(targetEntity = ProductoEntity.class)
//    private List<ProductoEntity> ListaProductos;

}
