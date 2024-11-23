package gm.Taller.repositorio;

import gm.Taller.modelo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorio extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username, String password);
    Users findByUsername(String username);
}
