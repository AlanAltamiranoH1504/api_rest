package altamirano.hernandez.api_restfiull.servicies;

import altamirano.hernandez.api_restfiull.entities.Rol;
import altamirano.hernandez.api_restfiull.repositories.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImplRolService implements IRolService{
    @Autowired
    IRolRepository iRolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> getAll() {
        return (List<Rol>) iRolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Rol save(Rol rol) {
        iRolRepository.save(rol);
        return rol;
    }
}
