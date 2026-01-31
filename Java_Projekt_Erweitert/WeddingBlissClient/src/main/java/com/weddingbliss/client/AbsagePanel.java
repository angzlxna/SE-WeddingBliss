package com.weddingbliss.client;

import javax.swing.*;
import java.awt.*;
import com.weddingbliss.client.ui.BgPanel;

/**
 * Das AbsagePanel wird angezeigt, wenn ein Gast angibt, nicht teilnehmen zu können.
 * Es enthält eine freundliche Nachricht und einen Button zum Beenden der Anwendung.
 */
public class AbsagePanel extends BgPanel {

	/** Button zum Schließen der Anwendung. */
    private final JButton btnClose;

    /**
     * Konstruktor für das AbsagePanel.
     *
     * @param gastClientApp2 Referenz zur Hauptanwendung (nicht direkt verwendet, für Konsistenz übergeben).
     * @param darkOverlay    Transparente Overlay-Farbe für den Hintergrund.
     * @param monte          Benutzerdefinierte Schriftart.
     */
    public AbsagePanel(GastClientApp2 gastClientApp2, Color darkOverlay, Font monte) {
        super("/images/2.jpeg", darkOverlay);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // ---------- 6.1 Erste Nachricht ----------
        gbc.gridy = 0;
        JLabel lblAbsage1 = new JLabel("Danke für deine Absage.", SwingConstants.CENTER);
        lblAbsage1.setFont(lblAbsage1.getFont().deriveFont(Font.PLAIN, 36f));
        lblAbsage1.setForeground(Color.WHITE);
        add(lblAbsage1, gbc);
        lblAbsage1.setName("absageLabel");

        // ---------- 6.2 Zweite Nachricht ----------
        gbc.gridy = 1;
        JLabel lblAbsage2 = new JLabel("Schade, dass du nicht dabei sein kannst", SwingConstants.CENTER);
        lblAbsage2.setFont(lblAbsage2.getFont().deriveFont(Font.PLAIN, 36f));
        lblAbsage2.setForeground(Color.WHITE);
        add(lblAbsage2, gbc);
        lblAbsage2.setName("absageLabel2");

        // ---------- 6.3 Schließen-Button ----------
        gbc.gridy = 2;
        btnClose = new JButton("Schließen");
        btnClose.setName("absageWeiterButton");
        btnClose.addActionListener(e -> System.exit(0));
        add(btnClose, gbc);
    }
}
