package lkarten;

public class MehrfachantwortKarte extends Lernkarte{
	private String[] moeglicheAntworten;
	private int[] richtigeAntworten;
	
	public MehrfachantwortKarte(String kategorie, String titel, String frage, String[] moeglicheAntworten, int[] richtigeAntworten) {
		super(kategorie, titel, frage);
		this.moeglicheAntworten = moeglicheAntworten;
		this.richtigeAntworten = richtigeAntworten;
	}
	
	

	@Override
	public void zeigeVorderseite() {
		super.zeigeVorderseite();
		for(int i=0 ; i<moeglicheAntworten.length ; i++) {
			System.out.println((i+1) + ": " + moeglicheAntworten[i] );
		}
		if(richtigeAntworten.length>1) System.out.println("(mehrere Antworten möglich)");
	}
	
	@Override
	public void zeigeRueckseite() {
		for(int e : richtigeAntworten) {
			System.out.println((e+1) +": " + moeglicheAntworten[e]);
		}
	}
}
