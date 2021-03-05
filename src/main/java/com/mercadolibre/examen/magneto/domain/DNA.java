package com.mercadolibre.examen.magneto.domain;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DNA {

  @NotNull
  private List<String> dna;

  public List<String> getDna() {
    return dna;
  }

  public void setDna(List<String> dna) {
    this.dna = dna;
  }
}
