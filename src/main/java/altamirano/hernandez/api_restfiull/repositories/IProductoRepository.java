package altamirano.hernandez.api_restfiull.repositories;

import altamirano.hernandez.api_restfiull.entities.Producto;
import org.springframework.data.repository.CrudRepository;

public interface IProductoRepository extends CrudRepository<Producto, Integer> {
}
