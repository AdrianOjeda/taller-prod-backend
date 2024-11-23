package gm.Taller.servicio;

import gm.Taller.modelo.Users;

import java.util.List;

public interface IUserServicio {

    public List<Users> listUsers();
    public Users searchUserById(Integer idUser);
    public Users saveUser(Users user);
    public Users updateUser(Integer idUser, Users updatedUser);
    public Users login(String username, String password);
    public void deleteUser(Users user);

    public Users findByUsername(String username);
}
