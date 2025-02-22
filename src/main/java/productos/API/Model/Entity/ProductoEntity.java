package productos.API.Model.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "productos")
public class ProductoEntity implements Serializable {

    @Id
    @Column(name = "ID_Productos")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer Id;

    @Column(name = "Productos")
     String Producto;


    @Column(name = "Stock")
     Integer Stock;

    @Column(name = "Stock_Min")
     Integer Stock_Min;

    @Column(name = "Estado")
     boolean Estado;

    @Column(name = "Precio")
     Integer Precio;

    @Column(name = "Descripcion")
     String Descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_Categoria", nullable = true)
     Categoria Categoria;

    @ManyToOne
    @JoinColumn(name = "Id_User", nullable = true)
     User user;

}
