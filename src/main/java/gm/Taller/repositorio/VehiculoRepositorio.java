package gm.Taller.repositorio;

import gm.Taller.modelo.Vehiculos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface VehiculoRepositorio extends JpaRepository<Vehiculos, Integer> {

    Optional<Vehiculos> findByMatriculaVehiculo(String matriculaVehiculo);
}
