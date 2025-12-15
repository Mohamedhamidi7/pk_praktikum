package lkarten;

import java.util.Objects;

public abstract class Lernkarte implements Comparable<Lernkarte>, ValidierbareKarte, CsvExportable {
	private static int currId = 0;
	private int id;
	private String kategorie;
	private String titel;
	private String frage;
	//private String antwort;
	
	public int getId() {
	    return id;
	}

	public String getTitel() {
	    return titel;
	}

	public String getFrage() {
	    return frage;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(frage, id, kategorie, titel);
	}

	@Override
	public int compareTo(Lernkarte o) {
		if(this.id>o.id) return -1;
		if(this.id<o.id) return 1;
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lernkarte other = (Lernkarte) obj;
		return Objects.equals(frage, other.frage) && id == other.id && Objects.equals(kategorie, other.kategorie)
				&& Objects.equals(titel, other.titel);
	}
	
	/*, String antwort*/
	public Lernkarte(String kategorie, String titel, String frage  ) {
		this.id = currId++;
		this.kategorie = kategorie;
		this.titel = titel;
		this.frage = frage;
		//this.antwort = antwort;
	}
	
	public String getKategorie() {
		return kategorie;
	}


	public void validiere() throws UngueltigeKarteExeption {
		if(kategorie == null ||titel == null || frage == null || kategorie.isBlank() ||titel.isBlank()|| frage.isBlank() )
			throw new UngueltigeKarteExeption("Das Feld darf nicht leer sein.");
	}
	
	public void zeigeVorderseite() {
		System.out.println("[" + id + ", " + kategorie + "] " + titel + ": " + frage);
	}
	public abstract void zeigeRueckseite();
//	public void zeigeRueckseite() {
//		System.out.println("	" + antwort);
//	}
	public void druckeKarte() {
		zeigeVorderseite();
		zeigeRueckseite();
	}
}
