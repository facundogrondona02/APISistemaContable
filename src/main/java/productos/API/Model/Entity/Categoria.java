package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import productos.API.Model.DTO.CategoriaDTO;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "categorias")
public class Categoria extends CategoriaDTO implements Serializable {

    @Id
    @Column(name = "ID_Categoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_Categoria;

    @Column(name = "Categoria")
    private String Categoria;

}
