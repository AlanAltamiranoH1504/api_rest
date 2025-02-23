package altamirano.hernandez.api_restfiull.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @Size(min = 2, max = 60)
    @Column(unique = true)
    private String nombre;
    @NotBlank
    @Size(max = 90)
    private String password;
    private boolean enabled;

    public Usuario(){

    }
    public Usuario(String nombre, String password, boolean enabled){
        this.nombre = nombre;
        this.password = password;
        this.enabled = enabled;
    }

    //Metodos GET y SET
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    //Metodo toString
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    //Metodos equals y hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && enabled == usuario.enabled && Objects.equals(nombre, usuario.nombre) && Objects.equals(password, usuario.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, password, enabled);
    }
}
