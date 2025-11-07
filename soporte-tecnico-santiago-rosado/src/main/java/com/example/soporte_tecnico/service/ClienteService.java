package com.example.soporte_tecnico.service;

import com.example.soporte_tecnico.dto.ClienteDTO;
import com.example.soporte_tecnico.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Cliente save(ClienteDTO clienteDTO);
    Cliente update(Long id, ClienteDTO clienteDTO);
    void deleteById(Long id);
}
