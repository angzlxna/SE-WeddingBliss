package com.weddingbliss.client;

public class Gast {
    public String name;
    public boolean kommt;
    public String essenswunsch;
    public String allergien;

    public Gast() {}

    public Gast(String name, boolean kommt, String essenswunsch, String allergien) {
        this.name = name;
        this.kommt = kommt;
        this.essenswunsch = essenswunsch;
        this.allergien = allergien;
    }
}