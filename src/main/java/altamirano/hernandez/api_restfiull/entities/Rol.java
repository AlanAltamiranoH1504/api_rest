package altamirano.hernandez.api_restfiull.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @Size(max = 45)
    @Column(unique = true)
    private String nombre;

    //Relacion ManyToMany con Usuario - Un rol puede tener muchos usuarios
//    @JsonIgnoreProperties({"roles"})
//    @ManyToMany(mappedBy = "roles")
//    List<Usuario> usuarios = new ArrayList<>();
    //Constructores
    public Rol(){}
    public Rol(String nombre){
        this.nombre = nombre;
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
//    public List<Usuario> getUsuarios() {
//        return usuarios;
//    }
//    public void setUsuarios(List<Usuario> usuarios) {
//        this.usuarios = usuarios;
//    }

    //Metodo toString
    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }

//    //Metodo Equals y HashCode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Rol rol = (Rol) o;
//        return id == rol.id && Objects.equals(nombre, rol.nombre);
//    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, nombre);
//    }
}
