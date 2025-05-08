import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Modulbeschreibungen implements IModulBeschreibungen{

	List<Modul> module = new ArrayList<Modul>();
	String filename;

	public Modulbeschreibungen(String file) throws IOException {
		filename = file;
		List<List<String>> fullFile = load(filename);

		for(int i = 0; i < fullFile.size(); i++) {

			
			Modul m = new Modul(fullFile.get(i));
			
			while((i+1) < fullFile.size() && (fullFile.get(i+1).size() > 1)) {
				m.Veranstaltungen.add(new Veranstaltung(fullFile.get(i+1)));
				i++;
			}
			i++;
			module.add(m);
			//module.put(key, m);	
			
			//m.printModul();
		}
		
	
		// lade die datei
	}

	public static List<List<String>> load(String filename) throws IOException {		// gibt jede Zeile als Liste an Strings zurück
		List<List<String>> result = new ArrayList<List<String>>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result.add(Arrays.asList(line.split("\\|")));
		}
		br.close();
		return result;
	}

	public Set<String> getZertifikate(String Studiengang){
		Set<String> zertifikate = new HashSet<String>();
		return null;
	}
	
	public Set<String> getVerzahnteModule(){
		Set<String> verzahnteModule = new HashSet<String>();
		for(Modul m : module)
		{
			for(Modul s : module)
			{
				if(m.name.equals(s.name) && !m.studiengang.equals(s.studiengang))
				{
					verzahnteModule.add(m.kuerzel);
				}
			}
		}
		return verzahnteModule;
	}
	
	public int getAnzahlModule(String Studiengang, Boolean pflicht)
	{
		int count = 0;
		String istPflicht = pflicht.booleanValue()? "Pflichtmodul" : "Wahlpflichtmodul";
		for(Modul m : module)
		{
			if(m.studiengang.equals(Studiengang) && istPflicht.equals(m.type)) count++;
		}
		return count;
	}
	
	public static void main(String[] args) {
		try {
			Modulbeschreibungen mb = new Modulbeschreibungen(
					"/Users/oleksandrsavcenko/Workspace/Java/Praktikum_Programmieren2/Aufgabe_4/Modulbeschreibungen");
			
			System.out.println("Anzahl der Pflichtmodule für BET: " + mb.getAnzahlModule("BET", true));
			System.out.println("Anzahl der Wahlpflichtmodule für BET: " + mb.getAnzahlModule("BET", false));
			System.out.println("");
			System.out.println("Verzahnte Module: ");
			System.out.println("");
			for(String s : mb.getVerzahnteModule())
			{
				System.out.println(s);
			}
			System.out.println(mb.getJSON("BET"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getJSON(String Studiengang)
	{
		
		Set<Modul> mod = new HashSet<Modul>();
		for(Modul m : module)
		{
			if(m.studiengang.equals(Studiengang)) mod.add(m);
		}
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("[{");
		
		for(Modul modul : mod)
		{
			jsonResult.append("\t\"bezeichnung\": " + "\"" + modul.name + "\",\n");
			jsonResult.append("\t\"kuerzel\": " + "\"" + modul.kuerzel + "\",\n");
			jsonResult.append("\t\"studiengang\": " + "\"" + modul.studiengang + "\",\n");
			jsonResult.append("\t\"semester\": " + "\"" + modul.semester + "\",\n");
			jsonResult.append("\t\"art\": " + "\"" + modul.type + "\",\n");
			jsonResult.append("\t\"ects\": "  + modul.ectsPoints + ",\n");
			jsonResult.append("\t\"pruefungsform\": " + "\"" + modul.examtype + "\",\n");
			jsonResult.append("\t\"verantwortlicher\": " + "\"" + modul.PrsInCharge + "\",\n");
			jsonResult.append("\t\"veranstaltungen\": [{\n");
			for(Veranstaltung v : modul.Veranstaltungen)
			{
				jsonResult.append("\t\t\"titel\": " + "\"" + v.titel + "\",\n");
				jsonResult.append("\t\t\"dozenten\": " + "\"" + v.Dozent + "\",\n");
				jsonResult.append("\t\t\"sws\": " + v.sws + ",\n");
				jsonResult.append("\t}, {\n");
			}
			jsonResult.replace(jsonResult.length() - 4, jsonResult.length() - 1, "]");
			jsonResult.append("}, {\n");
		}
		jsonResult.replace(jsonResult.length() - 4, jsonResult.length() - 1, "]");
		return jsonResult.toString();
		
		
		
	}

}
