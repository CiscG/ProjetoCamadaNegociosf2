package com.travelagency.repository;

import com.travelagency.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository para Cliente
 * Requisito: Referência this e super (herança do MongoRepository)
 * Requisito: Persistência e recuperação de objetos
 */
@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
}
