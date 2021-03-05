package com.mercadolibre.examen.magneto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dna_verified")
public class DNAVerified {

  @Id
  private DNA dna;

  @Indexed(name = "is_mutant")
  private boolean isMutant;

  public DNA getDna() {
    return dna;
  }

  public void setDna(DNA dna) {
    this.dna = dna;
  }

  public boolean isMutant() {
    return isMutant;
  }

  public void setMutant(boolean mutant) {
    isMutant = mutant;
  }
}
