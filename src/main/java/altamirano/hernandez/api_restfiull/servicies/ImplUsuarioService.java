package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Rol;
import altamirano.hernandez.api_restfiull.entities.Usuario;
import altamirano.hernandez.api_restfiull.repositories.IRolRepository;
import altamirano.hernandez.api_restfiull.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImplUsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    @Autowired
    private IRolRepository iRolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        List<Usuario> usuarios = (List<Usuario>) iUsuarioRepository.findAll();
        return usuarios;
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> save(Usuario usuario) {
        Map<String, Object> json = new HashMap<>();
        //Verificamos que el usuario no se ecuentre ya registrado
        Usuario usuarioEnDb = iUsuarioRepository.findByNombre(usuario.getNombre());
        if (usuarioEnDb != null){
            json.put("Error", "Usuario con nombre ya registrado en la base de datos");
            return json;
        }

        //Buscamos los roles en la db
        Rol rollUser = iRolRepository.findByName("ROLE_USUARIO");
        List<Rol> roles = new ArrayList<>();
        //Verificamos que exista el rol "ROLE_USUARIO" en la base de datos. Si existe lo agregamos al array de roles
        if (rollUser != null){
            roles.add(rollUser);
        }
        //Verificamos si el usuario es un admin para agregarle el rol admin
        if (usuario.isAdmin()){
            Rol rollAdmin = iRolRepository.findByName("ROLE_ADMIN");
            roles.add(rollAdmin);
        }

        //Al usuario agregamos roles y encriptamos la password
        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        iUsuarioRepository.save(usuario);
        json.put("usuario", usuario);
        return json;
    }
}
