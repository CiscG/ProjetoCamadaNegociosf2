package com.travelagency.repository;

import com.travelagency.model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository para Reserva
 * Requisito: Referência this e super (herança do MongoRepository)
 * Requisito: Persistência e recuperação de objetos
 */
@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {
    List<Reserva> findByClienteId(String clienteId);
    List<Reserva> findByViagemId(String viagemId);
}
