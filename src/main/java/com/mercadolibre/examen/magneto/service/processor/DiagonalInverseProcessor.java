package com.mercadolibre.examen.magneto.service.processor;

import com.mercadolibre.examen.magneto.service.validator.ValidatorContext;

public class DiagonalInverseProcessor extends ProcessorMutant {

  public DiagonalInverseProcessor(ValidatorContext context) {
    super(context);
  }

  @Override
  public void searchMutant() {
    char[][] matrizDna = context.getMatrizDna();

    int firstRow = matrizDna.length - 1;
    int firstColumn = 0;
    boolean match = findMutantSequence(Coordinate.at(matrizDna, firstRow, firstColumn));
    if (match) {
      return;
    }
    for (int row = 1; row <= matrizDna.length - context.getNumSequencesToMutant(); row++) {
      match = findMutantSequence(Coordinate.at(matrizDna, firstRow - row, 0, row))
          || findMutantSequence(Coordinate.at(matrizDna, firstRow, row, row));
      if (match) {
        break;
      }
    }
  }

  @Override
  protected void next(Coordinate coordinate) {
    coordinate.row--;
    coordinate.column++;
    coordinate.subIndex++;
    coordinate.lastChar = coordinate.curruntChar;
    coordinate.curruntChar = coordinate.dna[coordinate.row][coordinate.column];
  }

  @Override
  protected boolean hasNext(Coordinate coordinate, int matchSequence) {
    int index = coordinate.subIndex;
    int available = coordinate.size - index;
    return available + matchSequence >= context.getNumSequencesToMutant() && coordinate.row - 1 >= 0
        && coordinate.column + 1 < coordinate.size;
  }
}
