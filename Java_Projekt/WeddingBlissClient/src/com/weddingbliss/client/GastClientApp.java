package com.weddingbliss.client;

import com.fasterxml.jackson.databind.ObjectMapper;   // Jackson-Mapper zum (De)Serialisieren von JSON-Daten
import com.weddingbliss.client.model.Gast;           // Domänenklasse „Gast“ für Name, Menüwahl u. Ä.
import com.weddingbliss.client.ui.BgPanel;           // Eigenes JPanel mit Hintergrundbild-Funktion
import okhttp3.*;                                    // OkHttp – HTTP-Client für REST-Aufrufe (Request, Response …)
import javax.swing.*;                                // Swing-GUI-Basis (JFrame, JButton, JLabel, …)
import java.awt.*;                                   // Grundlegende AWT-Klassen (Graphics, Color, Insets …)
import java.io.IOException;                          // Exception-Klasse für I/O-Fehler (z. B. HTTP, Datei)
import java.net.URL;                                 // Repräsentiert eine Internet- oder Ressourcen-Adresse

/**
 * GastClientApp ist die Hauptklasse für die Desktop-Anwendung zur Hochzeitseinladung.
 * Sie führt den Benutzer durch mehrere Panels (Cards) mit CardLayout.
 */
public class GastClientApp {

    /* ---------- Backend & JSON ---------- */
    private static final String        BASE_URL = "http://localhost:8080/api/gaeste";
    private static final OkHttpClient  client   = new OkHttpClient();
    private static final ObjectMapper  mapper   = new ObjectMapper();

    /* ---------- gemeinsam genutzte GUI-Felder ---------- */
    private static JTextField  vorF, nachF, tfAllergie;
    private static JRadioButton ja, rbFleisch, rbFisch, rbVegan, rbVegetarisch;
    private static JButton btnWeiter, btnBest, btnWeiterKommen, btnSenden,
                           btnCloseThanks, btnClose, btnUberUns, btnZurueck;

