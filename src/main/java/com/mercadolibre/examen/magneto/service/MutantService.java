package com.mercadolibre.examen.magneto.service;

import com.mercadolibre.examen.magneto.domain.DNA;
import com.mercadolibre.examen.magneto.domain.DNAVerified;
import com.mercadolibre.examen.magneto.domain.Stats;
import com.mercadolibre.examen.magneto.exception.MutantException;
import com.mercadolibre.examen.magneto.repository.IDNAVerifiedRepository;
import com.mercadolibre.examen.magneto.service.validator.ValidatorMutant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class MutantService {

  @Autowired
  private IDNAVerifiedRepository dnaVerifiedRepository;

  private static final Pattern DNA_PATTERN = Pattern.compile("[atcg]+", Pattern.CASE_INSENSITIVE);

  /**
   * Procesa la sequencia de DNA y decide si el humano es mutante o no
   *
   * @param dna DNA
   * @return true si se encuentra DNA mutante, en caso contrario false
   */
  public boolean isMutant(DNA dna) throws MutantException {
    //Se valida que el numero minimo de secuencias sea, para que se pueda validar estructura mutante
    if (dna.getDna().size() < 4) {
      throw new MutantException("El tamaño minimo debe ser de 4 secuencias.");
    }

    char[][] matrizDna = loadDNAStructure(dna);
    ValidatorMutant validatorMutant = new ValidatorMutant(matrizDna, 2, 4);

    //Se verifica si el adn es de un mutante
    boolean isMutante = validatorMutant.isMutante();

    //Se crea el objeto que se almacena en BD y guarda
    DNAVerified dnaVerified = new DNAVerified();
    dnaVerified.setDna(dna);
    dnaVerified.setMutant(isMutante);

    this.dnaVerifiedRepository.save(dnaVerified);

    return isMutante;
  }

  /**
   * Convierte la secuencia de DNA en una matriz
   *
   * @param dna DNA
   * @return matriz que representa la secuencia del DNA
   */
  private char[][] loadDNAStructure(DNA dna) throws MutantException {
    int dnaLength = dna.getDna().size();
    char[][] matrizDna = new char[dnaLength][dnaLength];

    for (int i = 0; i < dnaLength; i++) {
      String dnaRow = dna.getDna().get(i);
      validateDNAStructure(dnaLength, dnaRow);
      matrizDna[i] = dnaRow.toUpperCase().toCharArray();
    }
    return matrizDna;
  }

  /**
   * Valida la estructura y los tamaños del dna
   *
   * @param dnaLength Numero de secuencias del dna
   * @param dnaRow    Informacion de la fila a validar
   * @throws MutantException si el numero de secuencias es diferente al numero de letras del registro o si
   *                         algun dato del registro a validar no cumple con la expresion regular
   */
  private void validateDNAStructure(int dnaLength, String dnaRow) throws MutantException {
    if (dnaRow.length() != dnaLength) {
      throw new MutantException("El tamaño de la secuencia no coincide con la de las cadenas.");

    } else if (!DNA_PATTERN.matcher(dnaRow).matches()) {
      throw new MutantException("La información de la secuencia del dna es incorrecta.");
    }
  }

  /**
   * Obtiene las estadisticas de los mutantes y humanos registrados y el ratio
   *
   * @return cantidad de humanos, mutantes y ratio
   */
  public Stats getStats() {
    List<DNAVerified> listDnaVerified = dnaVerifiedRepository.findAll();
    Stats stats = new Stats();
    stats.setHumanCount(listDnaVerified.stream().filter(dna -> !dna.isMutant()).count());
    stats.setMutantCount(listDnaVerified.stream().filter(dna -> dna.isMutant()).count());
    stats.setRatio(stats.getMutantCount() != 0 && stats.getHumanCount() != 0 ? Double.valueOf(stats.getMutantCount()) / Double.valueOf(stats.getHumanCount()) : 0);

    return stats;
  }

}
