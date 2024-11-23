package gm.Taller.servicio;

import gm.Taller.modelo.Reparaciones;
import gm.Taller.modelo.Vehiculos;

import java.util.List;
import java.util.Map;

public interface IReparacionServicio {

    public List<Reparaciones> listReparaciones();
    public Reparaciones searchReparacionById(Integer idReparacion);
    public Reparaciones saveReparacion(Reparaciones reparacion);
    public Reparaciones updateReparacion(Integer idReparacion, Reparaciones updatedreparacion);
    public List<Map<String, Object>> fechas();
    public void deleteReparacion(Reparaciones reparacion);
}
