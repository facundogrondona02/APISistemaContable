package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ultimas_transacciones")
public class Transacciones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name ="id")
    String Id;

    @Column(name ="tipo")
    String TipoTransaccion;

    @Column(name ="Entidad")
    String Entidad;

    @Column(name ="Objeto")
    String Objeto;

    @Column(name ="Cantidad")
    Integer Cantidad;

    @Column(name ="precio")
    Integer Precio;

    @Column(name ="Fecha")
    Date Fecha;

    @Column(name ="Pendiente")
    Boolean Pendiente;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    User user;

}
