package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @Column(name = "ID_Cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Nombre_Completo")
    private String Nombre_Completo;

    @Column(name = "Mail")
    private String Mail;

    @Column(name = "Telefono")
    private String Telefono;

    @Column(name = "Direccion")
    private String Direccion;

    @Column(name= "DNI")
    private String Dni;

    @ManyToOne // Relación muchos a uno con User
    @JoinColumn(name = "Id_User", referencedColumnName = "Id") // Relación con la columna `Id_User` de `Cliente`, que es la clave foránea
    private User user;
}
