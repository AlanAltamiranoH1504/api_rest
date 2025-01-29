package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Producto;
import altamirano.hernandez.api_restfiull.repositories.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplProductoService implements IProductoService{
    //Inyectamos el repositorio
    @Autowired
    IProductoRepository iProductoRepository;

    @Override
    public List<Producto> findAllProductos() {
        List<Producto> productos = (List<Producto>) iProductoRepository.findAll();
        return productos;
    }

    @Override
    public Producto findProductoById(int id) {
        Producto producto = iProductoRepository.findById(id).orElse(null);
        return producto;
    }

    @Override
    public void saveProductp(Producto producto) {
        iProductoRepository.save(producto);
        System.out.println("Producto guardado en la base de datos");
    }

    @Override
    public void deleteProductoById(int id) {
        Producto producto = iProductoRepository.findById(id).orElse(null);
        if (producto != null){
            iProductoRepository.deleteById(id);
            System.out.println("Producto eliminado de la base de datos");
        }else{
            System.out.println("Producto no encontrado con ese ID");
        }
    }

    @Override
    public void deleteProducto(Producto producto) {
        Producto productFind = iProductoRepository.findById(producto.getId()).orElse(null);
        if (productFind != null){
            iProductoRepository.delete(producto);
            System.out.println("Producto eliminado de la base de datos");
        }else{
            System.out.println("Producto no encontrado con ese ID");
        }
    }
}
