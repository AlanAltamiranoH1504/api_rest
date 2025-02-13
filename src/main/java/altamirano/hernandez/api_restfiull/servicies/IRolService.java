package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Rol;

import java.util.List;

public interface IRolService {
    public abstract List<Rol> getAll();
    public abstract Rol save(Rol rol);
}
