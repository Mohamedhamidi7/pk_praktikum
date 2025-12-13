package lkarten;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Lernkartei {
	LinkedList<Lernkarte> lernkarten = new LinkedList<>();
	

	
	public void hinzufuegen(Lernkarte karte) {
		lernkarten.add(karte);
	}
	
	public void druckeAlleKarten() {
		Iterator<Lernkarte> it = lernkarten.iterator();
		while(it.hasNext()) {
			it.next().druckeKarte();
		}
//		for(Lernkarte e: lernkarten) {
//			e.druckeKarte();
//		}
	}
	
	public int gibAnzahlKarten() {
		return lernkarten.size();
	}
	
	public Lernkarte[] gibKartenZuKategorie(String kategorie) {
		Lernkarte[] result = new Lernkarte[gibAnzahlKarten()];
		int anzahlResult = 0;
		for(Lernkarte e: lernkarten) {
			if(e.getKategorie().equals(kategorie)) result[anzahlResult++]=e ;
		}
		return result;
	}
	
		public Lernkarte[] erzeugeDeck(int anzahlKarten) {
		    if (lernkarten.isEmpty()) {
		        throw new IllegalStateException("Keine Lernkarten vorhanden!");
		    }

		    Lernkarte[] result = new Lernkarte[anzahlKarten];
		    Random rnd = new Random();

		    for (int i = 0; i < result.length; i++) {
		        result[i] = lernkarten.get(rnd.nextInt(lernkarten.size()));
		    }
		    return result;

	}

}
