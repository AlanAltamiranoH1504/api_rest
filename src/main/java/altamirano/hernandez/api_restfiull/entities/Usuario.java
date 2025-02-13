package altamirano.hernandez.api_restfiull.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
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

    //Agregamos relacion many to many (Un usuario puede tener muchos roles)
    @ManyToMany
    @JoinTable(
            name = "Usuarios_Roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "rol_id"})}
    )
    List<Rol> roles = new ArrayList<>();

    //Campo non mapeado a la base de datos
    @Transient
    private boolean admin;

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
    public List<Rol> getRoles() {
        return roles;
    }
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
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
