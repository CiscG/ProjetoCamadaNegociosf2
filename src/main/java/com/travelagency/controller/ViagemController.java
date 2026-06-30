package com.travelagency.controller;

import com.travelagency.exception.DatasInvalidasException;
import com.travelagency.exception.ViagemNotFoundException;
import com.travelagency.model.Viagem;
import com.travelagency.service.ViagemService;
import com.travelagency.util.ArquivoUtil;
import com.travelagency.util.LoggerUtil;
import com.travelagency.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Controller REST para gerenciar Viagens
 * Requisito: Desenvolvimento de Componentes de Serviços
 * Requisito: Manipulação de XML
 */
@RestController
@RequestMapping("/api/viagens")
public class ViagemController {
    
    private final ViagemService viagemService;
    private final LoggerUtil logger = new LoggerUtil();

    @Autowired
    public ViagemController(ViagemService viagemService) {
        this.viagemService = viagemService;
    }

    /**
     * Obtém todas as viagens
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() {
        try {
            List<Viagem> viagens = viagemService.listarTodas();
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("total", viagens.size());
            response.put("viagens", viagens);
            
            logger.info("Listadas " + viagens.size() + " viagens");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao listar viagens: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Obtém viagem por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obterPorId(@PathVariable String id) {
        try {
            Viagem viagem = viagemService.buscarViagemById(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("viagem", viagem);
            
            return ResponseEntity.ok(response);
            
        } catch (ViagemNotFoundException e) {
            logger.error("Viagem não encontrada: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro ao buscar viagem: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Cria nova viagem
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody Viagem viagem) {
        try {
            Viagem salva = viagemService.salvarViagem(viagem);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Viagem criada com sucesso");
            response.put("viagem", salva);
            
            logger.info("Nova viagem criada: " + salva.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (DatasInvalidasException e) {
            logger.error("Datas inválidas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro ao criar viagem: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Atualiza viagem
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(@PathVariable String id, @RequestBody Viagem viagem) {
        try {
            viagem.setId(id);
            Viagem atualizada = viagemService.salvarViagem(viagem);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Viagem atualizada com sucesso");
            response.put("viagem", atualizada);
            
            logger.info("Viagem atualizada: " + id);
            return ResponseEntity.ok(response);
            
        } catch (DatasInvalidasException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro ao atualizar viagem: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Deleta viagem
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable String id) {
        try {
            viagemService.deletarViagem(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Viagem deletada com sucesso");
            
            logger.info("Viagem deletada: " + id);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao deletar viagem: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Busca viagens por destino
     */
    @GetMapping("/destino/{destino}")
    public ResponseEntity<Map<String, Object>> buscarPorDestino(@PathVariable String destino) {
        try {
            List<Viagem> viagens = viagemService.buscarPorDestino(destino);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("destino", destino);
            response.put("total", viagens.size());
            response.put("viagens", viagens);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao buscar por destino: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Exporta viagens em XML
     * Requisito: Manipulação de XML
     */
    @GetMapping("/export/xml")
    public ResponseEntity<String> exportarXML() {
        try {
            List<Viagem> viagens = viagemService.listarTodas();
            String xml = XMLUtil.viagensParaXML(viagens);
            XMLUtil.salvarXMLArquivo(xml, "viagens_export");
            
            logger.info("Viagens exportadas em XML");
            return ResponseEntity.ok()
                    .header("Content-Type", "application/xml")
                    .body(xml);
            
        } catch (Exception e) {
            logger.error("Erro ao exportar XML: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gera relatório de viagens em arquivo texto
     * Requisito: Leitura e gravação de arquivos texto
     */
    @PostMapping("/relatorio")
    public ResponseEntity<Map<String, Object>> gerarRelatorio() {
        try {
            List<Viagem> viagens = viagemService.listarTodas();
            ArquivoUtil.gravarRelatorioViagens(viagens, "relatorio_viagens");
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("mensagem", "Relatório gerado com sucesso");
            response.put("caminho", "relatorios/relatorio_viagens.txt");
            
            logger.info("Relatório de viagens gerado");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro ao gerar relatório: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }

    /**
     * Busca viagem de forma assíncrona
     * Requisito: Programação Concorrente
     */
    @GetMapping("/async/{id}")
    public ResponseEntity<Map<String, Object>> buscarAsync(@PathVariable String id) {
        try {
            Future<Viagem> futuro = viagemService.buscarViagemAsync(id);
            Viagem viagem = futuro.get(); // Aguarda resultado
            
            if (viagem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("status", "erro", "mensagem", "Viagem não encontrada"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "sucesso");
            response.put("viagem", viagem);
            response.put("processamento", "assincrono");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Erro na busca assíncrona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "erro", "mensagem", e.getMessage()));
        }
    }
}
