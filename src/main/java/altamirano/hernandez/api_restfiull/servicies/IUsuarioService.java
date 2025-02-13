package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface IUsuarioService {
    public abstract List<Usuario> getAll();
    public abstract Usuario save(Usuario usuario);
}