    public static void main(String[] args) {

        /* ---------- Farben & Overlays ---------- */
        Color hauptOrange = new Color(0xFFA764);
        Color roseOverlay = new Color(0x80FF8F8F, true);
        Color darkOverlay = new Color(0x80000000, true);
        Color brown       = new Color(0x4D2715);
        Color white       = new Color(0xffffff);

        /* ---------- MonteCarlo-Font laden ---------- */
        Font monte = loadMonteCarloFont();

        /* ---------- Karten-Container ---------- */
        // 1) Erzeuge ein CardLayout-Objekt.
        CardLayout cl = new CardLayout();
        // 2) Lege ein JPanel an, das dieses CardLayout benutzt.
        JPanel cards = new JPanel(cl);

        /* ---------- Karten erzeugen & registrieren ---------- */
        JPanel start   = createStartPanel(monte, darkOverlay);
        JPanel frage   = createFragePanel(darkOverlay);
        JPanel kommen  = createKommenPanel(monte, brown);
        JPanel essen   = createEssenPanel(darkOverlay);
        JPanel freuen  = createFreuenPanel(monte, darkOverlay);
        JPanel absage  = createAbsagePanel(darkOverlay);
        JPanel uberUns = createUberUnsPanel(white, monte);

        cards.add(start,   "start");
        cards.add(frage,   "frage");
        cards.add(kommen,  "kommen");
        cards.add(essen,   "essen");
        cards.add(freuen,  "freuen");
        cards.add(absage,  "absage");
        cards.add(uberUns, "uberuns");

        /* ---------- Navigation verdrahten ---------- */
        wireListeners(cl, cards);

        /* ---------- Hauptfenster ---------- */
        JFrame f = new JFrame("Hochzeitseinladung");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(cards);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); // Vollbildmodus
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /* =======================================================
       1) Start-Panel – unveränderter Originalcode
       ======================================================= */
    private static JPanel createStartPanel(Font monte, Color darkOverlay) {

        /*  Das Start-Panel ist ein eigenes BgPanel (= JPanel mit Hintergrundbild
            + optionalem Overlay). Hier legen wir das Titelbild und alle
            Einleitungselemente ab, die der Gast als erstes sieht.               */
        JPanel start = new BgPanel("/images/6.jpeg", darkOverlay); // Bild + halbtransparentes Overlay
        start.setLayout(new GridBagLayout());                      // flexibles Raster → alles linksbündig

        //1.1  Navigation-Button „Über Wedding Bliss“ (rechts oben)
        btnUberUns = new JButton("Über 'Wedding Bliss'");  // öffnet später das Über-uns-Panel

        //1.2  Überschrift und Einleitungstexte
        JLabel headline = new JLabel("Feier mit uns!", SwingConstants.LEFT); // große Call-to-Action
        headline.setFont(monte.deriveFont(Font.ITALIC, 200f));       // MonteCarlo-Schrift, 200 pt, kursiv
        headline.setForeground(Color.WHITE);                         // heller Text auf dunklem Bild

        // drei Unterzeilen mit Informationen
        JLabel text1 = new JLabel(
                "Wir laden Dich herzlich zu unserer Hochzeit ein!", SwingConstants.LEFT);
        JLabel text2 = new JLabel(
                "Beantworte ein paar Fragen damit unsere Planung einwandfrei funktionieren kann",
                SwingConstants.LEFT);
        JLabel text3 = new JLabel(
                "Trage bitte deinen Vor- und Nachnamen ein:", SwingConstants.LEFT);

        // alle drei Labels weiß einfärben
        for (JLabel l : new JLabel[]{text1, text2, text3})
            l.setForeground(Color.WHITE);

        // individuelle Schriftgrößen (absteigend von Info- zu Hilfetext)
        text1.setFont(text1.getFont().deriveFont(Font.PLAIN, 46f));
        text2.setFont(text2.getFont().deriveFont(Font.PLAIN, 18f));
        text3.setFont(text3.getFont().deriveFont(Font.PLAIN, 24f));

        //1.3  Eingabezeile für Namen + Weiter-Button
        vorF       = new JTextField(12);  // Vorname
        nachF      = new JTextField(12);  // Nachname
        btnWeiter  = new JButton("Weiter");

        JPanel nameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameRow.setOpaque(false);
        nameRow.add(vorF); nameRow.add(nachF); nameRow.add(btnWeiter);

        //1.4  GridBagConstraints – Grundvorgabe für linke Spalte
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 30, 8, 8);
        gbc.gridx   = 0;
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        //1.5  Separates Constraint für Button rechts oben
        GridBagConstraints gbcTopRight = new GridBagConstraints();
        gbcTopRight.gridx  = 1;
        gbcTopRight.gridy  = 0;
        gbcTopRight.anchor = GridBagConstraints.NORTHEAST;
        gbcTopRight.insets = new Insets(20, 8, 8, 30);
        start.add(btnUberUns, gbcTopRight);

        //1.6  Elemente der linken Spalte in aufsteigender Reihenfolge
        gbc.gridy = 1; start.add(headline, gbc);
        gbc.gridy = 2; start.add(text1,   gbc);
        gbc.gridy = 3; start.add(text2,   gbc);
        gbc.gridy = 4; start.add(text3,   gbc);
        gbc.gridy = 5; start.add(nameRow, gbc);

