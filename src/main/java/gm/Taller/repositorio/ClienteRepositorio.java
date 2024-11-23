package gm.Taller.repositorio;

import gm.Taller.modelo.Clientes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Clientes, Integer> {
    Clientes findByEmailCliente(String emailCliente);

}
