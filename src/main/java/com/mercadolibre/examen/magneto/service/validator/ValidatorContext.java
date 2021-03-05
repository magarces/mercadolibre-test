package com.mercadolibre.examen.magneto.service.validator;

public class ValidatorContext {

  char[][] matrizDna;
  private int numSequencesToMutant;
  private int minSequencesMatch;
  private int matchs;

  public ValidatorContext(char[][] matrizDna, int numSequencesToMutant, int minSequencesMatch) {
    this.matrizDna = matrizDna;
    this.numSequencesToMutant = numSequencesToMutant;
    this.minSequencesMatch = minSequencesMatch;
  }

  public char[][] getMatrizDna() {
    return matrizDna;
  }

  public int getNumSequencesToMutant() {
    return numSequencesToMutant;
  }

  public int getMinSequencesMatch() {
    return minSequencesMatch;
  }

  public int getMatchs() {
    return matchs;
  }

  public void addMatch() {
    this.matchs++;
  }
}
