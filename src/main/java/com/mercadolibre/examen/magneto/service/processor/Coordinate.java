package com.mercadolibre.examen.magneto.service.processor;

public class Coordinate {

  char dna[][];
  int subIndex;
  int row;
  int column;
  char lastChar;
  char curruntChar;
  int safeIndex;
  int size;

  public Coordinate(char[][] dna, int subIndex, int row, int column) {
    super();
    this.dna = dna;
    this.safeIndex = dna.length - 1;
    this.size = dna.length;
    this.subIndex = subIndex;
    this.row = row;
    this.column = column;
    this.lastChar = dna[row][column];
  }

  public static Coordinate at(char[][] dna, int row, int column) {
    return new Coordinate(dna, 0, row, column);
  }

  public static Coordinate at(char[][] dna, int row, int column, int index) {
    return new Coordinate(dna, index, row, column);
  }
}
