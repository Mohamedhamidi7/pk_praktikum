package lkarten;

import java.util.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


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
		
		public void exportiereEintraegeAlsCsvNio(Path datei) throws java.io.IOException {
		    String header = "ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)";

		    ArrayList<Lernkarte> list = new ArrayList<>(lernkarten);
		    Collections.sort(list);

		    ArrayList<String> lines = new ArrayList<>();
		    lines.add(header);
		    for (Lernkarte k : list) {
		        lines.add(k.exportiereAlsCsv());
		    }

		    Files.write(datei, lines, StandardCharsets.UTF_8,
		            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}
		public void exportiereEintraegeAlsCsv(Path datei) throws java.io.IOException {
		    String header = "ID,Kategorie,Titel,Frage,Antwort(en),Richtige Antwort(en)";

		    ArrayList<Lernkarte> list = new ArrayList<>(lernkarten);
		    Collections.sort(list);

		    try (BufferedWriter writer = new BufferedWriter(
		            new OutputStreamWriter(new FileOutputStream(datei.toFile()), StandardCharsets.UTF_8)
		    )) {
		        writer.write(header);
		        writer.newLine();

		        for (Lernkarte k : list) {
		            writer.write(k.exportiereAlsCsv());
		            writer.newLine();
		        }
		    }
		}


}
