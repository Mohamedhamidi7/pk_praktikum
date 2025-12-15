package lkarten;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Menu {
	Lernkartei lernkartei = new Lernkartei();
	
	Scanner scn = new Scanner(System.in);
	public void start() {
		while(true) {
			System.out.println(
			        "Lernkarten-App\n"
			        + " 1. Lernen!\n"
			        + " 2. Einzelantwortkarte hinzufügen\n"
			        + " 3. Drucke alle Karten\n"
			        + " 4. Drucke Karten zu Kategorie\n"
			        + " 5. CSV-Export\n"
			        + " 6. Beenden\n"
			        + " Bitte Aktion wählen:"
			);

			int wahl ;
			try {
				wahl = scn.nextInt();
				scn.nextLine();
			}catch(InputMismatchException e) {
				System.err.println("Bitte wählen Sie eine Nummer aus der Liste.");
				scn.nextLine();
				continue;
			}
			switch(wahl) {
				case 1:
					Lernkarte[] deck = lernkartei.erzeugeDeck(5);
					for(Lernkarte e :  deck) {
						e.zeigeVorderseite();
						System.out.println("<Drücken Sie Enter, um die Rückseite der Karte zu sehen.>");
						try{
							System.in.read(new byte[2]);
						} catch(IOException ex) {
							System.err.println("Fehler: " + ex.getMessage());
						}
						e.zeigeRueckseite();
					}
					
					break;
				case 2:
					String kategorie = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie ein: ");
					String titel = JOptionPane.showInputDialog(null, "Bitte geben Sie eine titel ein: ");
					String frage = JOptionPane.showInputDialog(null, "Bitte geben Sie eine frage ein: ");
					String antwort = JOptionPane.showInputDialog(null, "Bitte geben Sie eine abtwort ein: ");
					EinzelantwortKarte neueKarte = new EinzelantwortKarte(kategorie,titel,frage,antwort);
					lernkartei.hinzufuegen(neueKarte);
					break;
				case 3:
					lernkartei.druckeAlleKarten();
					break;
				case 4:
					String kategorieEingabe = JOptionPane.showInputDialog(null, "Bitte geben Sie eine Kategorie ein: ");
					for(Lernkarte e : lernkartei.gibKartenZuKategorie(kategorieEingabe)) if(e!=null) e.druckeKarte();
					break;
				case 5:
				    while (true) {
				        String dateiname = JOptionPane.showInputDialog(null, "Dateiname für CSV-Export:");

				        if (dateiname == null) { // cancel
				            break;
				        }

				        if (dateiname.isBlank()) {
				            JOptionPane.showMessageDialog(null, "Dateiname darf nicht leer sein.");
				            continue;
				        }

				        Path pfad = Path.of(dateiname);

				        if (Files.exists(pfad)) {
				            int res = JOptionPane.showConfirmDialog(
				                    null,
				                    "Datei existiert bereits. Überschreiben?",
				                    "Bestätigung",
				                    JOptionPane.YES_NO_OPTION
				            );
				            if (res != JOptionPane.YES_OPTION) {
				                continue;
				            }
				        }

				        try {
				            lernkartei.exportiereEintraegeAlsCsv(pfad);
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Fehler beim Export: " + ex.getMessage());
				        }
				        break;
				    }
				    break;
				case 6:
				    return;
				default:
					System.err.println("Bitte wählen eins von 1 bis 6");
					continue;
			}
			
				
			
			
		}
	}
}
