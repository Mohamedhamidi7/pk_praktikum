package lkarten;

public abstract class Lernkarte {
	private static int currId = 0;
	private int id;
	private String kategorie;
	private String titel;
	private String frage;
	//private String antwort;
	
	
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
