package productos.API.Model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import productos.API.Model.Entity.User;

import java.util.Date;

@Data
@Builder
public class TransaccionesDTO {
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
    productos.API.Model.Entity.User User;
}
