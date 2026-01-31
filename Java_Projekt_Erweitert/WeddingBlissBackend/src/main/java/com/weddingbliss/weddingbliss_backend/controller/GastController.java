package com.weddingbliss.weddingbliss_backend.controller;

import com.weddingbliss.weddingbliss_backend.model.Gast;
import com.weddingbliss.weddingbliss_backend.service.GastService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** REST-Controller für Gäste-Endpunkte. */
@RestController
@RequestMapping("/api/gaeste")
public class GastController {

  @Autowired private GastService gastService;

  /**
   * Gibt eine Liste aller Gäste zurück.
   *
   * @return Liste von Gast-Objekten
   */
  @GetMapping
  public List<Gast> alleGaeste() {
    return gastService.ladeGaeste();
  }

  /**
   * Fügt einen neuen Gast hinzu.
   *
   * @param gast Gast-Objekt im Request-Body
   */
  @PostMapping
  public void gastHinzufuegen(@RequestBody Gast gast) {
    gastService.gastHinzufuegen(gast);
  }
}
