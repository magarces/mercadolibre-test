package com.mercadolibre.examen.magneto.controller;

import com.mercadolibre.examen.magneto.domain.DNA;
import com.mercadolibre.examen.magneto.exception.MutantException;
import com.mercadolibre.examen.magneto.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST Controller para identificar si un humano es mutante o no
 */
@RestController
@RequestMapping("/mutant")
public class MutantController {

  private static final Logger log = LoggerFactory.getLogger(MutantController.class);

  @Autowired
  private MutantService mutantService;

  /**
   * Identifica si un humano es mutante o no
   *
   * @param dna secuencia de dna del humano
   * @return 200 si es un mutante si no 403
   */
  @PostMapping("/")
  public ResponseEntity<Void> isMutant(@RequestBody @Valid DNA dna) {
    try {
      boolean isMutant = mutantService.isMutant(dna);
      if (isMutant) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }
    } catch (MutantException me) {
      log.error(me.getMessage(), me);
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

}
