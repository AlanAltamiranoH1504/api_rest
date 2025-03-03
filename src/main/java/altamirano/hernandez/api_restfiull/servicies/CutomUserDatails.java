package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import altamirano.hernandez.api_restfiull.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CutomUserDatails implements UserDetailsService {
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscamos el usuario en la db por el nombre
        Usuario usuarioDb = iUsuarioRepository.findByNombre(username);

        if (usuarioDb == null) {
            throw new UsernameNotFoundException("Usuario con nombre: " + username + " no encontrado");
        }

        //Obtenelos los roles de usuario
        List<GrantedAuthority> roles = usuarioDb.getRoles().stream().map(
                rol -> new SimpleGrantedAuthority(rol.getNombre())
        ).collect(Collectors.toList());

        //Regresamos los datos del usuario
        return new org.springframework.security.core.userdetails.User(usuarioDb.getNombre(), usuarioDb.getPassword(), roles);
    }
}
