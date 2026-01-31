package com.weddingbliss.client;

import com.weddingbliss.client.GastClientApp2;
import com.weddingbliss.client.ui.BgPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Das FreuenPanel zeigt nach erfolgreicher Anmeldung und Eingabe der Essenspräferenzen
 * eine abschließende Dankesnachricht und beendet die Anwendung nach Klick auf „Schließen“.
 */
public class FreuenPanel extends BgPanel {

	/** Schließen-Button zur Beendigung der Anwendung. */
    private final JButton btnCloseThanks;

    /**
     * Konstruktor für das FreuenPanel.
     *
     * @param app          Referenz zur Hauptanwendung (nicht direkt verwendet, aber für Konsistenz übergeben).
     * @param monte        Benutzerdefinierte Schriftart.
     * @param darkOverlay  Transparente Overlay-Farbe für den Hintergrund.
     */
    public FreuenPanel(GastClientApp2 app, Font monte, Color darkOverlay) {
        super("/images/4.jpeg", darkOverlay);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 8);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // ---------- 5.1 Text ----------
        gbc.gridy = 0;
        JLabel lblDanke = new JLabel("Wir freuen uns auf Dich", SwingConstants.CENTER);
        lblDanke.setFont(monte.deriveFont(Font.ITALIC, 150f));
        lblDanke.setForeground(Color.WHITE);
        add(lblDanke, gbc);
        lblDanke.setName("freuenLabel");

        // ---------- 5.2 Schließen-Button ----------
        gbc.gridy = 1;
        btnCloseThanks = new JButton("Schließen");
        btnCloseThanks.addActionListener(e -> System.exit(0));
        add(btnCloseThanks, gbc);
        btnCloseThanks.setName("schließenButton");
    }
}

