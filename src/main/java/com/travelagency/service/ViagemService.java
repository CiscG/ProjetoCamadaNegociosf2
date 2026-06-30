package com.travelagency.service;

import com.travelagency.exception.DatasInvalidasException;
import com.travelagency.exception.ViagemNotFoundException;
import com.travelagency.model.Viagem;
import com.travelagency.repository.ViagemRepository;
import com.travelagency.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Serviço para gerenciar Viagem
 * Requisito: Tipos Genéricos, try-catch-finally
 * Requisito: Programação Concorrente
 * Requisito: Blocos try-catch-finally
 */
@Service
public class ViagemService {
    
    private final ViagemRepository viagemRepository;
    private final LoggerUtil logger;
    private final ExecutorService executorService;

    @Autowired
    public ViagemService(ViagemRepository viagemRepository, LoggerUtil logger) {
        this.viagemRepository = viagemRepository;
        this.logger = logger;
        // Pool de threads para programação concorrente
        this.executorService = Executors.newFixedThreadPool(3);
    }

    /**
     * Busca viagem por ID com try-catch-finally
     * Requisito: Blocos try-catch-finally
     */
    public Viagem buscarViagemById(String id) throws ViagemNotFoundException {
        try {
            if (id == null || id.isEmpty()) {
                throw new ViagemNotFoundException("ID da viagem não pode ser vazio", id);
            }

            Optional<Viagem> viagem = viagemRepository.findById(id);
            
            if (viagem.isEmpty()) {
                throw new ViagemNotFoundException("Viagem não encontrada com ID: " + id, id);
            }
            
            logger.info("Viagem encontrada: " + viagem.get().getDestino());
            return viagem.get();
            
        } catch (ViagemNotFoundException e) {
            logger.error("Erro - Viagem não encontrada: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao buscar viagem: " + e.getMessage());
            throw new ViagemNotFoundException("Erro ao buscar viagem", e);
        } finally {
            logger.info("Finalizada busca de viagem com ID: " + id);
        }
    }

    /**
     * Salva nova viagem com validação de datas
     * Requisito: Criação e Lançamento de exceções
     */
    public Viagem salvarViagem(Viagem viagem) throws DatasInvalidasException, ViagemNotFoundException {
        try {
            if (viagem == null) {
                throw new ViagemNotFoundException("Viagem não pode ser nula");
            }
            
            // Valida datas
            if (viagem.getDataPartida() == null || viagem.getDataRetorno() == null) {
                throw new DatasInvalidasException(
                    "Datas de partida e retorno são obrigatórias",
                    String.valueOf(viagem.getDataPartida()),
                    String.valueOf(viagem.getDataRetorno())
                );
            }
            
            if (viagem.getDataPartida().isAfter(viagem.getDataRetorno())) {
                throw new DatasInvalidasException(
                    "Data de partida não pode ser após data de retorno",
                    viagem.getDataPartida().toString(),
                    viagem.getDataRetorno().toString()
                );
            }
            
            if (viagem.getDataPartida().isBefore(LocalDate.now())) {
                throw new DatasInvalidasException(
                    "Data de partida não pode ser no passado",
                    viagem.getDataPartida().toString(),
                    viagem.getDataRetorno().toString()
                );
            }
            
            Viagem salva = viagemRepository.save(viagem);
            logger.info("Viagem salva com sucesso: " + salva.getDestino());
            return salva;
            
        } catch (DatasInvalidasException e) {
            logger.error("Erro nas datas: " + e.getMessage());
            throw e;
        } catch (ViagemNotFoundException e) {
            logger.error("Erro ao salvar viagem: " + e.getMessage());
            throw e;
        } finally {
            logger.info("Operação de salvar viagem finalizada");
        }
    }

    /**
     * Lista todas as viagens com tipos genéricos
     * Requisito: Tipos Genéricos
     */
    public List<Viagem> listarTodas() {
        try {
            List<Viagem> viagens = viagemRepository.findByAtivaTrue();
            logger.info("Total de viagens ativas encontradas: " + viagens.size());
            return viagens;
        } finally {
            logger.info("Busca de todas as viagens finalizada");
        }
    }

    /**
     * Busca viagens por destino
     */
    public List<Viagem> buscarPorDestino(String destino) throws ViagemNotFoundException {
        try {
            if (destino == null || destino.isEmpty()) {
                throw new ViagemNotFoundException("Destino não pode ser vazio");
            }
            return viagemRepository.findByDestino(destino);
        } finally {
            logger.info("Busca de viagens por destino finalizada: " + destino);
        }
    }

    /**
     * Operação assíncrona com programação concorrente
     * Requisito: Programação Concorrente
     */
    public Future<Viagem> buscarViagemAsync(String id) {
        return executorService.submit(() -> {
            try {
                Thread.sleep(500); // Simula operação demorada
                return buscarViagemById(id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread interrompida durante busca assíncrona");
                return null;
            } catch (ViagemNotFoundException e) {
                logger.error("Erro na busca assíncrona: " + e.getMessage());
                return null;
            }
        });
    }

    /**
     * Atualiza vagas disponíveis de forma concorrente
     */
    public void atualizarVagasAsync(String id, Integer novasVagas) {
        executorService.execute(() -> {
            try {
                Viagem viagem = buscarViagemById(id);
                viagem.setVagas(novasVagas);
                viagemRepository.save(viagem);
                logger.info("Vagas atualizadas para viagem: " + id);
            } catch (ViagemNotFoundException e) {
                logger.error("Erro ao atualizar vagas: " + e.getMessage());
            }
        });
    }

    /**
     * Deleta viagem
     */
    public void deletarViagem(String id) throws ViagemNotFoundException {
        try {
            if (id == null || id.isEmpty()) {
                throw new ViagemNotFoundException("ID da viagem não pode ser vazio");
            }
            viagemRepository.deleteById(id);
            logger.info("Viagem deletada com ID: " + id);
        } finally {
            logger.info("Operação de deletar viagem finalizada");
        }
    }

    /**
     * Encerra o executor service
     */
    public void shutdown() {
        executorService.shutdown();
        logger.info("Executor service encerrado");
    }
}
