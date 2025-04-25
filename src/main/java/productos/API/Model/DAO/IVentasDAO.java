package productos.API.Model.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import productos.API.Model.Entity.ProductoEntity;
import productos.API.Model.Entity.Ventas;

import java.util.Date;
import java.util.List;

@Repository
public interface IVentasDAO extends CrudRepository<Ventas, Integer> {

    List<Ventas> findByUser_Username(String username);

    @Query("SELECT v FROM Ventas v JOIN v.user u WHERE v.fecha BETWEEN :start AND :end AND u.username = :username")
    List<Ventas> findByTwoDates(@Param("start") Date start, @Param("end") Date end, @Param("username") String username);


}
