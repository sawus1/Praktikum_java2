
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Modulbeschreibungen implements IModulbeschreibungen {

//	Map<Modul, List<Veranstaltung>> module = new HashMap<>();
	List<Modul> module = new ArrayList<>();
	Set<String> studiengaenge = new TreeSet<>(Comparator.reverseOrder());

	public Modulbeschreibungen(String file) throws IOException {
		List<List<String>> fullFile = load(file);

		for (int i = 0; i < fullFile.size(); i++) {

//			System.out.println(fullFile.get(i));
//			System.out.println(fullFile.get(i+1));
//			System.out.println(fullFile.get(i+2));		
//			System.out.println("---");

			Modul m = new Modul(fullFile.get(i));
//			System.out.println(key);
			while ((i + 1) < fullFile.size() && (fullFile.get(i + 1).size() > 1)) {
				m.Veranstaltungen.add(new Veranstaltung(fullFile.get(i + 1)));
				i++;
			}
			i++;

			module.add(m);

			if (!studiengaenge.contains(m.studiengang))
				studiengaenge.add(m.studiengang);

		}
		System.out.println("studiengänge" + studiengaenge);

	}

	public static List<List<String>> load(String filename) throws IOException { // gibt jede Zeile als Liste an Strings
																				// zurück
		List<List<String>> result = new ArrayList<List<String>>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result.add(Arrays.asList(line.split("\\|")));
		}
		br.close();
		return result;
	}

	@Override
	public Set<String> getZertifikate(String studiengang) {
		Set<String> alleZertifikate = new HashSet<>();
		for (Modul modul : module) {
			if (modul.studiengang.equals(studiengang)) {
				String type = modul.type;
				if (type.contains("Zertifikat")) {

					String[] typeMitZertifikat = type.split(" Zertifikat ");
//					for (String s : typeMitZertifikat)
//						System.out.print(s + " ");
//					System.out.println();

					for (int i = 1; i < typeMitZertifikat.length; i++) {
						StringBuilder zertifikat = new StringBuilder(typeMitZertifikat[i]);
						
						//System.out.println(zertifikat);
						
						//und
						if(zertifikat.toString().endsWith("und"))
							zertifikat.delete(zertifikat.length()-4, zertifikat.length());
							
						// , Wahlpflichtmodul
						if(zertifikat.toString().contains("Wahlpflichtmodul"))
							zertifikat.delete(zertifikat.indexOf("Wahlpflichtmodul")-2, zertifikat.indexOf("Wahlpflichtmodul")+"Wahlpflichtmodul".length() );
						
						
						
						alleZertifikate.add(zertifikat.toString());
					}
				}
			}
		}
		return alleZertifikate;

	}

	public int getMaxSemester() {
		int maxSemester = 0;
		for (Modul modul : module) { // Höchstes Semester herausfinden

			if (!modul.semester.contains("WPM") && (Integer.parseInt(modul.semester)) > maxSemester) {
				maxSemester = Integer.parseInt(modul.semester);
			}
		}
		return maxSemester;

	}

	public Set<String> getVerzahnteModule() {
		Set<String> verzahnteModule = new HashSet<>();
		for (Modul m : module) {
			for (Modul s : module) {
				if (m.name.equals(s.name) && !m.studiengang.equals(s.studiengang)) {
					verzahnteModule.add(m.kuerzel);
				}
			}
		}
		return verzahnteModule;
	}

	@Override
	public int getAnzahlModule(String Studiengang, Boolean pflicht) {
		int count = 0;
		if(pflicht != null) {
		String istPflicht = pflicht.booleanValue() ? "Pflichtmodul" : "Wahlpflichtmodul";
		for (Modul m : module) {
			if (m.studiengang.equals(Studiengang) && istPflicht.equals(m.type))
				count++;
			}
		return count;
		}
		else 
			return module.size();
	}

	@Override
	public int getAnzahlVeranstaltungen(String studiengang, Boolean pflicht) {
		int result = 0;

		for (Modul modul : module) {
			List<Veranstaltung> veranstaltung = new ArrayList<>(modul.Veranstaltungen);
			if (pflicht == null) {
				if (modul.studiengang.equals(studiengang)) {
					result += veranstaltung.size();
				}

			} else {
				String compareType = pflicht ? "Pflichtmodul" : "Wahlpflichtmodul";
				if ((modul.type.contains(compareType)) && (modul.studiengang.equals(studiengang))) {
					result += veranstaltung.size();
				}
			}

		}

		return result;
	}

	@Override
	public Map<Integer, Integer> getECTS(String studiengang) {
		Map<Integer, Integer> allECTS = new HashMap<>();
		int maxSemester = getMaxSemester();

		for (Integer semester = 1; semester <= maxSemester; semester++) {
			Double ECTSofSemester = 0.0;
			for (Modul modul : module) {

				if (!modul.semester.contains("WPM") && Integer.parseInt(modul.semester) == semester
						&& modul.studiengang.equals(studiengang))
					ECTSofSemester += Double.parseDouble(modul.ectsPoints.replace(",", "."));
			}
			Integer ECTSasInt = (int) (Math.round(ECTSofSemester));
			if(ECTSasInt > 0)
				allECTS.put(semester, ECTSasInt);
		}
		return allECTS;
	}

	@Override
	public Map<Integer, Integer> getSWS(String studiengang) {
		Map<Integer, Integer> allSWS = new HashMap<>();
		int maxSemester = getMaxSemester();

		for (Integer i = 1; i <= maxSemester; i++) {
			Integer SWSofSemester = 0;
			for (Modul modul : module) {
				List<Veranstaltung> v = new ArrayList<>(modul.Veranstaltungen);

				if (!modul.semester.contains("WPM") && Integer.parseInt(modul.semester) == i
						&& modul.studiengang.equals(studiengang)) {
					for (int k = 0; k < v.size(); k++) {
						SWSofSemester += v.get(k).sws;
					}
				}
			}
			if(SWSofSemester > 0)
				allSWS.put(i, SWSofSemester);
		}
		return allSWS;
	}

	@Override
	public List<String> getSortierteStudiengaenge() {
		List<String> sortierteSg = new ArrayList<>();
		Map<String, Integer> SWSproSg = new HashMap<>();
		List<Integer> justSWS = new ArrayList<>();

		for (String s : studiengaenge) {
			int SWSvonS = 0;
			for (Map.Entry<Integer, Integer> entry : getSWS(s).entrySet())

				SWSvonS += entry.getValue();
			SWSproSg.put(s, SWSvonS);
			justSWS.add(SWSvonS);
		}
		while (justSWS.size() > 0) {
			int maxSWS = Collections.max(justSWS);
			for (Map.Entry<String, Integer> entry : SWSproSg.entrySet()) {
				if (entry.getValue() == maxSWS) {
					if (!sortierteSg.contains(entry.getKey())) {
						sortierteSg.add(0, entry.getKey());
						justSWS.remove(Integer.valueOf(maxSWS));
					}
				}
			}
		}
		return sortierteSg;
	}

	@Override
	public String getJSON(String Studiengang) {

		Set<Modul> mod = new HashSet<Modul>();
		for (Modul m : module) {
			if (m.studiengang.equals(Studiengang))
				mod.add(m);
		}

		StringBuilder jsonResult = new StringBuilder();

		jsonResult.append("[{");

		for (Modul modul : mod) {
			jsonResult.append("\t\"bezeichnung\": " + "\"" + modul.name + "\",\n");
			jsonResult.append("\t\"kuerzel\": " + "\"" + modul.kuerzel + "\",\n");
			jsonResult.append("\t\"studiengang\": " + "\"" + modul.studiengang + "\",\n");
			jsonResult.append("\t\"semester\": " + "\"" + modul.semester + "\",\n");
			jsonResult.append("\t\"art\": " + "\"" + modul.type + "\",\n");
			jsonResult.append("\t\"ects\": " + modul.ectsPoints + ",\n");
			jsonResult.append("\t\"pruefungsform\": " + "\"" + modul.examtype + "\",\n");
			jsonResult.append("\t\"verantwortlicher\": " + "\"" + modul.PrsInCharge + "\",\n");
			jsonResult.append("\t\"veranstaltungen\": [{\n");
			for (Veranstaltung v : modul.Veranstaltungen) {
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

	public static void main(String[] args) {
		try {
			String testDateipfad = "/home/ino/eclipse-workspace/Programmieren2/src/Aufgabe_4/Modulbeschreibungen";
			Modulbeschreibungen mb = new Modulbeschreibungen(testDateipfad);

			System.out.println("Anzahl Module insgesamt: " + mb.module.size());

			for (String studiengang : mb.studiengaenge) {
				System.out.println("Studiengang: " + studiengang);
				System.out.println("  Pflichtmodule: " + mb.getAnzahlModule(studiengang, true));
				System.out.println("  Wahlpflichtmodule: " + mb.getAnzahlModule(studiengang, false));
				System.out.println("  Veranstaltungen (alle): " + mb.getAnzahlVeranstaltungen(studiengang, null));
				System.out.println("  ECTS je Semester: " + mb.getECTS(studiengang));
				System.out.println("  SWS je Semester: " + mb.getSWS(studiengang));
				System.out.println();
				System.out.println(mb.getJSON(studiengang));
			}

			System.out.println("Verzahnte Module: " + mb.getVerzahnteModule());
			System.out.println("Sortierte Studiengänge (nach SWS): " + mb.getSortierteStudiengaenge());
			System.out.println("Zertifikate für BMT: " + mb.getZertifikate("BMT"));
			System.out.println("Zertifikate für BI: " + mb.getZertifikate("BI"));
			System.out.println("Zertifikate für BET: " + mb.getZertifikate("BET"));

		} catch (IOException e) {
			System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
		}
	}

}