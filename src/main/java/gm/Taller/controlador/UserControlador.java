package gm.Taller.controlador;


import gm.Taller.modelo.Users;
import gm.Taller.servicio.IUserServicio;
import gm.Taller.servicio.UserServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:8081",
        "http://localhost:3000",
        "http://192.168.49.2:30000",
        "http://127.0.0.1:52034",
        "http://taller-frontend-service.default.svc.cluster.local",
        "http://127.0.0.1:61191",
        "*"  // Allow all origins if necessary
})
public class UserControlador {
    private static final Logger logger =
            LoggerFactory.getLogger((UserControlador.class));
    @Autowired
    private IUserServicio UserServicio;

    @GetMapping("/users") //This endpoint retrieves every user in the db and sends them to the FrontEnd
    public List<Users> getUsers(){
        var users = UserServicio.listUsers();
        users.forEach((user -> logger.info(user.toString())));

        return users;

    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody Users userInfo) {
        Users user = UserServicio.login(userInfo.getUsername(), userInfo.getPassword());
        logger.info(String.valueOf(userInfo));
        logger.info("Received username: " + userInfo.getUsername());
        logger.info("Received password: " + userInfo.getPassword());
        logger.info("Hola "+user);
        if (user != null) {
            System.out.println("Ingreso exitoso");
            return ResponseEntity.ok(user); // Successful login
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }
    }
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Users newUser) {
        try {

            Users existingUser = UserServicio.findByUsername(newUser.getUsername()); //Check if user exists
            if (existingUser != null) {
                return new ResponseEntity<>("El nombre de usuario ya existe", HttpStatus.CONFLICT);
                //return 409 Conflict
            }


            Users savedUser = UserServicio.saveUser(newUser);
            logger.info("Usuario creado: " + savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            logger.error("Error al crear el usuario: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @PutMapping("/users/editar/{editingUserId}") // Endpoint for editing users
    public ResponseEntity<Users> editUser(@PathVariable Integer editingUserId, @RequestBody Users updatedUser) {
        try {
            Users savedUser = UserServicio.updateUser(editingUserId, updatedUser);

            if (savedUser != null) {
                logger.info("Usuario editado: " + savedUser);
                return new ResponseEntity<>(savedUser, HttpStatus.OK); // Return the updated user with a 200 status
            } else {
                logger.warn("Usuario no encontrado con ID: " + editingUserId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if user not found
            }
        } catch (Exception e) {
            logger.error("Error al editar el usuario con ID: " + editingUserId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }
    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<Users> deleteUser(@PathVariable Integer userId){
        try{
            Users user = UserServicio.searchUserById(userId);
            UserServicio.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch(Exception e){
            logger.error("Error al eliminar el usuario con ID: " + userId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }

}
