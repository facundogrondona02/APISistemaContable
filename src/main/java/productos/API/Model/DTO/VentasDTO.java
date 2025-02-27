package productos.API.Model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.ClienteDTO;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.User;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentasDTO implements Serializable {

    Integer Id;

    ProductoDTO producto;

    ClienteDTO cliente;

    Integer cantidad;

    Integer precioTotal;

    String fecha;

    String modoDePago;

    Boolean pendiente;

    String tipoTransaccion;

}
