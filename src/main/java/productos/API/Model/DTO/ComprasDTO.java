package productos.API.Model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.Proveedores;
import productos.API.Model.Entity.User;

import java.util.Date;

@Data
@Builder
public class ComprasDTO {

    Integer id;

    Proveedores proveedores;

    ProductoEntity producto;

    Integer cantidad;

    Integer precioUnidad;

    Integer costoEnvio;

    Integer costoTotal;

    String fecha;

    Boolean pendiente;

    String tipoTransaccion;

    User user;
}
