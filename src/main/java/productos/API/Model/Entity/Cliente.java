package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String DNI;

}
