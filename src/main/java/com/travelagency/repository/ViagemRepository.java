package com.travelagency.repository;

import com.travelagency.model.Viagem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository para Viagem
 * Requisito: Referência this e super (herança do MongoRepository)
 * Requisito: Persistência e recuperação de objetos
 */
@Repository
public interface ViagemRepository extends MongoRepository<Viagem, String> {
    List<Viagem> findByDestino(String destino);
    List<Viagem> findByAtivaTrue();
}
