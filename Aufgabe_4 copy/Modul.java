import java.util.ArrayList;
import java.util.List;

public class Modul {

	String name;
	String kuerzel;
	String studiengang;
	String semester;
	String type;
	String ectsPoints;
	String examtype;
	String PrsInCharge;
	List<Veranstaltung> Veranstaltungen = new ArrayList<>();
	
	public Modul(List<String> data) {
		int indexBuffer = (data.size() == 8) ? 0 : -1;
		name = data.get(0);
		if (data.size() == 8) kuerzel = data.get(1);
		studiengang = data.get(2+indexBuffer);
		semester = data.get(3+indexBuffer);
		type = data.get(4+indexBuffer);
		ectsPoints = data.get(5+indexBuffer);
		examtype = data.get(6+indexBuffer);
		PrsInCharge = data.get(7+indexBuffer);
	}
	
	
}
