package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transacciones")
public class Transacciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id_Transacciones")
    Integer Id;

    @Column(name ="Entidad")
    String Entidad;

    @Column(name ="Objeto")
    String Objeto;

    @Column(name ="Cantidad")
    Integer Cantidad;

    @Column(name ="Dinero")
    Integer Dinero;

    @Column(name ="Fecha")
    Date Fecha;

    @Column(name ="Pendiente")
    boolean Pendiente;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    User User;

}
