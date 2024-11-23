package gm.Taller.modelo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

public class Piezas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idPieza;
    String piezaName;
    String piezaDescripcion;
    Integer stock;

    @ManyToMany(mappedBy = "piezas", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Reparaciones> reparaciones = new ArrayList<>();


    public Integer getIdPieza(){
        return idPieza;
    }

    public String getPiezaName(){
        return piezaName;
    }
    public String getPiezaDescripcion(){
        return piezaDescripcion;
    }
    public Integer getStock(){
        return stock;
    }

    public void setIdPieza(Integer id) {
        this.idPieza = id;
    }

    public void setPiezaName(String piezaName) {
        this.piezaName = piezaName;
    }

    public void setPiezaDescripcion(String piezaDescripcion) {
        this.piezaDescripcion = piezaDescripcion;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<Reparaciones> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(List<Reparaciones> reparaciones) {
        this.reparaciones = reparaciones;
    }
}
