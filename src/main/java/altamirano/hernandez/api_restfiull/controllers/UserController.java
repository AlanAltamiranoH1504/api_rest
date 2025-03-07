package altamirano.hernandez.api_restfiull.controllers;

import altamirano.hernandez.api_restfiull.dto.UsuarioDTO;
import altamirano.hernandez.api_restfiull.entities.Usuario;
import altamirano.hernandez.api_restfiull.servicies.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Habilitamos CORS
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/security")
public class UserController {
    @Autowired
    private IUsuarioService iUsuarioService;

    @GetMapping("/prueba")
    public Map<String, Object> json(){
        Map<String, Object> json = new HashMap<>();
        json.put("resultado", "la prueba pasa");
        return json;
    }

    @GetMapping("/findAll")
    public List<Usuario> findAll(){
        List<Usuario> usuarios = iUsuarioService.findAll();
        return usuarios;
    }

    //Validamos el requestBody
    @PostMapping("/save")
    public Map<String, Object> save(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){
        Map<String, Object> json = new HashMap<>();

        if (bindingResult.hasErrors()) {
            Map<String, Object> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error-> {
                errores.put(error.getField(), error.getDefaultMessage());
            });
            json.put("errores", errores);
            return json;
        }

        iUsuarioService.save(usuario);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEnabled(usuario.isEnabled());
        usuarioDTO.setAdmin(usuario.isAdmin());

        json.put("result", "Usuario guardado");
        json.put("usuario", usuarioDTO);
        json.put("roles", usuario.getRoles());
        return json;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@Valid @RequestBody Usuario usuario, BindingResult bindingResult){
        Map<String, Object> json = new HashMap<>();
        if (bindingResult.hasGlobalErrors()){
            Map<String, Object> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error->{
                errores.put(error.getField(), error.getDefaultMessage());
            });
            json.put("errores", errores);
            return json;
        }
        usuario.setAdmin(false);
        Map<String, Object> resultado = iUsuarioService.save(usuario);
        String resultadoKey = (String) resultado.get("Error");

        if (resultadoKey != null){
            json.put("resultado", resultadoKey);
        }else{
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setEnabled(usuario.isEnabled());
            usuarioDTO.setAdmin(usuario.isAdmin());
            json.put("resultado", "Usuario registrado");
            json.put("usuario", usuarioDTO);
            json.put("roles", usuario.getRoles());
        }
        return json;
    }
}
