package com.mercadolibre.examen.magneto.service.processor;

import com.mercadolibre.examen.magneto.service.validator.ValidatorContext;

public class HorizontalProcessor extends ProcessorMutant {

  public HorizontalProcessor(ValidatorContext context) {
    super(context);
  }

  @Override
  public void searchMutant() {
    char[][] matrizDna = context.getMatrizDna();
    for (int row = 0; row < matrizDna.length; row++) {
      boolean match = findMutantSequence(Coordinate.at(matrizDna, row, 0));
      if (match) {
        break;
      }
    }
  }

  @Override
  protected void next(Coordinate coordinate) {
    coordinate.column++;
    coordinate.subIndex++;
    coordinate.lastChar = coordinate.curruntChar;
    coordinate.curruntChar = coordinate.dna[coordinate.row][coordinate.column];
  }

  @Override
  protected boolean hasNext(Coordinate coordinate, int matchSequence) {
    return coordinate.column + 1 <= coordinate.safeIndex;
  }

}
