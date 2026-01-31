package com.weddingbliss.client;

import com.fasterxml.jackson.databind.ObjectMapper;   // Jackson-Mapper zum (De)Serialisieren von JSON-Daten
import com.weddingbliss.client.model.Gast;           // Domänenklasse „Gast“ für Name, Menüwahl u. Ä.
import com.weddingbliss.client.ui.BgPanel;           // Eigenes JPanel mit Hintergrundbild-Funktion
import okhttp3.*;                                    // OkHttp – HTTP-Client für REST-Aufrufe (Request, Response …)
import javax.swing.*;                                // Swing-GUI-Basis (JFrame, JButton, JLabel, …)
import java.awt.*;                                   // Grundlegende AWT-Klassen (Graphics, Color, Insets …)
import java.io.IOException;                          // Exception-Klasse für I/O-Fehler (z. B. HTTP, Datei)
import java.net.URL;  


/**
 * Die zentrale Anwendungsklasse für das Wedding Bliss Gästeportal.
 * Diese Klasse verwaltet die Navigation zwischen verschiedenen Panels der GUI,
 * verarbeitet Benutzereingaben und kommuniziert mit dem Backend über HTTP.
 */
public class GastClientApp2 extends JFrame {
	
	/* ---------- Backend & JSON ---------- */

    /** Basis-URL des REST-Endpunkts für Gäste. */
    private static final String        BASE_URL = "http://localhost:8080/api/gaeste";
    /** HTTP-Client zur Kommunikation mit dem Server. */
    private static final OkHttpClient  client   = new OkHttpClient();
    /** JSON-Mapper für die Serialisierung von Java-Objekten. */
    private static final ObjectMapper mapper = new ObjectMapper();

    /** CardLayout zur Umschaltung zwischen Panels. */
    private final CardLayout cardLayout = new CardLayout();

    /** Container für die wechselbaren Panels. */
    private final JPanel cards = new JPanel(cardLayout);
    /** Texteingabefeld für den Vornamen. */
    public final JTextField vorF = new JTextField(20);
    /** Texteingabefeld für den Nachnamen. */
    public final JTextField nachF = new JTextField(20);
    /** Texteingabefeld für Allergiehinweise. */
    public final JTextField tfAllergie = new JTextField(20);

    /**
     * Konstruktor – Initialisiert Fenster, Panels, Layouts und GUI-Komponenten.
     */
    public GastClientApp2() {
        super("Wedding Bliss – Gästeportal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);

        Font monte = loadMonteCarloFont();
        Color hauptOrange = new Color(0xFFA764);
        Color roseOverlay = new Color(0x80FF8F8F, true);
        Color darkOverlay = new Color(0x80000000, true);
        Color brown       = new Color(0x4D2715);
        Color white = Color.WHITE;

        // Panels initialisieren und hinzufügen
        StartPanel startPanel = new StartPanel(this, monte, darkOverlay);
        FragePanel fragePanel = new FragePanel(this, darkOverlay, monte);
        TeilnahmePanel teilnahmePanel = new TeilnahmePanel(this, monte, brown);
        AbsagePanel absagePanel = new AbsagePanel(this, darkOverlay, monte);
        EssenPanel essenPanel = new EssenPanel(this, darkOverlay, monte);
        FreuenPanel freuenPanel = new FreuenPanel(this, monte, darkOverlay);
        UberunsPanel ueberUnsPanel = new UberunsPanel(this, white, monte);

        cards.add(startPanel, "start");
        cards.add(fragePanel, "frage");
        cards.add(teilnahmePanel, "kommen");
        cards.add(absagePanel, "absage");
        cards.add(essenPanel, "essen");
        cards.add(freuenPanel, "freuen");
        cards.add(ueberUnsPanel, "uberuns");

        setContentPane(cards);
        cardLayout.show(cards, "start");

        setVisible(true);
    }

    /**
     * Wechselt zum angegebenen Panel anhand seines Namens.
     *
     * @param name Der Name des Panels (z. B. "start", "essen", "freuen").
     */
    public void showCard(String name) {
        cardLayout.show(cards, name);
    }

    /**
     * Sendet einen Gast als JSON-Objekt an das Backend.
     *
     * @param g Der Gast, der übermittelt werden soll.
     */
    public void sendeGast(Gast g) {
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

    /**
     * Einstiegspunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (nicht verwendet).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GastClientApp2::new);
    }

    /**
     * Lädt die benutzerdefinierte MonteCarlo-Schriftart.
     *
     * @return Eine {@link Font}-Instanz der geladenen Schriftart oder ein Fallback-Font.
     */
    private static Font loadMonteCarloFont() {
        try (var is = GastClientApp2.class.getResourceAsStream("/fonts/MonteCarlo-Regular.ttf")) {
            Font f = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 40f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
            return f;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Font("Serif", Font.ITALIC, 40);
        }
    }

    /**
     * Prüft, ob ein eingegebener Name gültig ist (mind. 2 Zeichen, Buchstaben, Umlaute, Bindestrich oder Leerzeichen).
     *
     * @param name Der Name, der geprüft werden soll.
     * @return {@code true}, wenn der Name gültig ist, sonst {@code false}.
     */
    public static boolean istGueltigerName(String name) {
        return name.matches("^[A-Za-zÄÖÜäöüß\\- ]{2,}$");
    }
}
