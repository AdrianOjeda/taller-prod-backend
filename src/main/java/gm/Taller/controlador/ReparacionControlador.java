package gm.Taller.controlador;

import gm.Taller.modelo.*;
import gm.Taller.servicio.IReparacionServicio;
import gm.Taller.servicio.IPiezaServicio;
import gm.Taller.servicio.IVehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "http://localhost:3000"})
public class ReparacionControlador {

    @Autowired
    private IReparacionServicio reparacionServicio;

    @Autowired
    private IVehiculoServicio vehiculoServicio;

    @Autowired
    private IPiezaServicio piezaServicio;


    @GetMapping("/reparaciones")
    public ResponseEntity<List<Reparaciones>> getAllReparaciones() {
        List<Reparaciones> reparacionesList = reparacionServicio.listReparaciones(); // Fetch all reparaciones

        for (Reparaciones reparacion : reparacionesList) {
            Vehiculos vehiculo = reparacion.getVehiculo();
            if (vehiculo != null) {

                Clientes cliente = vehiculo.getCliente(); // Fetch associated client
                reparacion.setVehiculo(vehiculo); // Make sure vehiculo is properly set (already should be)
            }
        }

        return new ResponseEntity<>(reparacionesList, HttpStatus.OK); // Return the list of reparaciones
    }

    @GetMapping("/reparaciones/fechas")
    public ResponseEntity<?> getFechas(){
        List<Map<String, Object>> fetchFechas = reparacionServicio.fechas();
        return ResponseEntity.ok(fetchFechas);
    }
    @PostMapping("/reparaciones")
    public ResponseEntity<Reparaciones> createReparacion(@RequestBody Reparaciones reparacion) {
        // Log incoming reparacion
        System.out.println("Reparacion recibido: " + reparacion);

        // Fetch the associated vehicle
        Vehiculos vehiculo = vehiculoServicio.searchVehiculoById(reparacion.getVehiculo().getIdVehiculo());
        if (vehiculo == null) {
            System.out.println("Vehiculo no encontrado");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vehicle not found
        }

        // Fetch full piezas details from the database
        List<Piezas> piezasUtilizadas = new ArrayList<>();
        for (Piezas pieza : reparacion.getPiezas()) {
            Piezas piezaInDb = piezaServicio.searchPiezaById(pieza.getIdPieza());
            if (piezaInDb == null) {
                System.out.println("Pieza no encontrada");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Pieza not found
            }
            piezaInDb.setStock(piezaInDb.getStock()-pieza.getStock()); // Set the stock used in this repair
            piezasUtilizadas.add(piezaInDb);
        }

        reparacion.setPiezas(piezasUtilizadas); // Set the full list of piezas

        // Save the new reparacion
        Reparaciones createdReparacion = reparacionServicio.saveReparacion(reparacion);

        return new ResponseEntity<>(createdReparacion, HttpStatus.CREATED);
    }

    @PutMapping("/reparaciones/{editingRepairId}")
    public ResponseEntity<Reparaciones> updateReparacion(@PathVariable Integer editingRepairId, @RequestBody Reparaciones updatedReparacion) {
        // Fetch the existing reparacion
        Reparaciones existingReparacion = reparacionServicio.searchReparacionById(editingRepairId);


        existingReparacion.setFalla(updatedReparacion.getFalla());
        if (updatedReparacion.getFechaEntrega() != null) {
            existingReparacion.setFechaEntrega(updatedReparacion.getFechaEntrega());
        }

        Reparaciones savedReparacion = reparacionServicio.updateReparacion(editingRepairId, existingReparacion);

        return ResponseEntity.ok(savedReparacion); // Return the updated reparacion
    }

    @DeleteMapping("/reparaciones/{idReparacion}")
    public ResponseEntity<Reparaciones> deleteReparacion(@PathVariable Integer idReparacion){
        try{
            Reparaciones reparacion = reparacionServicio.searchReparacionById(idReparacion);
            reparacionServicio.deleteReparacion(reparacion);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch(Exception e){

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }




}
