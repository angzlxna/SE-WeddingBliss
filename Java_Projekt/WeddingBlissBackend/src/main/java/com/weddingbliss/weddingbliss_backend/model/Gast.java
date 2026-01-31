package com.weddingbliss.weddingbliss_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Modellklasse für einen Gast mit relevanten Eigenschaften. */
public class Gast {
  private String vorname;
  private String nachname;

  @JsonProperty private Boolean kommt = true;

  private String essenswunsch;
  private String allergien;

  public Gast() {}

  public Gast(String vorname, String nachname, Boolean kommt, String essenswunsch, String allergien) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.kommt = kommt;
    this.essenswunsch = essenswunsch;
    this.allergien = allergien;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  @JsonProperty
  public Boolean getKommt() {
    return kommt;
  }

  @JsonProperty
  public void setKommt(Boolean kommt) {
    this.kommt = kommt;
  }

  public String getEssenswunsch() {
    return essenswunsch;
  }

  public void setEssenswunsch(String essenswunsch) {
    this.essenswunsch = essenswunsch;
  }

  public String getAllergien() {
    return allergien;
  }

  public void setAllergien(String allergien) {
    this.allergien = allergien;
  }
}
