package lkarten;

public class Main {
	public static void main(String[] args) {
		Lernkartei lernkartei = new Lernkartei();
		Menu menu = new Menu();
		MehrfachantwortKarte karte1 = new MehrfachantwortKarte("A","titel","frage",new String[]{"1","2","3"},new int[]{1,2});
		MehrfachantwortKarte karte2 = new MehrfachantwortKarte("A","titel2","frage2",new String[]{"1","2","3"},new int[]{1,2});
		EinzelantwortKarte karte3 = new EinzelantwortKarte("A","titel3","frage3","antwort3");
		EinzelantwortKarte karte4 = new EinzelantwortKarte("B","titel4","frage4","antwort4");

		menu.lernkartei.hinzufuegen(karte1);
		menu.lernkartei.hinzufuegen(karte2);
		menu.lernkartei.hinzufuegen(karte3);
		menu.lernkartei.hinzufuegen(karte4);
		
//		lernkartei.druckeAlleKarten();
//		System.out.println("============================================");
//		int anzahlKarten = lernkartei.gibAnzahlKarten();
//		System.out.println(anzahlKarten);
//		System.out.println("============================================");
//		Lernkarte[] deck = lernkartei.erzeugeDeck(3);
//		for(Lernkarte e : deck) e.druckeKarte();
//		System.out.println("============================================");
//		Lernkarte[] kategorie = lernkartei.gibKartenZuKategorie("A");
//		for(Lernkarte e : kategorie) if(e!=null) e.druckeKarte();
		
		menu.start();
	}
}
