package com.mercadolibre.examen.magneto.service.processor;

import com.mercadolibre.examen.magneto.service.validator.ValidatorContext;

public class DiagonalProcessor extends ProcessorMutant {

  public DiagonalProcessor(ValidatorContext context) {
    super(context);
  }

  @Override
  public void searchMutant() {
    char[][] matrizDna = context.getMatrizDna();

    boolean match = findMutantSequence(Coordinate.at(matrizDna, 0, 0));
    if (match) {
      return;
    }
    for (int row = 1; row <= matrizDna.length - context.getNumSequencesToMutant(); row++) {
      match = findMutantSequence(Coordinate.at(matrizDna, row, 0, row))
          || findMutantSequence(Coordinate.at(matrizDna, 0, row, row));
      if (match) {
        break;
      }
    }
  }

  @Override
  protected void next(Coordinate coordinate) {
    coordinate.row++;
    coordinate.column++;
    coordinate.subIndex++;
    coordinate.lastChar = coordinate.curruntChar;
    coordinate.curruntChar = coordinate.dna[coordinate.row][coordinate.column];
  }

  @Override
  protected boolean hasNext(Coordinate coordinate, int matchSequence) {
    int subIndex = coordinate.subIndex;
    int available = coordinate.size - subIndex;
    return available + matchSequence >= context.getNumSequencesToMutant()
        && Math.max(coordinate.column, coordinate.row) + 1 < coordinate.size;
  }

}
