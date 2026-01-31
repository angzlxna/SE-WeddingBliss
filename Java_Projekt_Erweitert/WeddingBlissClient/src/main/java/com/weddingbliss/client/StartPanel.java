package com.weddingbliss.client;

import javax.swing.*;
import java.awt.*;
import com.weddingbliss.client.GastClientApp2;
import com.weddingbliss.client.ui.BgPanel;

/**
 * Das Einstiegs-Panel des Gästeportals.
 * Zeigt Begrüßung, Einführungstext, Namensfelder und Weiter-Button an.
 * Benutzer können hier ihren Vor- und Nachnamen eingeben und zur nächsten Ansicht wechseln.
 */
public class StartPanel extends BgPanel {
	
	/** Button zum Wechsel auf die "Über uns"-Seite. */
    private final JButton btnUberUns;

    /** Button zum Fortfahren zur nächsten Frage. */
    private final JButton btnWeiter;

    /**
     * Konstruktor für das StartPanel.
     *
     * @param app         Die Hauptanwendung (zum Zugriff auf Felder und Navigation).
     * @param monte       Benutzerdefinierte Schriftart (MonteCarlo).
     * @param darkOverlay Halbtransparente Overlay-Farbe für Textkontrast.
     */
    public StartPanel(GastClientApp2 app, Font monte, Color darkOverlay) {
        super("/images/6.jpeg", darkOverlay);
        setLayout(new GridBagLayout());

        // ---------- 1.1 Navigation-Button „Über Wedding Bliss“ ----------
        btnUberUns = new JButton("Über 'Wedding Bliss'");
        btnUberUns.addActionListener(e -> app.showCard("uberuns"));

        // ---------- 1.2 Überschrift und Einführungstexte ----------
        JLabel headline = new JLabel("Feier mit uns!", SwingConstants.LEFT);
        headline.setFont(monte.deriveFont(Font.ITALIC, 200f));
        headline.setForeground(Color.WHITE);
        headline.setName("headlineLabel");

        JLabel text1 = new JLabel("Wir laden Dich herzlich zu unserer Hochzeit ein!", SwingConstants.LEFT);
        JLabel text2 = new JLabel("Beantworte ein paar Fragen damit unsere Planung einwandfrei funktionieren kann", SwingConstants.LEFT);
        JLabel text3 = new JLabel("Trage bitte deinen Vor- und Nachnamen ein:", SwingConstants.LEFT);

        for (JLabel l : new JLabel[]{text1, text2, text3}) {
            l.setForeground(Color.WHITE);
        }

        text1.setFont(text1.getFont().deriveFont(Font.PLAIN, 46f));
        text2.setFont(text2.getFont().deriveFont(Font.PLAIN, 18f));
        text3.setFont(text3.getFont().deriveFont(Font.PLAIN, 24f));
        text1.setName("text1Label");
        text2.setName("text2Label");
        text3.setName("text3Label");

        // ---------- 1.3 Eingabefelder und Weiter-Button ----------
        btnWeiter = new JButton("Weiter");
        
        app.vorF.setName("vornameFeld");
        app.nachF.setName("nachnameFeld");
        btnWeiter.setName("weiterButton");
        btnUberUns.setName("ueberUnsButton");

        // Validierung und Navigation beim Klick auf „Weiter“
        btnWeiter.addActionListener(e -> {
            String vorname = app.vorF.getText().trim();
            String nachname = app.nachF.getText().trim();

            if (vorname.isEmpty() || nachname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bitte fülle beide Namensfelder aus.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!GastClientApp2.istGueltigerName(vorname) || !GastClientApp2.istGueltigerName(nachname)) {
                JOptionPane.showMessageDialog(this, "Bitte gib gültige Vor- und Nachnamen ein.", "Ungültiger Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            app.showCard("frage");
        });

        // ---------- Eingabereihe zusammenbauen ----------
        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameRow.setOpaque(false);
        nameRow.add(app.vorF);
        nameRow.add(app.nachF);
        nameRow.add(btnWeiter);

        // ---------- 1.4 GridBagConstraints für Layout ----------
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 8);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // ---------- 1.5 Button oben rechts platzieren ----------
        GridBagConstraints gbcTopRight = new GridBagConstraints();
        gbcTopRight.gridx = 1;
        gbcTopRight.gridy = 0;
        gbcTopRight.anchor = GridBagConstraints.NORTHEAST;
        gbcTopRight.insets = new Insets(20, 8, 8, 30);
        add(btnUberUns, gbcTopRight);

        // ---------- 1.6 Inhalte ins Layout einfügen ----------
        gbc.gridy = 1; add(headline, gbc);
        gbc.gridy = 2; add(text1, gbc);
        gbc.gridy = 3; add(text2, gbc);
        gbc.gridy = 4; add(text3, gbc);
        gbc.gridy = 5; add(nameRow, gbc);
    }
}
