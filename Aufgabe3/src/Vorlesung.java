import java.util.List;

public class Vorlesung {

	public String studiengruppe;
	public String titel;
	public String dozent_in;
	public int teilnehmerzahl;
	
	
	public Vorlesung(List<String> zeile) {
		studiengruppe = zeile.get(0);
		titel = zeile.get(1);
		dozent_in = zeile.get(2);
		teilnehmerzahl = Integer.parseInt(zeile.get(3));
	}


	@Override
	public String toString() {
		return "Vorlesung [studiengruppe=" + studiengruppe + ", titel=" + titel + ", dozent_in=" + dozent_in
				+ ", teilnehmerzahl=" + teilnehmerzahl + "]";
	}
	
	

}
