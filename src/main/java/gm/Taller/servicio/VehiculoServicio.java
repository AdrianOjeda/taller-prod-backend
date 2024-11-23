package gm.Taller.servicio;

import gm.Taller.modelo.Vehiculos;
import gm.Taller.repositorio.VehiculoRepositorio; // Assuming you have this repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehiculoServicio implements IVehiculoServicio {

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Vehiculos> listVehiculos() {
        return vehiculoRepositorio.findAll();
    }
    @Override
    public List<Map<String, Object>> marcasMostRepaired(){
        String sql = "SELECT v.marca_vehiculo, COUNT(r.id_reparacion) AS reparaciones_totales " +
                "FROM vehiculos v " +
                "JOIN reparaciones r ON v.id_vehiculo = r.id_vehiculo " +
                "GROUP BY v.marca_vehiculo " +
                "ORDER BY reparaciones_totales DESC " +
                "LIMIT 10";
        return jdbcTemplate.queryForList(sql);
    }
    @Override
    public Vehiculos searchVehiculoById(Integer id) {
        return vehiculoRepositorio.findById(id).orElse(null); // Return the vehicle or null if not found
    }

    @Override
    public Vehiculos saveVehiculo(Vehiculos vehiculo) {
        Optional<Vehiculos> existingVehiculo = vehiculoRepositorio.findByMatriculaVehiculo(vehiculo.getMatriculaVehiculo());

        // Check if the matriculaVehiculo already exists
        if (existingVehiculo.isPresent()) {
            return null; // Optionally, throw an exception or handle it differently
        }

        return vehiculoRepositorio.save(vehiculo);
    }

    @Override
    public Vehiculos updateVehiculo(Integer id, Vehiculos updatedVehiculo) {
        // Fetch the existing vehicle
        Optional<Vehiculos> existingVehiculoOpt = vehiculoRepositorio.findById(id);
        if (existingVehiculoOpt.isPresent()) {

            Vehiculos existingVehiculo = existingVehiculoOpt.get();
            existingVehiculo.setMarcaVehiculo(updatedVehiculo.getMarcaVehiculo());
            existingVehiculo.setModeloVehiculo(updatedVehiculo.getModeloVehiculo());
            existingVehiculo.setMatriculaVehiculo(updatedVehiculo.getMatriculaVehiculo());
            existingVehiculo.setFechaIngreso(updatedVehiculo.getFechaIngreso());
            existingVehiculo.setColorVehiculo(updatedVehiculo.getColorVehiculo());
            existingVehiculo.setNotasVehiculo(updatedVehiculo.getNotasVehiculo());
            return vehiculoRepositorio.save(existingVehiculo); // Save and return the updated vehicle
        }
        return null; // Vehicle not found
    }
    @Override
    public void deleteVehiculo(Vehiculos vehiculo) {
        vehiculoRepositorio.delete(vehiculo); // Delete the vehicle from the database
    }

    @Override
    public Vehiculos searchVehiculoByMatricula(String matriculaVehiculo) {
        return vehiculoRepositorio.findByMatriculaVehiculo(matriculaVehiculo)
                .orElse(null);  // Return null if not found
    }
}
