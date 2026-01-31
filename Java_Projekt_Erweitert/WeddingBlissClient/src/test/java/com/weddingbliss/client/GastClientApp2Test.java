package com.weddingbliss.client;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.*;

import com.weddingbliss.client.GastClientApp2;

import static org.junit.Assert.assertEquals;

import javax.swing.*;

/**
 * GUI-Testklasse für die Anwendung GastClientApp2.
 * Verwendet AssertJ Swing, um Interaktionen mit der Benutzeroberfläche zu testen.
 */
public class GastClientApp2Test {

    private FrameFixture window;

    /**
     * Initialisiert den Thread-Check von AssertJ Swing.
     * Stellt sicher, dass Swing-Komponenten nur im EDT (Event Dispatch Thread) verändert werden.
     */
    @BeforeClass
    public static void setUpOnce() {
        org.assertj.swing.edt.FailOnThreadViolationRepaintManager.install();
    }

    /**
     * Startet die Anwendung vor jedem Test und zeigt das Hauptfenster.
     */
    @Before
    public void setUp() {
        GastClientApp2 app = GuiActionRunner.execute(GastClientApp2::new);
        app.setName("mainFrame");
        window = new FrameFixture(app);
        window.show(); // Fenster sichtbar machen
    }

    /**
     * Schließt das Fenster nach jedem Test und bereinigt Ressourcen.
     */
    @After
    public void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }
    
    // Hilfsmethoden zur Navigation zwischen Panels

    /**
     * Navigiert vom StartPanel zum FragePanel (mit gültiger Namenseingabe).
     */
    private void navigateToFragePanel() {
        window.textBox("vornameFeld").enterText("Anna");
        window.textBox("nachnameFeld").enterText("Test");
        window.button("weiterButton").click();
    }

    /**
     * Navigiert vom FragePanel zum TeilnahmePanel (mit Auswahl "Ja").
     */
    private void navigateToKommenPanel() {
        navigateToFragePanel();
        window.radioButton("radioJa").click();
        window.button("frageWeiterButton").click();
    }

    /**
     * Navigiert vom TeilnahmePanel zum EssenPanel.
     */
    private void navigateToEssenPanel() {
        navigateToKommenPanel();
        window.button("essenWeiterButton").click();
    }
    
    // Einzelne Tests

    /**
     * Testet, ob bei gültiger Eingabe vom StartPanel zum FragePanel navigiert wird.
     */
    @Test
    @GUITest
    public void testStartPanel_validInput_navigatesToFragePanel() {
        window.textBox("vornameFeld").enterText("Anna");
        window.textBox("nachnameFeld").enterText("Test");
        window.button("weiterButton").click();

        window.label("frageLabel").requireVisible();
    }

    /**
     * Testet, ob bei fehlender Eingabe ein Fehlerdialog angezeigt wird.
     */
    @Test
    @GUITest
    public void testStartPanel_missingInput_showsErrorDialog() {
        window.textBox("vornameFeld").enterText("");
        window.textBox("nachnameFeld").enterText("Test");
        window.button("weiterButton").click();

        window.dialog().requireVisible();
        assertEquals("Eingabefehler", window.dialog().target().getTitle());
    }

    /**
     * Testet, ob das Über-uns-Panel korrekt geöffnet wird.
     */
    @Test
    @GUITest
    public void testnavigatesToUeberUns() {
        window.button("ueberUnsButton").click();
        window.label("ueberUnsLabel").requireVisible();
    }

    /**
     * Testet, ob bei Auswahl "Ja" zum TeilnahmePanel gewechselt wird.
     */
    @Test
    @GUITest
    public void testFragePanel_selectionJa_navigatesToKommen() {
        navigateToFragePanel();

        window.radioButton("radioJa").click();
        window.button("frageWeiterButton").click();

        window.label("kommenLabel").requireVisible();
    }

    /**
     * Testet, ob bei Auswahl "Nein" zum AbsagePanel gewechselt wird.
     */
    @Test
    @GUITest
    public void testFragePanel_selectionNein_navigatesToAbsage() {
        navigateToFragePanel();

        window.radioButton("radioNein").click();
        window.button("frageWeiterButton").click();

        window.label("absageLabel").requireVisible();
    }
    
    /**
     * Testet, ob bei Klick auf „Weiter“ zum EssenPanel gewechselt wird.
     */
    @Test
    @GUITest
    public void testTeilnahmePanel_navigatesToEssenPanel() {
        navigateToKommenPanel();

        window.button("essenWeiterButton").click();
        window.label("essenLabel").requireVisible();
    }

    /**
     * Testet, ob ein Menü gewählt, eine Allergie eingegeben und anschließend das FreuenPanel erreicht wird.
     */
    @Test
    @GUITest
    public void testEssenPanel_selectMenueAndProceed() {
        navigateToEssenPanel();

        window.radioButton("radioVegetarisch").click();
        window.textBox("allergieFeld").enterText("Nüsse");
        window.button("essenWeiterButton").click();

        window.label("freuenLabel").requireVisible();
    }
    
}