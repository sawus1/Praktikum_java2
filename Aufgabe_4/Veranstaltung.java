import java.util.List;

public class Veranstaltung {

	String titel;
	String Dozent;
	int sws;
	
	public Veranstaltung(List<String> data) {
		titel = data.get(0);
		Dozent = data.get(1);
		sws = Integer.parseInt(data.get(2));
		
	}
	
	@Override
	public String toString(){
		return ("Veranstaltung: " + titel + ", Dozent: " + Dozent + ", SWS: " + sws);
	}
	
}
