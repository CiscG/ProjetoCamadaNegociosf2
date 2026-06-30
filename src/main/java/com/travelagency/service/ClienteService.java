package com.travelagency.service;

import com.travelagency.exception.ClienteInvalidoException;
import com.travelagency.model.Cliente;
import com.travelagency.repository.ClienteRepository;
import com.travelagency.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar Cliente
 * Requisito: Tipos Genéricos e try-catch-finally
 * Requisito: Referência this e super
 */
@Service
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final LoggerUtil logger;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, LoggerUtil logger) {
        this.clienteRepository = clienteRepository;
        this.logger = logger;
    }

    /**
     * Busca cliente por ID com try-catch-finally
     * Requisito: Blocos try-catch-finally
     */
    public Cliente buscarClienteById(String id) {
        try {
            if (id == null || id.isEmpty()) {
                throw new ClienteInvalidoException("ID do cliente não pode ser vazio", id);
            }

            Optional<Cliente> cliente = clienteRepository.findById(id);
            
            if (cliente.isEmpty()) {
                throw new ClienteInvalidoException("Cliente não encontrado com ID: " + id, id);
            }
            
            logger.info("Cliente encontrado: " + cliente.get().getNome());
            return cliente.get();
            
        } catch (ClienteInvalidoException e) {
            logger.error("Erro - Cliente inválido: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao buscar cliente: " + e.getMessage());
            throw new ClienteInvalidoException("Erro ao buscar cliente: " + e.getMessage(), id);
        } finally {
            logger.info("Finalizada busca de cliente com ID: " + id);
        }
    }

    /**
     * Salva novo cliente
     */
    public Cliente salvarCliente(Cliente cliente) {
        try {
            if (cliente == null) {
                throw new ClienteInvalidoException("Cliente não pode ser nulo");
            }
            
            // Valida email
            if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
                throw new ClienteInvalidoException("Email do cliente é obrigatório");
            }
            
            // Valida CPF
            if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
                throw new ClienteInvalidoException("CPF do cliente é obrigatório");
            }
            
            Cliente salvo = clienteRepository.save(cliente);
            logger.info("Cliente salvo com sucesso: " + salvo.getNome());
            return salvo;
            
        } catch (ClienteInvalidoException e) {
            logger.error("Erro ao salvar cliente: " + e.getMessage());
            throw e;
        } finally {
            logger.info("Operação de salvar cliente finalizada");
        }
    }

    /**
     * Lista todos os clientes com tipos genéricos
     * Requisito: Tipos Genéricos
     */
    public List<Cliente> listarTodos() {
        try {
            List<Cliente> clientes = clienteRepository.findAll();
            logger.info("Total de clientes encontrados: " + clientes.size());
            return clientes;
        } finally {
            logger.info("Busca de todos os clientes finalizada");
        }
    }

    /**
     * Busca cliente por email
     */
    public Optional<Cliente> buscarPorEmail(String email) {
        try {
            if (email == null || email.isEmpty()) {
                throw new ClienteInvalidoException("Email não pode ser vazio");
            }
            return clienteRepository.findByEmail(email);
        } finally {
            logger.info("Busca de cliente por email finalizada: " + email);
        }
    }

    /**
     * Deleta cliente
     */
    public void deletarCliente(String id) {
        try {
            if (id == null || id.isEmpty()) {
                throw new ClienteInvalidoException("ID do cliente não pode ser vazio");
            }
            clienteRepository.deleteById(id);
            logger.info("Cliente deletado com ID: " + id);
        } finally {
            logger.info("Operação de deletar cliente finalizada");
        }
    }
}
