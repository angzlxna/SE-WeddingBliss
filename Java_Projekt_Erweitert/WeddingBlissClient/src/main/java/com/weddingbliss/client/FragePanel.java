package com.weddingbliss.client;

import com.weddingbliss.client.GastClientApp2;
import com.weddingbliss.client.ui.BgPanel;
import javax.swing.*;
import java.awt.*;

/**
 * Das FragePanel zeigt die zentrale Frage an,
 * ob der eingeladene Gast an der Hochzeit teilnehmen wird.
 * Der Benutzer kann zwischen "Ja" und "Nein" wählen
 * und wird abhängig von der Auswahl zum nächsten Panel weitergeleitet.
 */
public class FragePanel extends BgPanel {

	/** Radio-Button für Zusage zur Teilnahme. */
    private final JRadioButton ja;

    /** Radio-Button für Absage. */
    private final JRadioButton nein;

    /** Button zum Bestätigen der Auswahl. */
    private final JButton btnBest;

    /**
     * Konstruktor für das FragePanel.
     *
     * @param app         Referenz auf die Hauptanwendung (zur Navigation).
     * @param darkOverlay Transparente Overlay-Farbe für Lesbarkeit.
     * @param monte       Benutzerdefinierte Schriftart.
     */
    public FragePanel(GastClientApp2 app, Color darkOverlay, Font monte) {
        super("/images/6.jpeg", darkOverlay);
        setLayout(new GridBagLayout());

        // ---------- 2.1 Frage ----------
        JLabel frage1 = new JLabel(
                "Wirst du an unserem besonderen Tag dabei sein?",
                SwingConstants.CENTER);
        frage1.setFont(frage1.getFont().deriveFont(Font.PLAIN, 36f));
        frage1.setForeground(Color.WHITE);
        frage1.setName("frageLabel");

        // ---------- 2.2 Auswahl: Ja / Nein ----------
        ja = new JRadioButton("Ja");
        nein = new JRadioButton("Nein");
        ja.setName("radioJa");
        nein.setName("radioNein");
        

        for (JRadioButton rb : new JRadioButton[]{ja, nein}) {
            rb.setForeground(Color.WHITE);
            rb.setOpaque(false); // Hintergrund durchscheinen lassen
        }

        ButtonGroup grp = new ButtonGroup();
        grp.add(ja);
        grp.add(nein);

        // ---------- 2.3 Bestätigungs-Button ----------
        btnBest = new JButton("Bestätigen");
        btnBest.setName("frageWeiterButton");
        btnBest.addActionListener(e -> {
            if (ja.isSelected()) {
                app.showCard("kommen");
            } else if (nein.isSelected()) {
                app.showCard("absage");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Bitte wähle aus, ob du teilnehmen wirst.",
                    "Eingabefehler",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        // ---------- 2.4 Ja/Nein Zeile ----------
        JPanel jaNeinRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        jaNeinRow.setOpaque(false);
        jaNeinRow.add(ja);
        jaNeinRow.add(nein);

        // ---------- 2.5 Layout-Positionierung ----------
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0; add(frage1, gbc);
        gbc.gridy = 1; add(jaNeinRow, gbc);
        gbc.gridy = 2; add(btnBest, gbc);
    }
}
