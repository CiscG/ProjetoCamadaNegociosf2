package com.travelagency.controller;

import com.travelagency.exception.ClienteInvalidoException;
import com.travelagency.model.Cliente;
import com.travelagency.service.ClienteService;
import com.travelagency.util.LoggerUtil;
import com.travelagency.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller REST para gerenciar Clientes
 * Requisito: Desenvolvimento de Componentes de Serviços
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    private final LoggerUtil logger = new LoggerUtil();

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Lista todos os clientes
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        try {
            List<Cliente> clientes = clienteService.listarTodos();
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("total", clientes.size());
            response.put("clientes", clientes);
            
            logger.info("Listados " + clientes.size() + " clientes");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao listar clientes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Obtém cliente por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obterPorId(@PathVariable String id) {
        try {
            Cliente cliente = clienteService.buscarClienteById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("cliente", cliente);
            
            return ResponseEntity.ok(response);
            
        } catch (ClienteInvalidoException e) {
            logger.error("Cliente não encontrado: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro ao buscar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Cria novo cliente
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody Cliente cliente) {
        try {
            Cliente salvo = clienteService.salvarCliente(cliente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Cliente criado com sucesso");
            response.put("cliente", salvo);
            
            logger.info("Novo cliente criado: " + salvo.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (ClienteInvalidoException e) {
            logger.error("Cliente inválido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro ao criar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Atualiza cliente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(@PathVariable String id, @RequestBody Cliente cliente) {
        try {
            cliente.setId(id);
            Cliente atualizado = clienteService.salvarCliente(cliente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Cliente atualizado com sucesso");
            response.put("cliente", atualizado);
            
            logger.info("Cliente atualizado: " + id);
            return ResponseEntity.ok(response);
            
        } catch (ClienteInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro ao atualizar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Deleta cliente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable String id) {
        try {
            clienteService.deletarCliente(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Cliente deletado com sucesso");
            
            logger.info("Cliente deletado: " + id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao deletar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Busca cliente por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> buscarPorEmail(@PathVariable String email) {
        try {
            Optional<Cliente> cliente = clienteService.buscarPorEmail(email);
            
            if (cliente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("status", "erro", "mensagem", "Cliente não encontrado"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("cliente", cliente.get());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao buscar por email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Exporta cliente em XML
     * Requisito: Manipulação de XML
     */
    @GetMapping("/{id}/export/xml")
    public ResponseEntity<String> exportarXML(@PathVariable String id) {
        try {
            Cliente cliente = clienteService.buscarClienteById(id);
            String xml = XMLUtil.clienteParaXML(cliente);
            XMLUtil.salvarXMLArquivo(xml, "cliente_" + id);
            
            logger.info("Cliente exportado em XML: " + id);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/xml")
                    .body(xml);
            
        } catch (ClienteInvalidoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Erro ao exportar XML: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
