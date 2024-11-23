package gm.Taller.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reparaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReparacion;

    private String falla;
    private String fechaEntrega;
    private String fechaInicio;



    @ManyToOne

    @JoinColumn(name = "id_vehiculo", nullable = false)  // Define the foreign key column
    private Vehiculos vehiculo;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "reparacion_piezas",
            joinColumns = @JoinColumn(name = "id_reparacion"),
            inverseJoinColumns = @JoinColumn(name = "id_pieza")
    )
     // Serialize this side
    @JsonProperty("piezasUtilizadas") // Specify JSON property name
    private List<Piezas> piezas = new ArrayList<>();




    // Getters and Setters

    public Vehiculos getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculos vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Integer getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(Integer idReparacion) {
        this.idReparacion = idReparacion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFalla() {
        return falla;
    }

    public void setFalla(String falla) {
        this.falla = falla;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public List<Piezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<Piezas> piezas) {
        this.piezas = piezas;
    }
}
