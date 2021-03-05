package com.mercadolibre.examen.magneto.repository;

import com.mercadolibre.examen.magneto.domain.DNAVerified;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IDNAVerifiedRepository extends MongoRepository<DNAVerified, DNAVerified> {

}
