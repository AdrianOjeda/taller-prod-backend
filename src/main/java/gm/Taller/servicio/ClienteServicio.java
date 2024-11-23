package gm.Taller.servicio;

import gm.Taller.modelo.Clientes;

import gm.Taller.repositorio.ClienteRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServicio implements IClienteServicio{
    @Autowired
    private ClienteRepositorio clienteRepositorio;


    @Override
    public List<Clientes> listClientes() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Clientes searchClienteById(Integer idCliente) {

        return clienteRepositorio.findById(idCliente).orElse(null);
    }

    @Override
    public Clientes saveCliente(Clientes cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Clientes updateCliente(Integer idCliente, Clientes updatedCliente) {

        Clientes existingCliente = clienteRepositorio.findById(idCliente).orElse(null);
        if(existingCliente != null){
            existingCliente.setAddress(updatedCliente.getAddress());
            existingCliente.setFirstName(updatedCliente.getFirstName());
            existingCliente.setLastName(updatedCliente.getLastName());
            existingCliente.setPhone(updatedCliente.getPhone());

            return clienteRepositorio.save(existingCliente);
        }
        return null;
    }

    @Override
    public void deleteCliente(Clientes cliente) {
        clienteRepositorio.delete(cliente);
    }

    public boolean emailExists(String email) {

        return clienteRepositorio.findByEmailCliente(email) != null;
    }
}
