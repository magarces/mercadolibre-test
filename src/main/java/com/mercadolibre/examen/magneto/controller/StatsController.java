package com.mercadolibre.examen.magneto.controller;

import com.mercadolibre.examen.magneto.domain.Stats;
import com.mercadolibre.examen.magneto.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para consultar las estadisticas de verificaciones de mutantes
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

  private static final Logger log = LoggerFactory.getLogger(StatsController.class);

  @Autowired
  private MutantService mutantService;

  /**
   * Devuelve la cantidad de humanos y mutantes y el porcentaje de mutantes
   *
   * @return Stats
   */
  @GetMapping("")
  public ResponseEntity<Stats> stats() {
    return ResponseEntity.ok(mutantService.getStats());
  }

}
