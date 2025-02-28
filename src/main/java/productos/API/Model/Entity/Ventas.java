package productos.API.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import productos.API.Model.Entity.Cliente;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.User;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name= "ventas")
@Entity
public class Ventas implements Serializable {

    @Id
    @Column(name ="Id_Ventas")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;

    @ManyToOne
    @JoinColumn(name = "Id_Producto")
    ProductoEntity producto;

    @ManyToOne
    @JoinColumn(name= "Id_Cliente")
    Cliente cliente;

    @Column(name = "Cantidad")
    Integer cantidad;

    @Column(name = "Precio_Total")
    Integer precioTotal;

    @Column(name = "Fecha")
    Date fecha;

    @Column(name = "Modo_De_Pago")
    String modoDePago;


    @Column(name ="Pendiente")
    Boolean pendiente;

    @Column(name = "Tipo_Transaccion")
    String tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    User user;



}