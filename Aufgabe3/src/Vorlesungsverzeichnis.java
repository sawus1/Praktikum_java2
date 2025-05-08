import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vorlesungsverzeichnis {

	Set<Vorlesung> vorlesungen = new HashSet<>();

	public static List<List<String>> load(String filename) throws IOException {
		List<List<String>> result = new ArrayList<List<String>>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		for (String line = br.readLine(); line != null; line = br.readLine())
			result.add(Arrays.asList(line.split(":")));
		br.close();
		return result;
	}

	public Vorlesungsverzeichnis(String filename) {
		List<List<String>> Datenbasis;
		try {
			Datenbasis = load(filename);
			for (List<String> zeile : Datenbasis) {
				vorlesungen.add(new Vorlesung(zeile));
			}
		} catch (IOException e) {
			// throw TextFileFormatException();
			System.err.println("FEHLER!!!!!!!");
			e.printStackTrace();
		}

	}

	public List<String> titles() {
		List<String> alleTitel = new ArrayList<>();
		for (Vorlesung v : vorlesungen) {
			alleTitel.add(v.titel);
		}
		alleTitel.sort(null);
		return alleTitel;
	}

	public Set<String> workaholics() {
		Set<String> dozenten = new HashSet<>();
		Set<String> workaholics = new HashSet<>();

		for (Vorlesung v : vorlesungen) {
			if (dozenten.contains(v.dozent_in))
				workaholics.add(v.dozent_in);
			dozenten.add(v.dozent_in);
		}
		return workaholics;
	}

	public Map<String, List<String>> groupToTitles() {
		Map<String, List<String>> groupToTitles = new HashMap<>();
		Set<String> gruppen = new HashSet<>();

		for (Vorlesung v : vorlesungen) {
			gruppen.add(v.studiengruppe);
		}
		for (String gruppe : gruppen) {
			List<String> titel = new ArrayList<>();
			for (Vorlesung v : vorlesungen) {
				if (gruppe.contentEquals(v.studiengruppe)) {
					titel.add(v.titel);
				}
			}
			groupToTitles.put(gruppe, titel);
		}
		return groupToTitles;
	}

	public Map<String, List<String>> multipleTitles() {
		Map<String, List<String>> multipleTitles = new HashMap<>();
		Set<String> vorlesung = new HashSet<>();

		for (Vorlesung v : vorlesungen) {
			vorlesung.add(v.titel);
		}
		for (String titel : vorlesung) {
			List<String> Dozent = new ArrayList<>();
			for (Vorlesung v : vorlesungen) {
				if (titel.contentEquals(v.titel)) {
					Dozent.add(v.dozent_in);
				}
			}
			if (Dozent.size() > 1)
				multipleTitles.put(titel, Dozent);
		}
		return multipleTitles;
	}

	public List<String> descendingTitles() {
		List<String> titles = new ArrayList<>();
		Set<Vorlesung> alleVorlesungen = new HashSet<>(vorlesungen);
		int groessteTeilnehmer = 0;
		while (titles.size() != titles().size()) {
			for (Vorlesung v : alleVorlesungen) {
				if (groessteTeilnehmer < v.teilnehmerzahl)
					groessteTeilnehmer = v.teilnehmerzahl;
			}

			for (Vorlesung v : vorlesungen) {
				if (v.teilnehmerzahl == groessteTeilnehmer) {
					titles.add(v.titel);
				}
			}
		}

		return titles;
	}

	public static void main(String[] args) {

		Vorlesungsverzeichnis vl = new Vorlesungsverzeichnis("/Users/oleksandrsavcenko/Workspace/Java/Praktikum_Programmieren2/Aufgabe3/src/datei.txt");
		System.out.println(vl.workaholics());
		System.out.println(vl.groupToTitles());
		System.out.println("Mehrfache titel: " + vl.multipleTitles());

	}
}
