package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
}
