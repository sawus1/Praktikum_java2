import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.TestTimedOutException;

public class VorlesungsverzeichnisTest {
    String filename = "db_junit.txt";
    PrintWriter pw;

    
    @Rule
	public Timeout globalTimeout = Timeout.seconds(2);

	@Rule
	public TestName testnames = new TestName();

	private static int counter = 1;

	@Rule
	public TestWatcher watcher = new TestWatcher() {

		protected void starting(Description description) {
			System.out.println("\n___________________________________________\n\nSTARTING TEST " + counter + ": '"
					+ description.getMethodName() + "'...");
			counter++;
		}

		protected void finished(Description description) {
			System.out.println("FINISHED TEST: " + description.getMethodName()
					+ ".\n___________________________________________\n");
		}

		protected void succeeded(Description description) {
			System.out.println("PASSED TEST: " + description.getMethodName());
		}

		protected void failed(Throwable e, Description description) {
			if (e instanceof TestTimedOutException) {
				System.err.println("--> ERROR(s) in '" + description.getMethodName()
						+ "' - beendet aufgrund eines Timeouts!\nBitte vor Abgabe von Aufgaben pruefen auf Endlosschleifen. ");

			}

			System.out.println("FAILED TEST: " + description.getMethodName());
		}

		protected void skipped(AssumptionViolatedException e, Description description) {
			System.err.println("An error occured during the JUnit-Test '" + description.getMethodName() + "':\n"
					+ e.toString() + "\n\nPLEASE CONTACT AN ADMIN");
		}

	};
    
    @Before
    public void setUp() throws IOException {
        pw = new PrintWriter(filename);
    }

    @Test
    public void testTitles() throws IOException, TextFileFormatException {
        List<String> expected = Arrays.asList(
            "Elektrodynamik", "Quantenmechanik", "Quantenphysik",
            "Relativitaetstheorie", "Theoretische Physik", "Thermodynamik"
        );

        pw.print("" +
            "A1:Relativitaetstheorie:Einstein:15\n" +
            "B2:Quantenmechanik:Heisenberg:17\n" +
            "C2:Quantenphysik:Planck:5\n" +
            "T4:Thermodynamik:Kelvin:78\n" +
            "C2:Theoretische Physik:Kelvin:54\n" +
            "B2:Thermodynamik:Planck:44\n" +
            "T4:Quantenphysik:Planck:45\n" +
            "B2:Elektrodynamik:Kelvin:34");
        pw.close();

        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        assertEquals(expected, v.titles());
    }

    @Test
    public void testWorkaholics() throws IOException, TextFileFormatException {
        Set<String> expected = new HashSet<>(Arrays.asList("Planck", "Kelvin"));

        pw.print("" +
            "A1:Relativitaetstheorie:Einstein:15\n" +
            "B2:Quantenmechanik:Heisenberg:17\n" +
            "C2:Quantenphysik:Planck:5\n" +
            "T4:Thermodynamik:Kelvin:78\n" +
            "C2:Theoretische Physik:Kelvin:54\n" +
            "B2:Thermodynamik:Planck:44\n" +
            "T4:Quantenphysik:Planck:45\n" +
            "B2:Elektrodynamik:Kelvin:34");
        pw.close();

        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        assertEquals(expected, v.workaholics());
    }

    @Test
    public void testGroupToTitles() throws IOException, TextFileFormatException {
        pw.print("" +
            "A1:Relativitaetstheorie:Einstein:15\n" +
            "B2:Quantenmechanik:Heisenberg:17\n" +
            "C2:Quantenphysik:Planck:5\n" +
            "T4:Thermodynamik:Kelvin:78\n" +
            "C2:Theoretische Physik:Kelvin:54\n" +
            "B2:Thermodynamik:Planck:44\n" +
            "T4:Quantenphysik:Planck:45\n" +
            "B2:Elektrodynamik:Kelvin:34");
        pw.close();

        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        Map<String, List<String>> result = v.groupToTitles();

        assertTrue(result.containsKey("A1"));
        assertEquals(Collections.singletonList("Relativitaetstheorie"), result.get("A1"));
        assertTrue(result.get("B2").contains("Quantenmechanik"));
        assertTrue(result.get("C2").contains("Quantenphysik"));
        assertTrue(result.get("T4").contains("Thermodynamik"));
    }

