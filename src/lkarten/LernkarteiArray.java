package lkarten;
import java.util.Random;
public class LernkarteiArray {
	Lernkarte[] lernkarten = new Lernkarte[100];
	private int anzahlKarten = 0;
	public void hinzufuegen(Lernkarte karte) {
		if(anzahlKarten == lernkarten.length) {
			System.err.println("Error: maximum " + lernkarten.length + " karte");
			return;
		}
		lernkarten[anzahlKarten++] = karte;
	}
	public void druckeAlleKarten(){
		for(int i=0;i<anzahlKarten;i++) {
			lernkarten[i].druckeKarte();
		}
	}
	public int gibAnzahlKarten() {
		return anzahlKarten;
	}
	public Lernkarte[] gibKartenZuKategorie(String kategorie) {
		Lernkarte[] result = new Lernkarte[anzahlKarten];
		int anzahlResult = 0;
//		for(int i=0 ; i<anzahlResult ; i++) {
//			if(lernkarten[i].getKategorie().equals(kategorie)) {
//				
//			}
//		}
		
		for(Lernkarte e : lernkarten) {
			
			if(e != null && e.getKategorie().equals(kategorie)) result[anzahlResult++] = e;
		}
		return result;
	}
	public Lernkarte[] erzeugeDeck(int anzahlKarten) {
		Lernkarte[] result = new Lernkarte[anzahlKarten];
		Random rnd = new Random();
		for(int i=0 ; i<result.length ; i++) {
			result[i] = lernkarten[rnd.nextInt(result.length - 1)];
		}
		return result;
	}
}
