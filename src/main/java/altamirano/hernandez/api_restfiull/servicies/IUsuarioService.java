package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Usuario;

import java.util.List;
import java.util.Map;

public interface IUsuarioService {
    List<Usuario> findAll();
    Map<String, Object> save(Usuario usuario);
}
