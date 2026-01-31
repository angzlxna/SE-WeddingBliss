package com.weddingbliss.client.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BgPanel extends JPanel {

    private final Image  bg;
    private final Color  overlay;   // null → kein Overlay

    /** @param imgPath  Pfad z. B. "/images/6.jpeg"
        @param tint     z. B. new Color(255, 180, 180, 120)  (letzter Wert = Alpha) */
    public BgPanel(String imgPath, Color tint) {
        URL url = BgPanel.class.getResource(imgPath);
        if (url == null) throw new IllegalArgumentException("Bild nicht gefunden: " + imgPath);
        bg       = new ImageIcon(url).getImage();
        overlay  = tint;
        setOpaque(false);
    }

    /* Convenience‑Ctor ohne Overlay */
    public BgPanel(String imgPath) { this(imgPath, null); }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Hintergrundbild proportional einpassen
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

        // Overlay zeichnen, falls vorhanden
        if (overlay != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(overlay);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}
