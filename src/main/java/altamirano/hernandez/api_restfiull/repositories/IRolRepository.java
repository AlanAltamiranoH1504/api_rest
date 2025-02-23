package altamirano.hernandez.api_restfiull.repositories;

import altamirano.hernandez.api_restfiull.entities.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IRolRepository extends CrudRepository<Rol, Integer> {

    @Query("SELECT r FROM Rol r WHERE r.nombre =:nombre")
    Rol findByName(@Param("nombre") String nombre);
}
