package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="compras")
public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Id_Compras")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "Id_Proveedor")
    Proveedores proveedores;

    @ManyToOne
    @JoinColumn(name = "Id_Producto")
    ProductoEntity producto;

    @Column(name = "Cantidad")
    Integer cantidad;

    @Column(name = "Precio_Unidad")
    Integer precioUnidad;

    @Column(name = "Costo_Envio")
    Integer costoEnvio;

    @Column(name = "Costo_Total")
    Integer costoTotal;

    @Column(name = "Fecha")
    Date fecha;

    @Column(name = "Pendiente")
    Boolean pendiente;

    @Column(name = "Tipo_Transaccion")
    String tipoTransaccion;

    @ManyToOne
    @JoinColumn(name="Id_User")
    User user;
}
