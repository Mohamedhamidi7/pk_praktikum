package lkarten;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
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
					+ " 5. Beenden\n"
					+ " Bitte Aktion wählen:"
			);
			
			switch(scn.nextInt()) {
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
					return;
				default:
					System.err.println("Unbekante taste!");
					continue;
			}
			
				
			
			
		}
	}
}
