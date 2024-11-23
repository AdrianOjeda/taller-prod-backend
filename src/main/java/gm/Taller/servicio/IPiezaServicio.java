package gm.Taller.servicio;

import gm.Taller.modelo.Piezas;

import java.util.List;
import java.util.Map;

public interface IPiezaServicio {

    public List<Map<String, Object>> listPiezas();
    public List<Map<String, Object>> mostUsedPiezas();

    public Piezas searchPiezaById(Integer idPieza);
    public Piezas savePieza(Piezas pieza);
    public Piezas updatePieza(Integer idPieza, Piezas updatedPieza);
    public void deletePieza(Integer piezaId);
}
