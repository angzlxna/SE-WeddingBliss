package com.weddingbliss.client;

import com.weddingbliss.client.GastClientApp2;
import javax.swing.*;
import java.awt.*;

/**
 * Das TeilnahmePanel begrüßt den Gast, wenn er seine Teilnahme zugesagt hat,
 * zeigt eine freundliche Nachricht und fragt anschließend nach Essenspräferenzen.
 * Enthält Vorschaubilder für Menüoptionen.
 */
public class TeilnahmePanel extends JPanel {

	/** Button zum Fortfahren zur Essensauswahl. */
    private final JButton btnWeiterKommen;

    /**
     * Konstruktor für das TeilnahmePanel.
     *
     * @param app   Referenz auf die Hauptanwendung zur Navigation.
     * @param monte Benutzerdefinierte Schriftart.
     * @param brown Hintergrundfarbe in Hochzeitsfarbton.
     */
    public TeilnahmePanel(GastClientApp2 app, Font monte, Color brown) {
        setLayout(new GridBagLayout());
        setBackground(brown);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // ---------- 3.1 Überschrift ----------
        JLabel lblTitle = new JLabel("Wir freuen uns auf dein Kommen!",
                SwingConstants.CENTER);
        lblTitle.setFont(monte.deriveFont(Font.ITALIC, 100f));
        lblTitle.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(lblTitle, gbc);
        lblTitle.setName("kommenLabel");

        // ---------- 3.2 Untertitel ----------
        JLabel lblUntertitle = new JLabel(
                "Nun wollen wir wissen, was deine Esspräferenzen sind",
                SwingConstants.CENTER);
        lblUntertitle.setFont(lblUntertitle.getFont().deriveFont(Font.PLAIN, 24f));
        lblUntertitle.setForeground(Color.WHITE);
        gbc.gridy = 1;
        add(lblUntertitle, gbc);
        lblUntertitle.setName("kommenLabel2");

        // ---------- 3.3 Vorschaubilder für Menüoptionen ----------
        JPanel imageRow = new JPanel(new GridLayout(1, 3, 10, 0));
        imageRow.setOpaque(false);

        int newW = 300, newH = 400;
        String[] paths = {"/images/3.jpeg", "/images/1.jpeg", "/images/5.jpeg"};
        for (String path : paths) {
            ImageIcon orig = new ImageIcon(GastClientApp2.class.getResource(path));
            Image img = orig.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            JLabel pic = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            pic.setPreferredSize(new Dimension(newW, newH));
            imageRow.add(pic);
        }

        gbc.gridy = 2;
        add(imageRow, gbc);

        // ---------- 3.4 Weiter-Button ----------
        btnWeiterKommen = new JButton("Weiter");
        btnWeiterKommen.setName("essenWeiterButton");
        btnWeiterKommen.addActionListener(e -> app.showCard("essen"));
        gbc.gridy = 3;
        add(btnWeiterKommen, gbc);
    }
}
