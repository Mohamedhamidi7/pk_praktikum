package lkarten;

import java.util.Objects;

public class EinzelantwortKarte extends Lernkarte {
	private String antwort;
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(antwort);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EinzelantwortKarte other = (EinzelantwortKarte) obj;
		return Objects.equals(antwort, other.antwort);
	}
	public EinzelantwortKarte(String kategorie, String titel, String frage, String antwort) {
		super(kategorie, titel, frage);
		this.antwort = antwort;
	}
	public void validiere() throws UngueltigeKarteExeption {
		super.validiere();
		if(antwort == null || antwort.isBlank()) throw new UngueltigeKarteExeption("Das Feld darf nicht leer sein.");
	}
	
	@Override
	public String exportiereAlsCsv() {
	    return getId() + "," + getKategorie() + "," + getTitel() + "," + getFrage() + "," + antwort + ",";
	}

	
	@Override
	public void zeigeRueckseite() {
		System.out.println("	" + antwort);
	}

}
