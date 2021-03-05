package com.mercadolibre.examen.magneto.service.validator;

import com.mercadolibre.examen.magneto.service.processor.*;

import java.util.LinkedList;
import java.util.List;

public class ValidatorMutant {

  private ValidatorContext context;

  List<ProcessorMutant> processors = new LinkedList<>();

  public ValidatorMutant(char[][] matrizDna, int minSequencesMatch, int numSequencesToMutant) {
    context = new ValidatorContext(matrizDna, numSequencesToMutant, minSequencesMatch);

    loadProcessors();
  }

  private void registerProcessor(ProcessorMutant processor) {
    processors.add(processor);
  }

  void loadProcessors() {
    ProcessorMutant horizontal = new HorizontalProcessor(context);
    registerProcessor(horizontal);
    ProcessorMutant vertical = new VerticalProcessor(context);
    registerProcessor(vertical);
    ProcessorMutant diagnonal = new DiagonalProcessor(context);
    registerProcessor(diagnonal);
    ProcessorMutant diagnonalInverse = new DiagonalInverseProcessor(context);
    registerProcessor(diagnonalInverse);
  }

  /**
   * @return true si encuentra en la secuencia las coincidencias para mutante.
   */
  public boolean isMutante() {
    for (ProcessorMutant processor : processors) {
      processor.searchMutant();
      if (processor.hasMatchSequencesMutant()) {
        break;
      }
    }

    return context.getMatchs() >= context.getMinSequencesMatch();
  }
}
