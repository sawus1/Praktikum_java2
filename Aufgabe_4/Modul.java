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
	
	public void printModul() {
		System.out.println("---------------------------------");
		System.out.println("Name : " + name);
		System.out.println("Kuerzel : " + kuerzel);
		System.out.println("Studiengang : " + studiengang);
		System.out.println("Semester : " + semester);
		System.out.println("Type : " + type);
		System.out.println("ECTSPoints : " + ectsPoints);
		System.out.println("Examtype : " + examtype);
		System.out.println("PrsInCharge : " + PrsInCharge);
		System.out.println("*********************************");
		for(Veranstaltung v : Veranstaltungen) System.out.println(v.toString());
		System.out.println();
	}
	
	
}
