package com.weddingbliss.client.model;

/**
 * Repräsentiert einen Gast für eine Hochzeitseinladung.
 */
public class Gast {
    public String vorname;
    public String nachname;
    public Boolean kommt;
    public String essenswunsch;
    public String allergien;

    /** Standardkonstruktor. */
    public Gast() {}

    /**
     * Erstellt einen neuen Gast mit den angegebenen Attributen.
     *
     * @param vorname Der Vorname
     * @param nachname Der Nachname
     * @param kommt Teilnahme an der Hochzeit
     * @param essenswunsch Essenswunsch
     * @param allergien Allergien
     */
    public Gast(
        String vorname, String nachname, Boolean kommt, String essenswunsch, String allergien) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.kommt = kommt;
        this.essenswunsch = essenswunsch;
        this.allergien = allergien;
    }
}