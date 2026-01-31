package com.weddingbliss.client;

import com.weddingbliss.client.GastClientApp2;
import com.weddingbliss.client.ui.BgPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Das EssenPanel ermöglicht dem Gast die Auswahl seiner Essenspräferenz (Fleisch, Vegetarisch, Vegan)
 * und die Angabe möglicher Allergien. Nach Validierung der Eingaben werden die Daten übermittelt.
 */
public class EssenPanel extends BgPanel {

	/** Radio-Button für Fleischgericht. */
    private final JRadioButton rbFleisch;

    /** Radio-Button für veganes Gericht. */
    private final JRadioButton rbVegan;

    /** Radio-Button für vegetarisches Gericht. */
    private final JRadioButton rbVegetarisch;

    /** Button zum Absenden der Angaben. */
    private final JButton btnSenden;

    /**
     * Konstruktor für das EssenPanel.
     *
     * @param app         Referenz zur Hauptanwendung (für Zugriff auf Textfelder und Navigation).
     * @param darkOverlay Transparente Overlay-Farbe für Bildhintergrund.
     * @param monte       Benutzerdefinierte Schriftart.
     */
    public EssenPanel(GastClientApp2 app, Color darkOverlay, Font monte) {
        super("/images/7.jpeg", darkOverlay);
        setLayout(new GridBagLayout());

        GridBagConstraints gE = new GridBagConstraints();
        gE.insets = new Insets(12, 25, 12, 25);
        gE.anchor = GridBagConstraints.WEST;

        // ---------- 4.2 Überschrift ----------
        gE.gridx = 0; gE.gridy = 0; gE.gridwidth = 2; gE.anchor = GridBagConstraints.CENTER;
        JLabel frage2 = new JLabel("Deine Essenspräferenz", SwingConstants.CENTER);
        frage2.setFont(frage2.getFont().deriveFont(Font.BOLD, 46f));
        frage2.setForeground(Color.WHITE);
        add(frage2, gE);
        frage2.setName("frageLabel2");

        // ---------- 4.3 Linke Spalte – Essenswahl ----------
        gE.gridwidth = 1; gE.anchor = GridBagConstraints.WEST;
        gE.gridx = 0; gE.gridy = 1;
        JLabel lblEss = new JLabel("Essenswunsch:");
        lblEss.setFont(frage2.getFont().deriveFont(Font.PLAIN, 24f));
        lblEss.setForeground(Color.WHITE);
        add(lblEss, gE);
        lblEss.setName("essenLabel");

        rbFleisch = new JRadioButton("Fleisch");
        rbVegan = new JRadioButton("Vegan");
        rbVegetarisch = new JRadioButton("Vegetarisch");
        rbFleisch.setName("radioFleisch");
        rbVegan.setName("radioVegan");
        rbVegetarisch.setName("radioVegetarisch");

        ButtonGroup grpGericht = new ButtonGroup();
        grpGericht.add(rbFleisch);
        grpGericht.add(rbVegan);
        grpGericht.add(rbVegetarisch);

        for (JRadioButton rb : new JRadioButton[]{rbFleisch, rbVegan, rbVegetarisch}) {
            rb.setForeground(Color.WHITE);
            rb.setOpaque(false);
        }

        gE.gridy = 2; add(rbFleisch, gE);
        gE.gridy = 3; add(rbVegan, gE);
        gE.gridy = 4; add(rbVegetarisch, gE);

        // ---------- 4.4 Rechte Spalte – Allergien ----------
        gE.gridx = 1; gE.gridy = 1;
        JLabel lblAll = new JLabel("Allergien:");
        lblAll.setFont(frage2.getFont().deriveFont(Font.PLAIN, 24f));
        lblAll.setForeground(Color.WHITE);
        add(lblAll, gE);
        lblAll.setName("allergienLabel");

        gE.gridy = 2;
        add(app.tfAllergie, gE);

        // ---------- 4.5 Senden-Button ----------
        btnSenden = new JButton("Senden");
        btnSenden.setName("essenWeiterButton");
        gE.gridy = 5; gE.anchor = GridBagConstraints.EAST;
        add(btnSenden, gE);
        
        app.tfAllergie.setName("allergieFeld");

        btnSenden.addActionListener(e -> {
            String gericht = null;
            if (rbFleisch.isSelected()) {
                gericht = "Fleisch";
            } else if (rbVegetarisch.isSelected()) {
                gericht = "Vegetarisch";
            } else if (rbVegan.isSelected()) {
                gericht = "Vegan";
            }

            if (gericht == null) {
                JOptionPane.showMessageDialog(this,
                    "Bitte wähle eine Essenspräferenz aus.",
                    "Eingabefehler",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String allergie = app.tfAllergie.getText().trim();

            if (allergie.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Bitte gib an, ob du gegen bestimmte Nahrungsmittel allergisch bist. Wenn nicht, schreib \"keine\".",
                    "Eingabefehler",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!GastClientApp2.istGueltigerName(allergie)) {
                JOptionPane.showMessageDialog(this,
                    "Bitte gib gültige Zeichen ein.",
                    "Ungültige Allergie",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Daten sammeln und senden
            String vorname = app.vorF.getText().trim();
            String nachname = app.nachF.getText().trim();

            com.weddingbliss.client.model.Gast g = new com.weddingbliss.client.model.Gast(vorname, nachname, true, gericht, allergie);
            app.sendeGast(g);
            app.showCard("freuen");
        });
    }
}
