package altamirano.hernandez.api_restfiull.dto;

import altamirano.hernandez.api_restfiull.entities.Usuario;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Objects;

public class UsuarioDTO {
    private int id;
    private String nombre;
    private boolean enabled;
    private boolean admin;

    //Constructores
    public UsuarioDTO() {
    }

    public UsuarioDTO(int id, String nombre, boolean enabled, boolean admin) {
        this.id = id;
        this.nombre = nombre;
        this.enabled = enabled;
        this.admin = admin;
    }

    //Metodos Get y Set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    //toString
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", enabled=" + enabled +
                ", admin=" + admin +
                '}';
    }

    //Equals y HashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return id == that.id && enabled == that.enabled && admin == that.admin && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, enabled, admin);
    }
}
