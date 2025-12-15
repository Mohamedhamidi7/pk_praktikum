package lkarten;

import java.util.Arrays;

public class MehrfachantwortKarte extends Lernkarte{
	private String[] moeglicheAntworten;
	private int[] richtigeAntworten;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(moeglicheAntworten);
		result = prime * result + Arrays.hashCode(richtigeAntworten);
		return result;
	}

	public void validiere() throws UngueltigeKarteExeption {
		super.validiere();
		if(moeglicheAntworten.length>1 && moeglicheAntworten[0] == null || moeglicheAntworten[0].isBlank() && moeglicheAntworten[1] == null || moeglicheAntworten[1].isBlank() )
			throw new UngueltigeKarteExeption("Das Feld darf nicht leer sein.");
	}

	@Override
	public String exportiereAlsCsv() {
	    String moegl = Arrays.toString(moeglicheAntworten);
	    String richtig = Arrays.toString(richtigeAntworten);

	    return getId() + "," + getKategorie() + "," + getTitel() + "," + getFrage() + "," + moegl + "," + richtig;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MehrfachantwortKarte other = (MehrfachantwortKarte) obj;
		return Arrays.equals(moeglicheAntworten, other.moeglicheAntworten)
				&& Arrays.equals(richtigeAntworten, other.richtigeAntworten);
	}



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
