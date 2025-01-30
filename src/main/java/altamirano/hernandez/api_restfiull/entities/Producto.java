package altamirano.hernandez.api_restfiull.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 60)
    private String nombre;

    @Min(1)
    private double precio;

    @NotEmpty
    @Size(max = 90)
    private String descripcion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //Constructores
    public Producto(){}
    public Producto(String nombre, double precio, String descripcion){
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    //Ciclo de vida
    @PrePersist
    public void postPersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void postUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    @PreRemove
    public void postRemove(){
        System.out.println("Objeto eliminado de la base de datos");
    }

    //Get y Set
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
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //toString
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    //Equals y hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return id == producto.id && Double.compare(precio, producto.precio) == 0 && Objects.equals(nombre, producto.nombre) && Objects.equals(descripcion, producto.descripcion);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio, descripcion);
    }
}
