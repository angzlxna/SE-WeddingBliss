# de.htw-dresden.informatik.se.wedingbliss

## Anwenderdokumentation für die Nutzer

Der Nutzer startet die Anwendung über einen Link, den er zuvor erhalten hat. Für die Nutzung ist eine Internetverbindung auf einem Laptop erforderlich; die Anwendung lässt sich ausschließlich auf einem Laptop öffnen.Nach dem Öffnen gelangt der Nutzer auf die Startseite, wo er seinen Vor- und Nachnamen eingibt und anschließend auf "Bestätigen" klickt. Daraufhin wird er zur nächsten Seite weitergeleitet.
Zusätzlich befindet sich auf der Startseite ein "Über uns"-Button. Klickt der Nutzer darauf, wird er auf eine separate Seite weitergeleitet, die Informationen über die Firma enthält. Auf dieser Seite gibt es einen "Zurück"-Button, mit dem der Nutzer wieder zur Startseite gelangen kann. Der "Über uns"-Button ist nur auf der Startseite sichtbar; in den weiteren Schritten der Anwendung ist er nicht mehr verfügbar.
Auf der nächsten Seite wird der Nutzer gefragt, ob er an der Hochzeit teilnimmt. Dies geschieht über eine Checkbox-Auswahl.
Falls der Nutzer absagt, wird ihm ein Abschlussscreen angezeigt. Mit einem Klick auf den Schließen-Button wird die Anwendung beendet.
Falls der Nutzer zusagt, erscheint ein Bildschirm mit einer Nachricht, dass sich die Verlobten auf sein Kommen freuen und gerne Informationen zu seinen Allergien und Essenspräferenzen hätten.
Mit einem Klick auf den "Weiter"-Button gelangt der Nutzer zu einer Seite, auf der er mittels Checkbox eine von drei Essensoptionen auswählen kann:

• Vegane Ernährung
• Vegetarische Ernährung
• Ernährung mit Fleisch

Zusätzlich steht ihm ein Textfeld zur Verfügung, in das er (optional) bekannte Allergien eintragen
kann.
Nach dem Bestätigen dieser Angaben wird ein abschließender Screen angezeigt. Mit einem Klick
auf den Schließen-Button wird die Anwendung beendet.
Alle eingegebenen Daten werden im Backend gespeichert. Die Verlobten erhalten dadurch eine
Übersicht über alle Zu- und Absagen sowie die jeweiligen Essenswünsche und eventuelle
Allergien der Gäste.

### Fehlerbehebung

• Anwendung lädt nicht → Internetverbindung prüfen, anderen Browser testen
• Link funktioniert nicht → Tippfehler prüfen oder Brautpaar kontaktieren
• Eingabe hängt → Browser neu starten, Link erneut öﬀnen

### Systemanforderungen

• Laptop oder PC mit Internetzugang
• Aktueller Webbrowser (Chrome, Firefox, Safari, Edge)
• Keine Installation notwendig

## Admindokumentation

### Systemvoraussetzungen

Software
• Java Development Kit (JDK): Version 17
• Java Runtime Environment (JRE): Für Laufzeitausführung
• Build-Tool: Maven oder Gradle
• IDE: Eclipse mit Spring Tools 4 oder IntelliJ IDEA mit Spring Boot Plugin
• API-Tools: Postman
• Versionsverwaltung: Git (z. B. via GitHub)

### Installation und Setup

Backend (Spring Boot)

1. Java 17 installieren
2. 3. Projekt klonen: „ git clone https://github.com/.../weddingbliss-backend.git“
      Projekt in Eclipse öffnen und WeddingBlissServerApplication.java ausführen
3. API erreichbar unter: http://localhost:8080/api/guests
   Frontend (Java Swing Client)
4. Projekt in Eclipse importieren:
   File > Import > Existing Projects into Workspace
5. Öffne GastClientApp.java
6. Ausführen über:
   Run > Run As > Java Application

### Konfiguration

Die Backend-Konfiguration erfolgt über die Datei application.properties, welche sich im
Ordner src/main/resources/ befindet.
Derzeit enthält die Datei nur eine grundlegende Einstellung zur Benennung der Anwendung:
spring.application.name=weddingbliss-backend
Diese Einstellung legt den Namen der Anwendung intern fest, z. B. für Logs und Monitoring
(Spring Boot Actuator), hat aber keinen funktionalen Einfluss auf die Port- oder
Datenbankkonfiguration.

### Benutzerverwaltung

-  Aktuell keine Benutzerregistrierung nötig.
-  API ist offen zugänglich (lokal).
-  Für den produktiven Einsatz:
   → Spring Security mit JWT oder Basic Auth einplanen
