package gm.Taller.servicio;

import gm.Taller.modelo.Clientes;
import gm.Taller.modelo.Vehiculos;

import java.util.List;
import java.util.Map;

public interface IVehiculoServicio {

    public List<Vehiculos> listVehiculos();
    public Vehiculos searchVehiculoById(Integer idVehiculo);
    public Vehiculos saveVehiculo(Vehiculos vehiculo);
    public Vehiculos updateVehiculo(Integer idVehiculo, Vehiculos updatedVehiculo);
    public Vehiculos searchVehiculoByMatricula(String matriculaVehiculo);
    public void deleteVehiculo(Vehiculos vehiculo);
    public List<Map<String, Object>> marcasMostRepaired();
}
