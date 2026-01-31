package com.weddingbliss.client.model;

public record Gast(String vorname,
                   String nachname,
                   boolean nimmtTeil,
                   String essenswunsch,
                   String allergien) {}
