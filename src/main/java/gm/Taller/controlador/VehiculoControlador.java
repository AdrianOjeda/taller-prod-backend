package gm.Taller.controlador;


import gm.Taller.modelo.Vehiculos;
import gm.Taller.servicio.IPiezaServicio;
import gm.Taller.servicio.IVehiculoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")

public class VehiculoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger((VehiculoControlador.class));

    @Autowired
    private IVehiculoServicio VehiculoServicio;

    @GetMapping("/vehiculos")
    public List<Vehiculos> getVehiculos(){
        var vehiculos = VehiculoServicio.listVehiculos();
        logger.info("Vehiculos "+vehiculos);
        return vehiculos;
    }

    @GetMapping("/vehiculos/marcasRepaired")
    public ResponseEntity<?> getMostRepairedMarcas(){
        List<Map<String, Object>> mostRepairedMarcas = VehiculoServicio.marcasMostRepaired();
        return ResponseEntity.ok(mostRepairedMarcas);
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<?> createVehiculo(@RequestBody Vehiculos newVehiculo) {
        Vehiculos savedVehiculo = VehiculoServicio.saveVehiculo(newVehiculo);
        logger.info("Vehiculo a agregar "+newVehiculo);
        if (savedVehiculo == null) {
            // Respond with an appropriate error if the matriculaVehiculo exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vehiculo with matriculaVehiculo already exists.");
        }
        System.out.println("Vehiculo agregado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehiculo);
    }
    @PutMapping("/vehiculos/{idVehiculo}")
    public ResponseEntity<Vehiculos> updateVehiculo(@PathVariable Integer idVehiculo, @RequestBody Vehiculos updatedVehiculo) {
        try {

            Vehiculos savedVehiculo = VehiculoServicio.updateVehiculo(idVehiculo, updatedVehiculo);

            if (savedVehiculo != null) {
                logger.info("Vehiculo editado: " + savedVehiculo);
                return new ResponseEntity<>(savedVehiculo, HttpStatus.OK); // Return the updated vehicle with a 200 status
            } else {
                logger.warn("Vehiculo no encontrado con ID: " + idVehiculo);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if vehicle not found
            }
        } catch (Exception e) {
            logger.error("Error al editar el vehiculo con ID: " + idVehiculo, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }

    @DeleteMapping("/vehiculos/{idVehiculo}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable Integer idVehiculo) {
        try {

            Vehiculos vehiculoToDelete = VehiculoServicio.searchVehiculoById(idVehiculo);
            if (vehiculoToDelete != null) {
                VehiculoServicio.deleteVehiculo(vehiculoToDelete); // Delete the vehicle
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if vehicle not found
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el vehiculo con ID: " + idVehiculo, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }
    @GetMapping("/vehiculos/searchByMatricula/{searchMatricula}")
    public ResponseEntity<?> searchByMatricula(@PathVariable String searchMatricula) {
        try {
            Vehiculos vehiculo = VehiculoServicio.searchVehiculoByMatricula(searchMatricula);

            if (vehiculo != null) {
                return new ResponseEntity<>(vehiculo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Vehiculo not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