    @Test
    public void testMultipleTitles() throws IOException, TextFileFormatException {
        pw.print("" +
            "A1:Relativitaetstheorie:Einstein:15\n" +
            "B2:Quantenmechanik:Heisenberg:17\n" +
            "C2:Quantenphysik:Planck:5\n" +
            "T4:Thermodynamik:Kelvin:78\n" +
            "C2:Theoretische Physik:Kelvin:54\n" +
            "B2:Thermodynamik:Planck:44\n" +
            "T4:Quantenphysik:Planck:45\n" +
            "B2:Elektrodynamik:Kelvin:34");
        pw.close();

        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        Map<String, List<String>> result = v.multipleTitles();
        assertTrue(result.containsKey("Thermodynamik"));
        assertEquals(2, result.get("Thermodynamik").size());
        assertTrue(result.get("Thermodynamik").contains("Planck"));
        assertTrue(result.get("Thermodynamik").contains("Kelvin"));
    }

    @Test
    public void testDescendingTitles() throws IOException, TextFileFormatException {
        List<String> expected = Arrays.asList(
            "Thermodynamik", "Theoretische Physik", "Quantenphysik",
            "Thermodynamik", "Elektrodynamik", "Quantenmechanik",
            "Relativitaetstheorie", "Quantenphysik"
        );

        pw.print("" +
            "A1:Relativitaetstheorie:Einstein:15\n" +
            "B2:Quantenmechanik:Heisenberg:17\n" +
            "C2:Quantenphysik:Planck:5\n" +
            "T4:Thermodynamik:Kelvin:78\n" +
            "C2:Theoretische Physik:Kelvin:54\n" +
            "B2:Thermodynamik:Planck:44\n" +
            "T4:Quantenphysik:Planck:45\n" +
            "B2:Elektrodynamik:Kelvin:34");
        pw.close();

        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        assertEquals(expected, v.descendingTitles());
    }

    @Test(expected = TextFileFormatException.class)
    public void testTooFewFields() throws IOException, TextFileFormatException {
        pw.print("B2:Thermodynamik:Planck\n");
        pw.close();
        new Vorlesungsverzeichnis(filename);
    }

    @Test(expected = TextFileFormatException.class)
    public void testTooManyFields() throws IOException, TextFileFormatException {
        pw.print("B2:Thermodynamik:Planck:44:Extra\n");
        pw.close();
        new Vorlesungsverzeichnis(filename);
    }

    @Test(expected = TextFileFormatException.class)
    public void testEmptyField() throws IOException, TextFileFormatException {
        pw.print(":Quantenphysik:Planck:45\n");
        pw.close();
        new Vorlesungsverzeichnis(filename);
    }

    @Test(expected = TextFileFormatException.class)
    public void testInvalidNumber() throws IOException, TextFileFormatException {
        pw.print("B2:Elektrodynamik:Kelvin:NaN\n");
        pw.close();
        new Vorlesungsverzeichnis(filename);
    }

    @Test
    public void testSingleLecture() throws IOException, TextFileFormatException {
        pw.print("X1:Mathematik 1:Euler:100\n");
        pw.close();

        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        assertEquals(Arrays.asList("Mathematik 1"), v.titles());
        assertTrue(v.workaholics().isEmpty());
        assertEquals(Arrays.asList("Mathematik 1"), v.groupToTitles().get("X1"));
        assertTrue(v.multipleTitles().isEmpty());
        assertEquals(Arrays.asList("Mathematik 1"), v.descendingTitles());
    }

    @Test
    public void testEmptyFile() throws IOException, TextFileFormatException {
        pw.close();
        Vorlesungsverzeichnis v = new Vorlesungsverzeichnis(filename);
        assertTrue(v.titles().isEmpty());
        assertTrue(v.workaholics().isEmpty());
        assertTrue(v.groupToTitles().isEmpty());
        assertTrue(v.multipleTitles().isEmpty());
        assertTrue(v.descendingTitles().isEmpty());
    }

    
}
