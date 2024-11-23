package gm.Taller.servicio;

import gm.Taller.modelo.Users;
import gm.Taller.repositorio.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServicio implements IUserServicio{

    @Autowired
    private UserRepositorio userRepositorio;

    @Override
    public List<Users> listUsers() {
        return userRepositorio.findAll();

    }

    @Override
    public Users searchUserById(Integer idUser) {
        Users user = userRepositorio.findById(idUser).orElse(null);
        return user;


    }

    @Override
    public Users saveUser(Users user) {
        return userRepositorio.save(user);

    }

    @Override
    public void deleteUser(Users user) {
        userRepositorio.delete(user);

    }

    @Override
    public Users updateUser(Integer idUser, Users updatedUser) {
        Users existingUser = userRepositorio.findById(idUser).orElse(null);
        if (existingUser != null) {
            // Update the fields of the existing user
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setName(updatedUser.getName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setCellPhone(updatedUser.getCellPhone());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setRole(updatedUser.getRole());

            return userRepositorio.save(existingUser); // Save and return the updated user
        }
        return null;
    }
    @Override
    public Users login(String username, String password) {
        return userRepositorio.findByUsernameAndPassword(username, password);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepositorio.findByUsername(username);
    }
}
