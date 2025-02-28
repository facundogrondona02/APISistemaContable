package productos.API.Model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "proveedores")
public class Proveedores implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Id_Proveedores")
    Integer id;

    @Column(name ="Nombre")
    String nombre;

    @Column(name ="Rubro")
    String rubro;

    @Column(name ="Contacto")
    String contacto;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    User user;
}
