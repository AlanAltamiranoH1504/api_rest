package altamirano.hernandez.api_restfiull.repositories;

import altamirano.hernandez.api_restfiull.entities.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IRolRepository extends CrudRepository<Rol, Integer> {

    //Metodo personalizado que busca un rol por un nombre dado
    @Query("SELECT r FROM Rol r WHERE r.nombre =:nombre")
    public Rol rolDB(@Param("nombre") String nombre);
}
