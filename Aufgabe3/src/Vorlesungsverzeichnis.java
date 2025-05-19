import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

	public Vorlesungsverzeichnis(String filename) throws IOException, TextFileFormatException {
		List<List<String>> Datenbasis;
//		try {
		Datenbasis = load(filename);
		for (List<String> zeile : Datenbasis) {
			// Überprüfen ob Zeile gültig ist:
			if (zeile.isEmpty())
				throw new TextFileFormatException("Zeile ist Leer");
			// Anzahl Elemente
			if (zeile.size() != 4)
				throw new TextFileFormatException("Länge der Zeile ungültig");

			// Leere Elemente
			for (int i = 0; i < 4; i++) {
				if (zeile.get(i).isBlank())
					throw new TextFileFormatException("Element " + i + " ist Leer");
			}
			// Falsche Datentypen
			for (int i = 0; i < 4; i++) {
				String s = zeile.get(i);
				char[] charArray = s.toCharArray();
				if (i == 3) { // sollte Zahl sein
					for (char ch : charArray) {
						if (!Character.isDigit(ch)) {
							throw new TextFileFormatException("Letztes Element der Zeile muss eine Zahl sein");
						}
					}
				}

			}

			vorlesungen.add(new Vorlesung(zeile));
		}
//		} catch (IOException | TextFileFormatException e) {
//			// throw TextFileFormatException();
//			e.printStackTrace();
//		} 
//		catch (TextFileFormatException e) {
//			//System.err.println("Datei hat das falsche Format");
//			e.printStackTrace();
//		}

	}

	public List<String> titles() {
		List<String> alleTitel = new ArrayList<>();
		for (Vorlesung v : vorlesungen) {
			if(!alleTitel.contains(v.titel))
				alleTitel.add(v.titel);
		}
		Collections.sort(alleTitel);
//		alleTitel.sort(null);
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

		while (titles.size() != vorlesungen.size()) { // solange bis alle Titel eigefügt wurden
			int groessteTeilnehmer = 0;
			for (Vorlesung v : alleVorlesungen) { // Welche ist die größte Teilnehmeranzahl
				if (groessteTeilnehmer < v.teilnehmerzahl)
					groessteTeilnehmer = v.teilnehmerzahl;
			}
			for (Vorlesung v : vorlesungen) { // Größte Vorlesung zum Ergebnis hinzufügen und aus den restlichen
												// entfernen
				if (v.teilnehmerzahl == groessteTeilnehmer) {
					titles.add(v.titel);
					alleVorlesungen.remove(v);
				}
			}
		}
		return titles;
	}

	public static void main(String[] args) {

		Vorlesungsverzeichnis vl;
		try {
			vl = new Vorlesungsverzeichnis(
					"/home/ino/Praktikum_java2/Aufgabe3/src/datei.txt");
			List<String> titelImVerzeichnis = vl.titles();
			System.out.println("-----Alle Titel-----");
			for(String s:titelImVerzeichnis) {
				System.out.println(s);
			}
			
			System.out.println("workaholics: "+vl.workaholics());
			System.out.println("group to titles: "+vl.groupToTitles());
			System.out.println("Mehrfache titel: " + vl.multipleTitles());
			
			
			System.out.println("In absteigender Teilnehmerzahl: " + vl.descendingTitles());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TextFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}
}