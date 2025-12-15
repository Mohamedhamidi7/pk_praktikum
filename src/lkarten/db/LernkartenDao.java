package lkarten.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lkarten.DoppelteKarteException;
import lkarten.EinzelantwortKarte;
import lkarten.Lernkarte;
import lkarten.MehrfachantwortKarte;

/**
 * Data Access Object (DAO) fuer Lernkarten. Diese Klasse enthaelt Methoden zum
 * Verwalten von Lernkarten in der Datenbank.
 */
public class LernkartenDao {

	private final Connection conn;

	public LernkartenDao(Connection conn) {
		this.conn = conn;
	}

	public int createEinzelantwortKarte(EinzelantwortKarte karte)
	        throws SQLException, DoppelteKarteException {

	    String sql = """
	        INSERT OR IGNORE INTO lernkarte
	        (kategorie, titel, frage, typ, antwort)
	        VALUES (?, ?, ?, 'E', ?)
	        """;

	    try (PreparedStatement ps = connection.prepareStatement(
	            sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, karte.getKategorie());
	        ps.setString(2, karte.getTitel());
	        ps.setString(3, karte.getFrage());
	        ps.setString(4, karte.getAntwort());

	        int affected = ps.executeUpdate();
	        if (affected == 0) {
	            throw new DoppelteKarteException("Karte existiert bereits");
	        }

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }
	    }
	    return -1;
	}


	public int createMehrfachantwortKarte(MehrfachantwortKarte karte)
	        throws SQLException, DoppelteKarteException {

	    String sql = """
	        INSERT OR IGNORE INTO lernkarte
	        (kategorie, titel, frage, typ)
	        VALUES (?, ?, ?, 'M')
	        """;

	    try (PreparedStatement ps = connection.prepareStatement(
	            sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, karte.getKategorie());
	        ps.setString(2, karte.getTitel());
	        ps.setString(3, karte.getFrage());

	        int affected = ps.executeUpdate();
	        if (affected == 0) {
	            throw new DoppelteKarteException("Karte existiert bereits");
	        }

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                createAntwortoptionen(karte, id);
	                return id;
	            }
	        }
	    }
	    return -1;
	}


	public List<Lernkarte> findAll() throws SQLException {

	    List<Lernkarte> result = new ArrayList<>();

	    String sql = "SELECT * FROM lernkarte";

	    try (PreparedStatement ps = connection.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            if (rs.getString("typ").equals("E")) {
	                result.add(mapEinzelantwortKarte(rs));
	            } else {
	                result.add(mapMehrfachantwortKarte(rs));
	            }
	        }
	    }
	    return result;
	}


	public List<Lernkarte> findByKategorie(String kategorie) throws SQLException {

	    List<Lernkarte> result = new ArrayList<>();

	    String sql = "SELECT * FROM lernkarte WHERE kategorie = ?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, kategorie);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                if (rs.getString("typ").equals("E")) {
	                    result.add(mapEinzelantwortKarte(rs));
	                } else {
	                    result.add(mapMehrfachantwortKarte(rs));
	                }
	            }
	        }
	    }
	    return result;
	}


	/**
	 * Zaehlt die Anzahl der Lernkarten in der Datenbank.
	 * 
	 * @return Anzahl der Lernkarten
	 * @throws SQLException bei SQL-Fehlern
	 */
	public int countLernkarten() throws SQLException {
		String sql = "SELECT COUNT(*) AS count FROM lernkarte";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt("count");
			} else {
				throw new SQLException("Count query returned no result.");
			}
		}
	}

	/**
	 * Fuegt die Antwortoptionen der gegebenen Mehrfachantwortkarte in die Datenbank
	 * ein.
	 * 
	 * @param karte Mehrfachantwortkarte mit den einzufuegenden Antwortoptionen
	 * @throws SQLException bei SQL-Fehlern
	 */
	private void createAntwortoptionen(MehrfachantwortKarte karte) throws SQLException {
		String[] moeglicheAntworten = karte.getMoeglicheAntworten();
		int[] richtigeAntworten = karte.getRichtigeAntworten();

		// Insert-Statement fuer Antwortoptionen
		String sql = "INSERT OR IGNORE INTO mehrfachantwort (lernkarte_id, antwort_index, antwort_text, ist_richtig) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (int i = 0; i < moeglicheAntworten.length; i++) {
				ps.setInt(1, karte.getId());
				ps.setInt(2, i);
				ps.setString(3, moeglicheAntworten[i]);
				ps.setBoolean(4, istAntwortRichtig(i, richtigeAntworten));
				// Statements fuer einzelne Antwortoptionen in einem Batch sammeln
				ps.addBatch();
			}
			// Batch ausfuehren
			ps.executeBatch();
		}
	}

	/**
	 * Prueft, ob die Antwort mit dem gegebenen Index zu den richtigen Antworten
	 * gehoert.
	 * 
	 * @param antwortIndex      Index der zu pruefenden Antwort (aus Array
	 *                          "moeglicheAntworten")
	 * @param richtigeAntworten Array mit den Indizes der richtigen Antworten
	 * @return true, wenn die Antwort richtig ist, sonst falseÏ
	 */
	private boolean istAntwortRichtig(int antwortIndex, int[] richtigeAntworten) {
		if (richtigeAntworten == null)
			return false;
		for (int r : richtigeAntworten)
			if (r == antwortIndex)
				return true;
		return false;
	}

	/**
	 * Konvertiert ein ResultSet in eine EinzelantwortKarte.
	 * 
	 * @param rs ResultSet mit den Daten der EinzelantwortKarte
	 * @return EinzelantwortKarte-Objekt
	 * @throws SQLException bei SQL-Fehlern
	 */
	private EinzelantwortKarte mapEinzelantwortKarte(ResultSet rs) throws SQLException {
		return new EinzelantwortKarte(rs.getInt("id"), rs.getString("kategorie"), rs.getString("titel"),
				rs.getString("frage"), rs.getString("antwort"));
	}

	/**
	 * Konvertiert ein ResultSet in eine MehrfachantwortKarte. Da sich die
	 * Antwortoptionen in einer separaten Tabelle befinden, wird eine zweite Abfrage
	 * ausgefuehrt, um diese abzurufen.
	 * 
	 * @param rs ResultSet mit den Grunddatendaten der MehrfachantwortKarte
	 * @return MehrfachantwortKarte-Objekt
	 * @throws SQLException bei SQL-Fehlern
	 */
	private MehrfachantwortKarte mapMehrfachantwortKarte(ResultSet rs) throws SQLException {
		MehrfachantwortKarte karte = null;
		int id = rs.getInt("id");

		// Zweite Abfrage fuer die Antwortoptionen
		String sql = "	SELECT antwort_index, antwort_text, ist_richtig FROM mehrfachantwort	WHERE lernkarte_id = ? ORDER BY antwort_index";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs2 = ps.executeQuery()) {

				List<String> moeglicheAntworten = new ArrayList<>();
				List<Integer> richtigeAntworten = new ArrayList<>();

				while (rs2.next()) {
					int antwortIndex = rs2.getInt("antwort_index");
					String text = rs2.getString("antwort_text");
					boolean istRichtig = rs2.getBoolean("ist_richtig");

					// Dank Sortierung nach antwort_index bleiben die Antworten in der richtigen
					// Reihenfolge
					moeglicheAntworten.add(text);
					if (istRichtig)
						richtigeAntworten.add(antwortIndex);
				}

				// Liste der richtigen Antworten manuell in ein Array umwandeln, da
				// toArray() nur fuer Objekttypen verfuegbar ist
				int[] richtigeArr = new int[richtigeAntworten.size()];
				for (int i = 0; i < richtigeAntworten.size(); i++)
					richtigeArr[i] = richtigeAntworten.get(i);

				karte = new MehrfachantwortKarte(id, rs.getString("kategorie"), rs.getString("titel"),
						rs.getString("frage"), moeglicheAntworten.toArray(new String[0]), richtigeArr);
			}
		}
		return karte;
	}
}
