package gm.Taller.controlador;


import gm.Taller.modelo.Clientes;
import gm.Taller.modelo.Users;
import gm.Taller.servicio.IClienteServicio;
import gm.Taller.servicio.IPiezaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {
        "https://taller-mecanico-web.vercel.app",
        "http://localhost:5173"
},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH},
        allowCredentials = "true"
)
public class ClienteControlador {

    private static final Logger logger =
            LoggerFactory.getLogger((ClienteControlador.class));
    @Autowired
    private IClienteServicio clienteServicio;

    @GetMapping("/clientes")
    public List<Clientes>getClientes(){
        var clientes = clienteServicio.listClientes();
        clientes.forEach((cliente->logger.info(cliente.toString())));
        return clientes;
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> createCliente(@RequestBody Clientes newCliente) {
        logger.info(newCliente.toString());

        // Check if the emailCliente already exists
        if (clienteServicio.emailExists(newCliente.getEmailCliente())) {
            return new ResponseEntity<>("El correo del cliente ya existe", HttpStatus.CONFLICT); // 409 Conflict
        }

        // Save the new client
        Clientes savedCliente = clienteServicio.saveCliente(newCliente);
        System.out.println("Cliente agregado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente); // 201 Created
    }

    @PutMapping("/clientes/{idCliente}")
    public ResponseEntity<Clientes> updateCliente(@RequestBody Clientes updatedClient, @PathVariable Integer idCliente){
        Clientes savedCliente = clienteServicio.updateCliente(idCliente, updatedClient);
        try {
            if (savedCliente != null) {
                logger.info("Cliente editado: " + savedCliente);
                return new ResponseEntity<>(savedCliente, HttpStatus.OK); // Return the updated user with a 200 status
            } else {
                logger.warn("Cliente no encontrado con ID: " + idCliente);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if user not found
            }
        } catch (Exception e) {
            logger.error("Error al editar el cliente con ID: " + idCliente, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }

    }

    @DeleteMapping("/clientes/{idCliente}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer idCliente){
        try{
            Clientes clienteToDelete = clienteServicio.searchClienteById(idCliente); //Search for client
            if(clienteToDelete != null){ //Take course of action weather it exists or doesn't
                clienteServicio.deleteCliente(clienteToDelete);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
