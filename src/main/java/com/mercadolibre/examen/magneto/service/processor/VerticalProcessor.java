package com.mercadolibre.examen.magneto.service.processor;

import com.mercadolibre.examen.magneto.service.validator.ValidatorContext;

public class VerticalProcessor extends ProcessorMutant {

  public VerticalProcessor(ValidatorContext context) {
    super(context);
  }

  @Override
  public void searchMutant() {
    char[][] matrizDna = context.getMatrizDna();
    for (int column = 0; column < matrizDna.length; column++) {
      boolean match = findMutantSequence(Coordinate.at(matrizDna, 0, column));
      if (match) {
        break;
      }
    }
  }

  @Override
  protected void next(Coordinate coordinate) {
    coordinate.row++;
    coordinate.subIndex++;
    coordinate.lastChar = coordinate.curruntChar;
    coordinate.curruntChar = coordinate.dna[coordinate.row][coordinate.column];
  }

  @Override
  protected boolean hasNext(Coordinate coordinate, int matchSequence) {
    return coordinate.row + 1 <= coordinate.safeIndex;
  }
}
