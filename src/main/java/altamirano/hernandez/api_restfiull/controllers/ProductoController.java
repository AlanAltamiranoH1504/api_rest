package altamirano.hernandez.api_restfiull.controllers;

import altamirano.hernandez.api_restfiull.entities.Producto;
import altamirano.hernandez.api_restfiull.servicies.IProductoService;
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
@RequestMapping("/api/productos")
public class ProductoController {
    //Inyectamos el servicio
    @Autowired
    IProductoService iProductoService;

    @GetMapping("/prueba")
    public Map<String, Object> prueba(){
        Map<String, Object> json = new HashMap<>();

        json.put("resultado", "comunicacion correcta a la api");
        return json;
    }

    @GetMapping("/findAll")
    public List<Producto> findAll(){
        List<Producto> productos = iProductoService.findAllProductos();
        return productos;
    }

    @GetMapping("/findById/{id}")
    public Map<String, Object>findById(@PathVariable int id){
        Map<String, Object> json = new HashMap<>();
        Producto producto = iProductoService.findProductoById(id);
        json.put("producto", producto);
        return json;
    }

    @PostMapping("/save")
    public Map<String, Object> save(@Valid @RequestBody Producto producto, BindingResult bindingResult){
        Map<String, Object> json = new HashMap<>();

        if (bindingResult.hasErrors()){
            Map<String, Object> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->{
                errores.put(error.getField(), error.getDefaultMessage());
            });
            json.put("errores", errores);
        }else{
            iProductoService.saveProductp(producto);
            json.put("producto", producto);
        }
        return json;
    }

    @PutMapping("/update/{id}")
    public Map<String, Object>update(@PathVariable int id, @Valid @RequestBody Producto producto, BindingResult bindingResult){
        Map<String, Object>json = new HashMap<>();

        if (bindingResult.hasErrors()){
            Map<String, Object>errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->{
                errores.put(error.getField(), error.getDefaultMessage());
            });
            json.put("errores", errores);
            return json;
        }
        Producto productoDb = iProductoService.findProductoById(id);
        if (productoDb != null){
            //Verificamos que viene en el RequestBody
            if (producto.getNombre() != null){
                productoDb.setNombre(producto.getNombre());
            }
            if (producto.getDescripcion() != null){
                productoDb.setDescripcion(producto.getDescripcion());
            }
            if (producto.getPrecio() != 0){
                productoDb.setPrecio(producto.getPrecio());
            }
            //Actualizamos en la db
            iProductoService.saveProductp(productoDb);
            json.put("resultado", "Producto actualizado en la db");
            json.put("producto nuevo", productoDb);
        }else{
            json.put("producto", "Producto no encontrado");
        }
        return json;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable int id){
        Map<String, Object> json = new HashMap<>();
        Producto producto = iProductoService.findProductoById(id);
        if (producto != null){
            iProductoService.deleteProductoById(producto.getId());
            json.put("resultado", "Producto eliminado");
        }else {
            json.put("resultado", "Producto no encontrado");
        }
        return json;
    }
}
