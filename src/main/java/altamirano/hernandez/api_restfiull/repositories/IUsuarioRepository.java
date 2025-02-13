package altamirano.hernandez.api_restfiull.repositories;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {
}
