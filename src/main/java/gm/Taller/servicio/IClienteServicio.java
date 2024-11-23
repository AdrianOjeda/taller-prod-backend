package gm.Taller.servicio;

import gm.Taller.modelo.Clientes;
import gm.Taller.modelo.Piezas;

import java.util.List;

public interface IClienteServicio {

    public List<Clientes> listClientes();
    public Clientes searchClienteById(Integer idCliente);
    public Clientes saveCliente(Clientes cliente);
    public Clientes updateCliente(Integer idCliente, Clientes updatedCliente);
    public boolean emailExists(String email);
    public void deleteCliente(Clientes cliente);
}
