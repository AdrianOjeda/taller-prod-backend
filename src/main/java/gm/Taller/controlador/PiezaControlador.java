package gm.Taller.controlador;


import gm.Taller.modelo.Piezas;
import gm.Taller.repositorio.PiezaRepositorio;
import gm.Taller.servicio.IPiezaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "http://localhost:3000"})

public class PiezaControlador {
    private static final Logger logger =
            LoggerFactory.getLogger((PiezaControlador.class));
    @Autowired
    private IPiezaServicio PiezaServicio;


    @GetMapping("/piezas")
    public ResponseEntity<List<Map<String, Object>>> getPiezas() {
        List<Map<String, Object>> piezas = PiezaServicio.listPiezas();

        // Transform the response to match the frontend expectations
        List<Map<String, Object>> transformedPiezas = piezas.stream().map(pieza -> {
            Map<String, Object> transformedPieza = new HashMap<>();
            transformedPieza.put("idPieza", pieza.get("id_pieza")); // Change to 'idPieza'
            transformedPieza.put("piezaName", pieza.get("pieza_name")); // Change to 'piezaName'
            transformedPieza.put("piezaDescripcion", pieza.get("pieza_descripcion")); // Change to 'piezaDescripcion'
            transformedPieza.put("stock", pieza.get("stock")); // Keep stock as it is
            return transformedPieza;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(transformedPiezas); // Return 200 OK with transformed piezas list
    }

    @GetMapping("/piezas/mostUsed")
    public ResponseEntity<?> getMostUsedPiezas() {
        try {
            List<Map<String, Object>> mostUsedPiezas = PiezaServicio.mostUsedPiezas();
            return ResponseEntity.ok(mostUsedPiezas);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception in the server log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching most used piezas: " + e.getMessage());
        }
    }


    @PostMapping("/piezas")
    public ResponseEntity<Piezas> createPieza(@RequestBody Piezas newPieza){
        try{
            Piezas savedPieza = PiezaServicio.savePieza(newPieza);
            logger.info("Received request to create pieza: " + newPieza);
            return new ResponseEntity<>(savedPieza, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }

    @PutMapping("/piezas/{piezaId}")
    public ResponseEntity<Piezas> updatePieza(@PathVariable Integer piezaId, @RequestBody Piezas updatedPieza) {
        try {
            Piezas existingPieza = PiezaServicio.searchPiezaById(piezaId); // Assuming a searchPiezaById method exists
            if (existingPieza != null) {
                updatedPieza.setIdPieza(piezaId); // Ensure the ID remains the same
                Piezas savedPieza = PiezaServicio.savePieza(updatedPieza); // Save the updated pieza
                logger.info("Pieza updated: " + savedPieza);
                return new ResponseEntity<>(savedPieza, HttpStatus.OK); // Return the updated pieza with status 200
            } else {
                logger.warn("Pieza not found with ID: " + piezaId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if pieza not found
            }
        } catch (Exception e) {
            logger.error("Error updating pieza with ID: " + piezaId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error if something goes wrong
        }
    }

    @DeleteMapping("/piezas/{piezaId}")
    public ResponseEntity<Void> deletePieza(@PathVariable Integer piezaId) {
        try {

            PiezaServicio.deletePieza(piezaId);
            System.out.println("Pieza borrada con exito");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content
        } catch (Exception e) {
            logger.error("Error deleting pieza with ID: " + piezaId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }
}
