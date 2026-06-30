package com.airbnbclone.repository;

import com.airbnbclone.model.Propriedade;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PropriedadeRepository extends MongoRepository<Propriedade, String> {
    List<Propriedade> findByAnfitriaoId(String anfitriaoId);
}
