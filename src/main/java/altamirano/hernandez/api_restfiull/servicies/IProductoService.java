package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> findAllProductos();
    Producto findProductoById(int id);
    void saveProductp(Producto producto);
    void deleteProductoById(int id);
    void deleteProducto(Producto producto);
}