        return start;
    }

    /* =======================================================
       2) Frage-Panel – unveränderter Originalcode
       ======================================================= */
    private static JPanel createFragePanel(Color darkOverlay) {

        /*  Panel mit demselben Hintergrundbild + Overlay wie das Start-Panel.
            Hier bestätigt der Gast, ob er überhaupt teilnehmen kann.          */
        JPanel frage = new BgPanel("/images/6.jpeg", darkOverlay);
        frage.setLayout(new GridBagLayout());    // Raster, alles zentriert

        //2.1  Überschrift / Frage
        JLabel frage1 = new JLabel(
                "Wirst du an unserem besonderen Tag dabei sein?",
                SwingConstants.CENTER);
        frage1.setFont(frage1.getFont().deriveFont(Font.PLAIN, 36f));
        frage1.setForeground(Color.WHITE);

        //2.2  Ja-/Nein-Radiobuttons
        ja           = new JRadioButton("Ja");
        JRadioButton nein = new JRadioButton("Nein");

        for (JRadioButton rb : new JRadioButton[]{ja, nein}) {
            rb.setForeground(Color.WHITE);
            rb.setOpaque(false);
        }
        ButtonGroup grp = new ButtonGroup(); grp.add(ja); grp.add(nein);

        //2.3  Bestätigungs-Button
        btnBest = new JButton("Bestätigen");

        //2.4  Zeile für die beiden Radiobuttons
        JPanel jaNeinRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        jaNeinRow.setOpaque(false);
        jaNeinRow.add(ja); jaNeinRow.add(nein);

        //2.5  Platzieren im GridBagLayout
        GridBagConstraints gbcFrage = new GridBagConstraints();
        gbcFrage.insets = new Insets(8, 8, 8, 8);
        gbcFrage.gridx  = 0; gbcFrage.anchor = GridBagConstraints.CENTER;

        gbcFrage.gridy = 0; frage.add(frage1,   gbcFrage);
        gbcFrage.gridy = 1; frage.add(jaNeinRow,gbcFrage);
        gbcFrage.gridy = 2; frage.add(btnBest,  gbcFrage);

        return frage;
    }

    /* =======================================================
       3) Kommen-Panel
       ======================================================= */
    private static JPanel createKommenPanel(Font monte, Color brown) {

        /*  Braun eingefärbtes Panel mit GridBagLayout – alles wird
            zentriert in einer Spalte untereinander platziert.          */
        JPanel kommen = new JPanel(new GridBagLayout());
        kommen.setBackground(brown);

        GridBagConstraints gbckommen = new GridBagConstraints();
        gbckommen.gridx   = 0;
        gbckommen.insets  = new Insets(20, 20, 20, 20);
        gbckommen.anchor  = GridBagConstraints.CENTER;

        //3.1  Überschrift
        gbckommen.gridy  = 0;
        JLabel lblTitle = new JLabel("Wir freuen uns auf dein Kommen!",
                                     SwingConstants.CENTER);
        lblTitle.setFont(monte.deriveFont(Font.ITALIC, 100f));
        lblTitle.setForeground(Color.WHITE);
        kommen.add(lblTitle, gbckommen);

        //3.2  Untertitel
        gbckommen.gridy  = 1;
        JLabel lblUntertitle = new JLabel(
                "Nun wollen wir wissen, was deine Esspräferenzen sind",
                SwingConstants.CENTER);
        lblUntertitle.setFont(lblUntertitle.getFont().deriveFont(Font.PLAIN, 24f));
        lblUntertitle.setForeground(Color.WHITE);
        kommen.add(lblUntertitle, gbckommen);

        //3.3  Drei Vorschaubilder
        gbckommen.gridy  = 2;
        JPanel imageRow = new JPanel(new GridLayout(1, 3, 10, 0));
        imageRow.setOpaque(false);

        int newW = 300, newH = 400;
        String[] paths = {"/images/3.jpeg", "/images/1.jpeg", "/images/5.jpeg"};
        for (String path : paths) {
            ImageIcon orig = new ImageIcon(GastClientApp.class.getResource(path));
            Image img = orig.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            JLabel pic = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            pic.setPreferredSize(new Dimension(newW, newH));
            imageRow.add(pic);
        }
        kommen.add(imageRow, gbckommen);

        //3.4  Weiter-Button
        gbckommen.gridy  = 3;
        btnWeiterKommen  = new JButton("Weiter");
        kommen.add(btnWeiterKommen, gbckommen);

        return kommen;
    }

    /* =======================================================
       4) Essen-Panel
       ======================================================= */
    private static JPanel createEssenPanel(Color darkOverlay) {

        JPanel essen = new BgPanel("/images/7.jpeg", darkOverlay);
        essen.setLayout(new GridBagLayout());

        GridBagConstraints gE = new GridBagConstraints();
        gE.insets  = new Insets(12, 25, 12, 25);
        gE.anchor  = GridBagConstraints.WEST;

        //4.2  Überschrift
        gE.gridx = 0; gE.gridy = 0; gE.gridwidth = 2; gE.anchor = GridBagConstraints.CENTER;
        JLabel frage2 = new JLabel("Deine Essenspräferenz", SwingConstants.CENTER);
        frage2.setFont(frage2.getFont().deriveFont(Font.BOLD, 46f));
        frage2.setForeground(Color.WHITE);
        essen.add(frage2, gE);

        // zurück zu linksbündig
        gE.gridwidth = 1; gE.anchor = GridBagConstraints.WEST;

        //4.3  Linke Spalte
        gE.gridx = 0; gE.gridy = 1;
        JLabel lblEss = new JLabel("Essenswunsch:");
        lblEss.setFont(frage2.getFont().deriveFont(Font.PLAIN, 24f));
        lblEss.setForeground(Color.WHITE);
        essen.add(lblEss, gE);

        gE.gridy = 2;
        rbFleisch     = new JRadioButton("Fleisch");
        rbFisch		  = new JRadioButton("Fisch");
        rbVegan       = new JRadioButton("Vegan");
        rbVegetarisch = new JRadioButton("Vegetarisch");

        ButtonGroup grpGericht = new ButtonGroup();
        grpGericht.add(rbFleisch); grpGericht.add(rbFisch); grpGericht.add(rbVegan); grpGericht.add(rbVegetarisch);

        for (JRadioButton rb : new JRadioButton[]{rbFleisch, rbFisch, rbVegan, rbVegetarisch}) {
            rb.setForeground(Color.WHITE); rb.setOpaque(false);
        }

        essen.add(rbFleisch, gE);
        gE.gridy = 3; essen.add(rbFisch, gE);
        gE.gridy = 4; essen.add(rbVegan, gE);
        gE.gridy = 5; essen.add(rbVegetarisch, gE);

        //4.4  Rechte Spalte – Allergien
        gE.gridx = 1; gE.gridy = 1;
        JLabel lblAll = new JLabel("Allergien:");
        lblAll.setFont(frage2.getFont().deriveFont(Font.PLAIN, 24f));
        lblAll.setForeground(Color.WHITE);
        essen.add(lblAll, gE);

        gE.gridy = 2; tfAllergie = new JTextField(15);
        essen.add(tfAllergie, gE);

        //4.5  Senden-Button
        gE.gridx = 1; gE.gridy = 5; gE.anchor = GridBagConstraints.EAST;
        btnSenden = new JButton("Senden");
        essen.add(btnSenden, gE);

        return essen;
    }

    /* =======================================================
       5) Danke-Panel
       ======================================================= */
    private static JPanel createFreuenPanel(Font monte, Color darkOverlay) {

        JPanel freuen = new BgPanel("/images/4.jpeg", darkOverlay);
        freuen.setLayout(new GridBagLayout());

        GridBagConstraints gbcDanke = new GridBagConstraints();
        gbcDanke.insets = new Insets(8, 30, 8, 8);
        gbcDanke.gridx  = 0; gbcDanke.gridy = 0; gbcDanke.anchor = GridBagConstraints.CENTER;

        JLabel lblDanke = new JLabel("Wir freuen uns auf Dich", SwingConstants.CENTER);
        lblDanke.setFont(monte.deriveFont(Font.ITALIC, 100f)); // kleiner, damit es in die Fläche passt
        lblDanke.setPreferredSize(new Dimension(1000, 160)); // breite genug für den Text


        lblDanke.setForeground(Color.WHITE);
        freuen.add(lblDanke, gbcDanke);

        // Button
        GridBagConstraints gbcClose = new GridBagConstraints();
        gbcClose.insets = new Insets(8, 30, 8, 8);
        gbcClose.gridx  = 0; gbcClose.gridy = 1; gbcClose.anchor = GridBagConstraints.CENTER;

        btnCloseThanks = new JButton("Schließen");
        freuen.add(btnCloseThanks, gbcClose);

        return freuen;
    }

    /* =======================================================
       6) Absage-Panel
       ======================================================= */
    private static JPanel createAbsagePanel(Color darkOverlay) {

        JPanel absage = new BgPanel("/images/2.jpeg", darkOverlay);
        absage.setLayout(new GridBagLayout());

        GridBagConstraints gbcAbsage = new GridBagConstraints();
        gbcAbsage.gridx = 0; gbcAbsage.insets = new Insets(8, 30, 8, 30);
        gbcAbsage.anchor = GridBagConstraints.CENTER;

        // Label 1
        gbcAbsage.gridy = 0;
        JLabel lblAbsage1 = new JLabel("Danke für deine Absage.", SwingConstants.CENTER);
        lblAbsage1.setFont(lblAbsage1.getFont().deriveFont(Font.PLAIN, 36f));
        lblAbsage1.setForeground(Color.WHITE);
        absage.add(lblAbsage1, gbcAbsage);

        // Label 2
        gbcAbsage.gridy = 1;
        JLabel lblAbsage2 = new JLabel("Schade, dass du nicht dabei sein kannst",
                                        SwingConstants.CENTER);
        lblAbsage2.setFont(lblAbsage2.getFont().deriveFont(Font.PLAIN, 36f));
        lblAbsage2.setForeground(Color.WHITE);
        absage.add(lblAbsage2, gbcAbsage);

        // Button
        gbcAbsage.gridy = 2;
        btnClose = new JButton("Schließen");
        absage.add(btnClose, gbcAbsage);

        return absage;
    }

    /* =======================================================
       7) Über-uns-Panel
       ======================================================= */
    private static JPanel createUberUnsPanel(Color white, Font monte) {

        JPanel uberUns = new JPanel(new BorderLayout());
        uberUns.setBackground(white);

        // Top-Bar mit Zurück
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        topBar.setOpaque(false);
        btnZurueck = new JButton("Zurück");
        topBar.add(btnZurueck);
        uberUns.add(topBar, BorderLayout.NORTH);

        // Center
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        /* 2a) Logo ------------------------------------------------------- */
        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.gridx  = 0; gbcLogo.gridy = 0;
        gbcLogo.insets = new Insets(10, 0, 20, 0); gbcLogo.anchor = GridBagConstraints.CENTER;

        URL logoUrl = GastClientApp.class.getClassLoader().getResource("logo.jpeg");
        if (logoUrl != null) {
            ImageIcon original = new ImageIcon(logoUrl);
            Image scaled = original.getImage().getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            center.add(new JLabel(new ImageIcon(scaled)), gbcLogo);
        } else {
            System.err.println("⚠️ Logo nicht gefunden: logo.jpeg");
        }

        /* 2b) Titel ------------------------------------------------------ */
        GridBagConstraints gbcTitel = new GridBagConstraints();
        gbcTitel.gridx = 0; gbcTitel.gridy = 1;
        gbcTitel.insets = new Insets(0, 0, 25, 0); gbcTitel.anchor = GridBagConstraints.CENTER;
        JLabel lblTitel = new JLabel("Wedding Bliss", SwingConstants.CENTER);
        lblTitel.setFont(monte.deriveFont(Font.BOLD, 36f));
        center.add(lblTitel, gbcTitel);

        /* 2c) Firmenporträt (HTML) – unverändert ------------------------ */
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

        GridBagConstraints gbcText = new GridBagConstraints();
        gbcText.gridx = 0; gbcText.gridy = 2;
        gbcText.insets = new Insets(10, 0, 25, 0); gbcText.anchor = GridBagConstraints.CENTER;
        center.add(new JLabel(htmlText, SwingConstants.CENTER), gbcText);

        /* 2d) Kontaktinfo ------------------------------------------------ */
        String contactHtml =
            "<html><div style='text-align:center;'>"
          + "<strong>Kontakt</strong><br>"
          + "Wedding&nbsp;Bliss&nbsp;GmbH&nbsp;&nbsp;|&nbsp;&nbsp;Musterstraße&nbsp;123&nbsp;&nbsp;|&nbsp;&nbsp;40210&nbsp;Düsseldorf<br>"
          + "Tel.&nbsp;+49&nbsp;211&nbsp;1234567&nbsp;&nbsp;|&nbsp;&nbsp;"
          + "E-Mail:&nbsp;<a href='mailto:info@weddingbliss.de'>info@weddingbliss.de</a>&nbsp;&nbsp;|&nbsp;&nbsp;"
          + "<a href='https://www.weddingbliss.de'>www.weddingbliss.de</a>"
          + "</div></html>";

        GridBagConstraints gbcKontakt = new GridBagConstraints();
        gbcKontakt.gridx = 0; gbcKontakt.gridy = 3;
        gbcKontakt.insets = new Insets(120, 0, 35, 0);
        gbcKontakt.anchor = GridBagConstraints.CENTER;
        center.add(new JLabel(contactHtml, SwingConstants.CENTER), gbcKontakt);

        // Füller
        GridBagConstraints gbcFill = new GridBagConstraints();
        gbcFill.gridx = 0; gbcFill.gridy = 4;
        gbcFill.weighty = 1.0;
        center.add(Box.createVerticalGlue(), gbcFill);

        uberUns.add(center, BorderLayout.CENTER);
        return uberUns;
    }

    /* ---------- Listener anschließen (Navigation) ----------------------- */
    private static void wireListeners(CardLayout cl, JPanel cards) {

        btnUberUns.addActionListener(e -> cl.show(cards, "uberuns"));
        btnZurueck.addActionListener(e -> cl.show(cards, "start"));
        btnWeiter.addActionListener(e -> cl.show(cards, "frage"));

        btnBest.addActionListener(e -> {
            if (ja.isSelected()) cl.show(cards, "kommen");
            else                 cl.show(cards, "absage");
        });

        btnClose.addActionListener(e -> System.exit(0));
        btnWeiterKommen.addActionListener(e -> cl.show(cards, "essen"));

        btnSenden.addActionListener(e -> {
            String gericht =
                rbFleisch.isSelected()     ? "Fleisch" :
                rbVegan.isSelected()       ? "Vegan"   :
                                             "Vegetarisch";

            Gast g = new Gast(
                    vorF.getText(), nachF.getText(), true,
                    gericht, tfAllergie.getText());

            sendeGast(g); cl.show(cards, "freuen");
        });

        btnCloseThanks.addActionListener(e -> System.exit(0));
    }

    /* ---------- Schrift laden ------------------------------------------ */
    private static Font loadMonteCarloFont() {
        try (var is = GastClientApp.class.getResourceAsStream("/fonts/MonteCarlo-Regular.ttf")) {
            Font f = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Font("Serif", Font.ITALIC, 40);
        }
    }

    /* ---------- Backend-Call ------------------------------------------- */
    private static void sendeGast(Gast g) {
        try {
            RequestBody body = RequestBody.create(
                    mapper.writeValueAsString(g),
                    MediaType.parse("application/json"));
            Request req = new Request.Builder().url(BASE_URL).post(body).build();
            client.newCall(req).execute().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
