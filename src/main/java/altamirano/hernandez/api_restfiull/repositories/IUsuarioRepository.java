package altamirano.hernandez.api_restfiull.repositories;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.nombre =:nombre")
    Usuario findByNombre(@Param("nombre") String nombre);
}
