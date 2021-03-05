package com.mercadolibre.examen.magneto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {

  @JsonProperty("count_mutant_dna")
  private long mutantCount;

  @JsonProperty("count_human_dna")
  private long humanCount;

  private Double ratio;

  public long getMutantCount() {
    return mutantCount;
  }

  public void setMutantCount(long mutantCount) {
    this.mutantCount = mutantCount;
  }

  public long getHumanCount() {
    return humanCount;
  }

  public void setHumanCount(long humanCount) {
    this.humanCount = humanCount;
  }

  public Double getRatio() {
    return ratio;
  }

  public void setRatio(Double ratio) {
    this.ratio = ratio;
  }
}
