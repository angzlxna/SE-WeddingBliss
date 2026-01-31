package com.weddingbliss.weddingbliss_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weddingbliss.weddingbliss_backend.model.Gast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/** Service-Klasse zur Verwaltung von Gastdaten. */
@Service
public class GastService {

  private static final String DATEIPFAD = "src/main/resources/guests.json";
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Lädt die gespeicherten Gäste aus einer JSON-Datei.
   *
   * @return Liste der Gäste
   */
  public List<Gast> ladeGaeste() {
    try {
      File file = new File(DATEIPFAD);
      if (!file.exists()) {
        return new ArrayList<>();
      }
      return mapper.readValue(file, new TypeReference<List<Gast>>() {});
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  /**
   * Fügt einen neuen Gast hinzu und speichert die Liste.
   *
   * @param gast Das hinzuzufügende Gastobjekt
   */
  public void gastHinzufuegen(Gast gast) {
    List<Gast> gaeste = ladeGaeste();
    gaeste.add(gast);
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATEIPFAD), gaeste);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
