package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import altamirano.hernandez.api_restfiull.repositories.IRolRepository;
import altamirano.hernandez.api_restfiull.repositories.IUsuarioRepository;
import altamirano.hernandez.api_restfiull.security.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImplUsuarioService implements IUsuarioService{
    @Autowired
    IUsuarioRepository iUsuarioRepository;
    @Autowired
    IRolRepository iRolRepository;
    @Autowired
    SpringSecurityConfig springSecurityConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return (List<Usuario>) iUsuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Usuario save(Usuario usuario) {
        List roles = new ArrayList();
        roles.add("ROLE_ADMIN");
        if (usuario.isAdmin()){
            roles.add("ROLE_USER");
        }

        //Agregamos roles al usuario y encriptamos la password
        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        iUsuarioRepository.save(usuario);
        return usuario;
    }
}
