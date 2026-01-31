package com.weddingbliss.client;

import com.weddingbliss.client.GastClientApp2;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Das UberunsPanel zeigt ein Firmenporträt von Wedding Bliss sowie Kontaktdaten.
 * Es dient als Informationsseite innerhalb der Anwendung.
 */
public class UberunsPanel extends JPanel {

	/** Button zur Rückkehr zur Startseite. */
    private final JButton btnZurueck;

    /**
     * Konstruktor für das Über-uns-Panel.
     *
     * @param app    Referenz zur Hauptanwendung zur Navigation zurück zur Startseite.
     * @param white  Hintergrundfarbe (meist weiß).
     * @param monte  Benutzerdefinierte Schriftart.
     */
    public UberunsPanel(GastClientApp2 app, Color white, Font monte) {
        super(new BorderLayout());
        setBackground(white);

        // ---------- Top-Bar mit Zurück-Button ----------
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        topBar.setOpaque(false);
        btnZurueck = new JButton("Zurück");
        topBar.add(btnZurueck);
        add(topBar, BorderLayout.NORTH);
        btnZurueck.setName("ZurückButton");
        btnZurueck.addActionListener(e -> app.showCard("start"));

        // ---------- Center-Panel mit Hauptinhalten ----------
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // ---------- Firmenlogo ----------
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0);
        URL logoUrl = GastClientApp2.class.getClassLoader().getResource("logo.jpeg");
        if (logoUrl != null) {
            ImageIcon original = new ImageIcon(logoUrl);
            Image scaled = original.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            center.add(new JLabel(new ImageIcon(scaled)), gbc);
        } else {
            System.err.println("Logo nicht gefunden: logo.jpeg");
        }

        // ---------- Titel ----------
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        JLabel lblTitel = new JLabel("Wedding Bliss", SwingConstants.CENTER);
        lblTitel.setFont(monte.deriveFont(Font.BOLD, 36f));
        center.add(lblTitel, gbc);
        lblTitel.setName("ueberUnsLabel");

        // ---------- HTML-Firmenbeschreibung ----------
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 25, 0);
        String htmlText =
            "<html><div style='width:600px;text-align:center;'>"
          + "Wedding&nbsp;Bliss ist ein Düsseldorfer Full-Service-Studio, das seit&nbsp;2014 "
          + "außergewöhnliche Hochzeiten deutschlandweit und international realisiert. "
          + "Unser 15-köpfiges Team vereint Eventdesign, Projekt&shy;management und langjährige "
          + "Branchen&shy;expertise, um jedes Fest nahtlos von der ersten Idee bis zum letzten Tanz "
          + "zu begleiten.<br><br>"
          + "Gemeinsam mit unseren Paaren entwickeln wir ein maßgeschneidertes Konzept, das Stil, "
          + "Budget und persönliche Geschichten widerspiegelt – vom intimen Gartenbrunch über das "
          + "elegante Schlossdinner bis zur dreitägigen Destination Wedding. Wir übernehmen "
          + "Design&nbsp;&amp;&nbsp;Dekoration, Teil- oder Komplettorganisation, zuverlässige "
          + "Partnerkoordination und die Ablaufregie am Hochzeitstag.<br><br>"
          + "<strong>Digitale&nbsp;Einladung:</strong> Über unser interaktives Online-Portal können "
          + "Gäste bequem zusagen und Menüwünsche wählen – so behalten Brautpaare jederzeit den "
          + "Überblick, während die Gäste schon im Vorfeld in die Feier eintauchen.<br><br>"
          + "Durch klare Strukturen, transparente Kommunikation und unsere Liebe zum Detail schaffen "
          + "wir freie Köpfe und echte Vorfreude – damit der große Tag wirklich nur eins bleibt: "
          + "<em>unvergesslich.</em>"
          + "</div></html>";
        center.add(new JLabel(htmlText, SwingConstants.CENTER), gbc);

        // ---------- Kontaktinformationen ----------
        gbc.gridy = 3;
        gbc.insets = new Insets(120, 0, 35, 0);
        String contactHtml =
            "<html><div style='text-align:center;'>"
          + "<strong>Kontakt</strong><br>"
          + "Wedding&nbsp;Bliss&nbsp;GmbH&nbsp;&nbsp;|&nbsp;&nbsp;Musterstraße&nbsp;123&nbsp;&nbsp;|&nbsp;&nbsp;40210&nbsp;Düsseldorf<br>"
          + "Tel.&nbsp;+49&nbsp;211&nbsp;1234567&nbsp;&nbsp;|&nbsp;&nbsp;"
          + "E-Mail:&nbsp;<a href='mailto:info@weddingbliss.de'>info@weddingbliss.de</a>&nbsp;&nbsp;|&nbsp;&nbsp;"
          + "<a href='https://www.weddingbliss.de'>www.weddingbliss.de</a>"
          + "</div></html>";
        center.add(new JLabel(contactHtml, SwingConstants.CENTER), gbc);

        // ---------- Füller für Resthöhe ----------
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        center.add(Box.createVerticalGlue(), gbc);

        add(center, BorderLayout.CENTER);
    }
}