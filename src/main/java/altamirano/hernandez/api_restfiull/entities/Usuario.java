package altamirano.hernandez.api_restfiull.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @NotBlank
    @Size(min = 3, max = 60)
    private String nombre;
    @NotBlank
    @Size(max = 90)
    private String password;
    private boolean enabled;
    //Relacion ManyToMany - Un Usuario puede tener muchos roles
    @ManyToMany
    @JoinTable(
            name = "Usuarios_Roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "rol_id"})}
    )
    List<Rol> roles = new ArrayList<>();
    //Atributo de rol
    @Transient
    private boolean admin;

    //Constructores
    public Usuario() {
    }

    public Usuario(String nombre, String password, boolean enabled) {
        this.nombre = nombre;
        this.password = password;
        this.enabled = enabled;
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
}
