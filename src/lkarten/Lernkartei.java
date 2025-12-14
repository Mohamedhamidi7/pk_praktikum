package lkarten;

import java.util.*;
import javax.swing.*;

public class Lernkartei {
	Set<Lernkarte> lernkarten = new HashSet<>();
	
	public void hinzufuegen(Lernkarte karte) {
		try{
			karte.validiere();
		} catch(UngueltigeKarteExeption e) {
			JOptionPane.showMessageDialog(null, "kein gültige Antwort angegeben");
		}
		lernkarten.add(karte);
	}
	
	public void druckeAlleKarten() {
		ArrayList<Lernkarte> lernkartenAsList = new ArrayList<>(lernkarten);
		Collections.sort(lernkartenAsList);
		
		Iterator<Lernkarte> it = lernkartenAsList.iterator();
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
		    LinkedList<Lernkarte> lernkartenAsList = new LinkedList<>(lernkarten);
		    Lernkarte[] result = new Lernkarte[anzahlKarten];
		    Random rnd = new Random();

		    for (int i = 0; i < result.length; i++) {
		        result[i] = lernkartenAsList.get(rnd.nextInt(lernkarten.size()));
		    }
		    return result;

	}

}
