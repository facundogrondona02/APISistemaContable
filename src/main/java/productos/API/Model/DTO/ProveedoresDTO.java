package productos.API.Model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import productos.API.Model.Entity.User;

@Data
@Builder
public class ProveedoresDTO {

    Integer id;

    String nombre;

    String rubro;

    String contacto;

    User user;
}
