package lkarten;

public class Main {
	public static void test() {
		
	}
	public static void main(String[] args) {
		Lernkartei lernkartei = new Lernkartei();
		Lernkarte karte1 = new Lernkarte("A","1","1","1");
		Lernkarte karte2 = new Lernkarte("B","2","2","2");
		Lernkarte karte3 = new Lernkarte("B","3","3","3");
		lernkartei.hinzufuegen(karte1);
		lernkartei.hinzufuegen(karte2);
		lernkartei.hinzufuegen(karte3);
		lernkartei.druckeAlleKarten();
		System.out.println("=====================================================");
		for(int i=0;i<lernkartei.gibKartenZuKategorie("B").length;i++) {
			if(lernkartei.gibKartenZuKategorie("B")[i] !=null){
						lernkartei.gibKartenZuKategorie("B")[i].druckeKarte();
			}
			
		}
		
		for(int i=0;i<lernkartei.erzeugeDeck(3).length;i++) {
			
			
		}
	}

}
