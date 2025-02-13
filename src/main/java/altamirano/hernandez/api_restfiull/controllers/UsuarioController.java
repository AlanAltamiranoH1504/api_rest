package altamirano.hernandez.api_restfiull.controllers;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import altamirano.hernandez.api_restfiull.servicies.ImplUsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    ImplUsuarioService implUsuarioService;

    @GetMapping("/prueba")
    public String prueba(){
        return "Prueba del controlador de usuario";
    }

    @GetMapping("/getAll")
    public List<Usuario> getAll(){
        return implUsuarioService.getAll();
    }

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody @Valid Usuario usuario, BindingResult bindingResult){
        Map<String, Object> json = new HashMap<>();
        if (bindingResult.hasErrors()){
            Map<String, Object> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->{
                errors.put(error.getField(), error.getDefaultMessage());
            });
            json.put("errors", errors);
            return json;
        }
        implUsuarioService.save(usuario);
        json.put("usuario", usuario);
        return json;
    }
}
