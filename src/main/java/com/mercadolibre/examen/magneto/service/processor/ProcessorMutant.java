package com.mercadolibre.examen.magneto.service.processor;

import com.mercadolibre.examen.magneto.service.validator.ValidatorContext;

public abstract class ProcessorMutant {

  protected ValidatorContext context;

  protected ProcessorMutant(ValidatorContext context) {
    this.context = context;
  }

  /**
   * Analiza la secuencia para buscar mutantes
   */
  public abstract void searchMutant();

  /**
   * Cambia a la siguiente posicion de la matriz
   *
   * @param coordinate
   */
  protected abstract void next(Coordinate coordinate);

  /**
   * @param coordinate
   * @param matchSequence numero de coincidencias en la secuencias
   * @return true si la siguiente posicion es valida, en caso contrario false
   */
  protected abstract boolean hasNext(Coordinate coordinate, int matchSequence);

  /**
   * Busca la secuencia mutante en la matriz
   *
   * @param coordidate first Coordinate
   * @return true si hay mas de una coincidencia para mutante en la secuencia del dna en caso contrario false
   */
  protected boolean findMutantSequence(Coordinate coordidate) {
    char currentChar = coordidate.dna[coordidate.row][coordidate.column];
    int matchSequence = 1;
    while (hasNext(coordidate, matchSequence)) {
      next(coordidate);

      if (currentChar != coordidate.curruntChar) {
        matchSequence = 1;
        currentChar = coordidate.curruntChar;
      } else if (++matchSequence >= context.getNumSequencesToMutant()) {
        context.addMatch();

        if (hasMatchSequencesMutant()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Analiza si hay mas de una coincidencia en las secuencias para mutante
   *
   * @return true si el total de coincidencias es mayor o igual al minimo de secuencias de coincidencia sino retorna false
   */
  public boolean hasMatchSequencesMutant() {
    int count = context.getMinSequencesMatch();
    return context.getMatchs() >= count;
  }
  
}
