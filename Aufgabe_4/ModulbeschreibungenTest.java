import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.TestTimedOutException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ModulbeschreibungenTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(2);

	private static int counter = 1;

	@Rule
	public TestWatcher watcher = new TestWatcher() {

		@Override
		protected void starting(Description description) {
			System.out.println("\n___________________________________________\n\nSTARTING TEST " + counter + ": '"
					+ description.getMethodName() + "'...");
			counter++;
		}

		@Override
		protected void finished(Description description) {
			System.out.println("FINISHED TEST: " + description.getMethodName()
					+ ".\n___________________________________________\n");
		}

		@Override
		protected void succeeded(Description description) {
			System.out.println("PASSED TEST: " + description.getMethodName());
		}

		@Override
		protected void failed(Throwable e, Description description) {
			if (e instanceof TestTimedOutException) {
				System.err.println("\"--> ERROR(s) in '" + description.getMethodName()
						+ "' - beendet aufgrund eines Timeouts!\nBitte vor Abgabe von Aufgaben pruefen auf Endlosschleifen. ");

			}

			System.out.println("FAILED TEST: " + description.getMethodName());
		}

		@Override
		protected void skipped(AssumptionViolatedException e, Description description) {
			System.err.println("An error occured during the JUnit-Test '" + description.getMethodName() + "':\n"
					+ e.toString() + "\n\nPLEASE CONTACT AN ADMIN");
		}

	};

	final String filename = "mb-junit.txt";
	PrintWriter pw;

	Class<Modul> cm = Modul.class;
	Class<Veranstaltung> cv = Veranstaltung.class;

	@Before
	public void setUp() throws IOException {
		pw = new PrintWriter(filename);
	}

	void einlesen() {
		pw.print(
				"Computeranimation|CMAN-I17|BI|WPM|Wahlpflichtmodul Zertifikat Medieninformatik|7,5|Klausur 1,5h oder muendliche Pruefung|M. Rauschenberger\n"
						+ "Computeranimation|B. Arp|4\n" + "Praktikum Computeranimation|B. Arp|2\n" + "\n"
						+ "Software-Qualitaetsmanagement|SWQM-I17|BI|6|Pflichtmodul|5|Klausur 1,5h oder muendliche Pruefung|N. Streekmann\n"
						+ "Software-Qualitaetssicherung|N. Streekmann|2\n"
						+ "Praktikum Software-Qualitaetssicherung|N. Streekmann|2\n" + "\n"
						+ "Interdisziplinaeres Arbeiten|IARB-I17|BI|WPM|Wahlpflichtmodul|2,5|Studienarbeit|M. Krueger-Basener\n"
						+ "Neue Technik-Horizonte|M. Krueger-Basener|2\n" + "\n"
						+ "Mikrowellenmesstechnik|MWMT-I17|BI|WPM|Wahlpflichtmodul|2,5|muendliche Pruefung oder Kursarbeit oder Klausur 1 h|H.-F. Harms\n"
						+ "Mikrowellenmesstechnik|J. Wiebe (LB)|2\n" + "\n"
						+ "AV-Produktion|AVPR-M17|BMT|WPM|Wahlpflichtmodul Zertifikat AV-Technik und Zertifikat Computer-Aided Media Production|5|Klausur 1,5h oder mndliche Pr�fung oder Studienarbeit|T. Lemke\n"
						+ "Audiovisuelle Produktion|T. Lemke, A. Klein|4\n" + "\n"
						+ "Marketing fuer Ingenieure|MRKT-I17|BI|WPM|Wahlpflichtmodul Zertifikat Marketing und Vertrieb|5|Klausur 1,5 h oder muendliche Pruefung oder Kursarbeit|L. Jaenchen\n"
						+ "Marketing fuer Ingenieure|L. Jaenchen|2\n"
						+ "Praktikum Marketing fuer Ingenieure|L. Jaenchen|2\n" + "\n"
						+ "Digitale Signalverarbeitung|DSVA-E17|BET|WPM|Wahlpflichtmodul Zertifikat Nachrichtentechnik und Zertifikat Technische Informatik|5|Klausur 1,5 h oder muendliche Pruefung|J.-M. Batke\n"
						+ "Digitale Signalverarbeitung|J.-M. Batke|3\n"
						+ "Praktikum Digitale Signalverarbeitung|J.-M. Batke|1\n" + "\n"
						+ "Multimediaprojekte|MMPJ-M17|BMT|WPM|Wahlpflichtmodul Zertifikat Computer-Aided Media Production|5|Erstellung und Dokumentation von Rechnerprogrammen und/oder Muendliche Pruefung|G. J. Veltink\n"
						+ "Multimediaprojekte|G. J. Veltink|2\n" + "Praktikum Multimediaprojekte|G. J. Veltink|2\n"
						+ "\n"
						+ "HW/SW Codesign|HWSW-E17|BET|WPM|Wahlpflichtmodul Zertifikat Technische Informatik|5|Klausur 1,5h oder muendliche Pruefung oder Studienarbeit|C. Koch\n"
						+ "HW/SW-Codesign|C. Koch|2\n" + "Praktikum HW/SW-Codesign|C. Koch|2\n" + "\n"
						+ "Softwaresicherheit|SWSE-I17|BI|WPM|Wahlpflichtmodul Zertifikat IT-Sicherheit|5|Kursarbeit oder Klausur 1,5h|C. Link\n"
						+ "Softwaresicherheit|C. Link|4\n" + "\n"
						+ "MATLAB Seminar||BI|WPM|Wahlpflichtmodul|2,5|Studienarbeit|G. Kane\n"
						+ "MATLAB Seminar|G. Kane|2\n" + "\n"
						+ "Data Science|DASC-I17|BI|5|Pflichtmodul|5|Klausur 1,5 h oder muendliche Pruefung|T. Schmidt\n"
						+ "Data Science|T. Schmidt|3\n" + "Praktikum Data Science|T. Schmidt|1\n" + "\n"
						+ "Kommunikationssysteme|KOSY-I17|BI|WPM|Wahlpflichtmodul|5|Kursarbeit oder muendliche Pruefung oder Klausur 1 h|H.-F. Harms\n"
						+ "Kommunikationssysteme|T. Buescher|2\n" + "Praktikum Kommunikationssysteme|H.-F. Harms|2\n"
						+ "\n"
						+ "Betriebssysteme|BTRS-I17|BI|4|Pflichtmodul|5|Klausur 1,5h oder muendliche Pruefung|C. Link\n"
						+ "Betriebssysteme|C. Link|2\n" + "Praktikum Betriebssysteme|C. Link|2\n" + "\n"
						+ "Hochfrequenztechnik|HFTE-E17|BET|WPM|Wahlpflichtmodul Zertifikat Nachrichtentechnik|5|Kursarbeit oder muendliche Pruefung oder Klausur 1,0 h|H.-F. Harms\n"
						+ "Hochfrequenztechnik|H.-F. Harms|2\n" + "Praktikum Hochfrequenztechnik|H.-F. Harms|2\n" + "\n"
						+ "HW/SW Codesign|HWSW-E17|BI|WPM|Wahlpflichtmodul Zertifikat Technische Informatik|5|Klausur 1,5h oder muendliche Pruefung oder Studienarbeit|C. Koch\n"
						+ "HW/SW-Codesign|C. Koch|2\n" + "Praktikum HW/SW-Codesign|C. Koch|2\n" + "\n"
						+ "Digitale Fotografie|DIFO-M17|BMT|WPM|Wahlpflichtmodul|2,5|Kursarbeit|C. Koch\n"
						+ "Digitale Fotografie|E. Buehler (LB)|2\n" + "\n"
						+ "Elektrotechnik 3|ETE3-E17|BET|3|Pflichtmodul|5|Klausur 1,5 h|J. Rolink\n"
						+ "Elektrische Maschinen|M. Masur|2\n" + "Praktikum Elektrotechnik B|J. Rolink|2\n" + "\n"
						+ "Mathematik 2|MAT2-M17|BMT|2|Pflichtmodul|7,5|Klausur 1,5 h oder muendliche Pruefung|I. Schebesta\n"
						+ "Mathematik 2|I. Schebesta|4\n" + "Uebung Mathematik 2|R. Heuermann|2\n" + "\n");
		pw.close();
	}

	@Test
	public void getZertifikate_Test() throws IOException {
		Set<String> s1 = new TreeSet<String>();
		Set<String> s2 = new TreeSet<String>();
		Set<String> s3 = new TreeSet<String>();

		s1.add("Nachrichtentechnik");
		s1.add("Technische Informatik");

		s2.add("IT-Sicherheit");
		s2.add("Marketing und Vertrieb");
		s2.add("Medieninformatik");
		s2.add("Technische Informatik");

		s3.add("AV-Technik");
		s3.add("Computer-Aided Media Production");

		einlesen();

		Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(s1, l.getZertifikate("BET"));
		assertEquals(s2, l.getZertifikate("BI"));
		assertEquals(s3, l.getZertifikate("BMT"));
	}

	@Test
	public void getVerzahnteModule_Test() throws IOException {
		Set<String> s1 = new TreeSet<String>();
		s1.add("HW/SW Codesign");
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(s1, l.getVerzahnteModule());
	}

	@Test
	public void getAnzahlModule_Test() throws IOException {
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);

		assertEquals(3, l.getAnzahlModule("BET", false));
		assertEquals(1, l.getAnzahlModule("BET", true));
		assertEquals(4, l.getAnzahlModule("BET", null));
		assertEquals(8, l.getAnzahlModule("BI", false));
		assertEquals(3, l.getAnzahlModule("BI", true));
		assertEquals(11, l.getAnzahlModule("BI", null));
		assertEquals(3, l.getAnzahlModule("BMT", false));
		assertEquals(1, l.getAnzahlModule("BMT", true));
		assertEquals(4, l.getAnzahlModule("BMT", null));
	}

	@Test
	public void getAnzahlVeranstaltungen_Test() throws IOException {
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);

		assertEquals(6, l.getAnzahlVeranstaltungen("BET", false));
		assertEquals(2, l.getAnzahlVeranstaltungen("BET", true));
		assertEquals(8, l.getAnzahlVeranstaltungen("BET", null));
		assertEquals(12, l.getAnzahlVeranstaltungen("BI", false));
		assertEquals(6, l.getAnzahlVeranstaltungen("BI", true));
		assertEquals(18, l.getAnzahlVeranstaltungen("BI", null));
		assertEquals(4, l.getAnzahlVeranstaltungen("BMT", false));
		assertEquals(2, l.getAnzahlVeranstaltungen("BMT", true));
		assertEquals(6, l.getAnzahlVeranstaltungen("BMT", null));
	}

	@Test
	public void getSWS_Test() throws IOException {
		Map<Integer, Integer> a1 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> a2 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> a3 = new HashMap<Integer, Integer>();

		a1.put(3, 4);
		a2.put(4, 4);
		a2.put(5, 4);
		a2.put(6, 4);
		a3.put(2, 6);

		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(a1, l.getSWS("BET"));
		assertEquals(a2, l.getSWS("BI"));
		assertEquals(a3, l.getSWS("BMT"));
	}

	@Test
	public void getECTS_Test() throws IOException {
		Map<Integer, Integer> a1 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> a2 = new HashMap<Integer, Integer>();
		Map<Integer, Integer> a3 = new HashMap<Integer, Integer>();

		a1.put(3, 5);
		a2.put(4, 5);
		a2.put(5, 5);
		a2.put(6, 5);
		a3.put(2, 8);

		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(a1, l.getECTS("BET"));
		assertEquals(a2, l.getECTS("BI"));
		assertEquals(a3, l.getECTS("BMT"));
	}

	@Test
	public void getSortierteStudiengaenge_Test() throws IOException {
		List<String> s1 = new ArrayList<String>();
		s1.add("BET");
		s1.add("BMT");
		s1.add("BI");
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);
		assertEquals(s1, l.getSortierteStudiengaenge());
	}

	@Test
	public void getJSON_Test() throws IOException {
		einlesen();
		Modulbeschreibungen l = new Modulbeschreibungen(filename);
		String json = l.getJSON("BI");
		assertTrue(json.startsWith("["));
		assertTrue(json.endsWith("]"));
		assertTrue(json.contains("\"bezeichnung\""));
		assertTrue(json.contains("\"kuerzel\""));
		assertTrue(json.contains("\"veranstaltungen\""));
		assertTrue(json.contains("\"sws\":"));
	}

	@Test
	public void getJSON_ValidierungMitParser() throws IOException {
	    einlesen();
	    Modulbeschreibungen l = new Modulbeschreibungen(filename);

	    String[] studiengaenge = {"BI", "BET", "BMT"};
	    for (String studiengang : studiengaenge) {
	        String json = l.getJSON(studiengang);
	        JSONArray arr = new JSONArray(json);
	        for (int i = 0; i < arr.length(); i++) {
	            JSONObject modul = arr.getJSONObject(i);
	            assertTrue("Fehlende Bezeichnung", modul.has("bezeichnung"));
	            assertTrue("Fehlendes Kuerzel", modul.has("kuerzel"));
	            assertTrue("Fehlende Veranstaltungen", modul.has("veranstaltungen"));
	            JSONArray veranstaltungen = modul.getJSONArray("veranstaltungen");
	            for (int j = 0; j < veranstaltungen.length(); j++) {
	                JSONObject v = veranstaltungen.getJSONObject(j);
	                assertTrue("Veranstaltung ohne Titel", v.has("titel"));
	                assertTrue("Veranstaltung ohne SWS", v.has("sws"));
	            }
	        }
	    }
	}

	
	@Test
    public void testJSON_ErwarteteBezeichnungen() throws IOException {
        einlesen();
        Modulbeschreibungen l = new Modulbeschreibungen(filename);

        Map<String, List<String>> erwarteteBezeichnungen = new HashMap<>();
        erwarteteBezeichnungen.put("BI", Arrays.asList("Computeranimation", "Software-Qualitaetsmanagement", "Interdisziplinaeres Arbeiten", "Mikrowellenmesstechnik", "Softwaresicherheit", "MATLAB Seminar", "Data Science"));
        erwarteteBezeichnungen.put("BET", Collections.singletonList("HW/SW Codesign"));
        erwarteteBezeichnungen.put("BMT", Arrays.asList("AV-Produktion", "Digitale Fotografie", "Mathematik 2", "Multimediaprojekte"));

        for (String studiengang : erwarteteBezeichnungen.keySet()) {
            String json = l.getJSON(studiengang);
            JSONArray arr = new JSONArray(json);
            List<String> gefundeneBezeichnungen = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject modul = arr.getJSONObject(i);
                gefundeneBezeichnungen.add(modul.getString("bezeichnung"));
            }
            for (String erwartet : erwarteteBezeichnungen.get(studiengang)) {
                assertTrue("Bezeichnung nicht gefunden: " + erwartet + " in " + studiengang,
                    gefundeneBezeichnungen.contains(erwartet));
            }
        }
    }
	
    @Test
    public void testJSON_ErwarteteVeranstaltungen() throws IOException {
        einlesen();
        Modulbeschreibungen l = new Modulbeschreibungen(filename);

        Map<String, List<String>> erwartete = new HashMap<>();
        erwartete.put("Computeranimation", Arrays.asList("Computeranimation", "Praktikum Computeranimation"));
        erwartete.put("Software-Qualitaetsmanagement", Arrays.asList("Software-Qualitaetssicherung", "Praktikum Software-Qualitaetssicherung"));
        erwartete.put("Interdisziplinaeres Arbeiten", Arrays.asList("Neue Technik-Horizonte"));
        erwartete.put("Mikrowellenmesstechnik", Arrays.asList("Mikrowellenmesstechnik"));
        erwartete.put("Softwaresicherheit", Arrays.asList("Softwaresicherheit"));
        erwartete.put("MATLAB Seminar", Arrays.asList("MATLAB Seminar"));
        erwartete.put("Data Science", Arrays.asList("Data Science", "Praktikum Data Science"));
        erwartete.put("Marketing fuer Ingenieure", Arrays.asList("Marketing fuer Ingenieure", "Praktikum Marketing fuer Ingenieure"));
        erwartete.put("Kommunikationssysteme", Arrays.asList("Kommunikationssysteme", "Praktikum Kommunikationssysteme"));
        erwartete.put("Betriebssysteme",Arrays.asList("Betriebssysteme", "Praktikum Betriebssysteme"));
        erwartete.put("HW/SW Codesign", Arrays.asList("HW/SW-Codesign","Praktikum HW/SW-Codesign"));

        String json = l.getJSON("BI");
        JSONArray arr = new JSONArray(json);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject modul = arr.getJSONObject(i);
            String bezeichnung = modul.getString("bezeichnung");
            assertTrue("Unerwartete Modulbezeichnung: " + bezeichnung, erwartete.containsKey(bezeichnung));

            JSONArray veranstaltungen = modul.getJSONArray("veranstaltungen");
            List<String> erwarteteTitel = erwartete.get(bezeichnung);
            List<String> gefundeneTitel = new ArrayList<>();
            for (int j = 0; j < veranstaltungen.length(); j++) {
                JSONObject v = veranstaltungen.getJSONObject(j);
                gefundeneTitel.add(v.getString("titel"));
            }
            for (String erwartet : erwarteteTitel) {
                assertTrue("Veranstaltung nicht gefunden: " + erwartet + " in Modul " + bezeichnung,
                        gefundeneTitel.contains(erwartet));
            }
        }
    }

	
	@Test
	public void testLeereDatei() throws IOException {
		try (PrintWriter pw = new PrintWriter("leer.txt")) {
			// leer lassen
		}
		Modulbeschreibungen l = new Modulbeschreibungen("leer.txt");
		assertEquals(0, l.getAnzahlModule("BI", null));
		assertTrue(l.getZertifikate("BI").isEmpty());
		assertTrue(l.getVerzahnteModule().isEmpty());
	}

	@Test
	public void testPflichtUndWPMGemischt() throws IOException {
		try (PrintWriter pw = new PrintWriter("gemischt.txt")) {
			pw.print("Modul A|MOD-A|BI|2|Pflichtmodul|5|Klausur|F. Rump\n");
			pw.print("A1|F. Rump|2\n\n");
			pw.print("Modul B|MOD-B|BI|WPM|Wahlpflichtmodul Zertifikat IT-Sicherheit|5|Studienarbeit|F. Rump\n");
			pw.print("B1|F. Rump|2\n\n");
		}
		Modulbeschreibungen l = new Modulbeschreibungen("gemischt.txt");
		assertEquals(1, l.getAnzahlModule("BI", true));
		assertEquals(1, l.getAnzahlModule("BI", false));
		assertEquals(2, l.getAnzahlModule("BI", null));
		assertEquals(1, l.getZertifikate("BI").size());
		assertTrue(l.getZertifikate("BI").contains("IT-Sicherheit"));
	}
}
