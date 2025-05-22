
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.TestTimedOutException;

import java.io.*;
import java.net.*;

public class KlausurenServerTest {

	private PrintWriter ausSo;
	private BufferedReader einSo;
	private Socket so;
	private String line;

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

	@BeforeClass
	public static void announcements() {
		System.out.println("\n\n---ooo--- SERVER GESTARTET? ---ooo---");

	}

	private void opensocket() throws IOException {
		so = new Socket("localhost", 2000); // Port 2000 wird erwartet!
		ausSo = new PrintWriter(so.getOutputStream(), true);
		einSo = new BufferedReader(new InputStreamReader(so.getInputStream()));
	}

	private void closesocket() throws IOException {
		if (so != null && !so.isClosed())
			so.close();
	}

	private void sendAndRead(String command) throws IOException {
		opensocket();
		ausSo.println(command);
		line = einSo.readLine();
		closesocket();
	}

	@Before
	public void setUp() throws IOException {

		System.out.println("== INITIALISIERUNG ==");

		for (int i = 1; i <= 4; i++) {
			sendAndRead("del mail" + i);
			System.out.println("DEL mail" + i + " -> " + line);
		}

		System.out.println("== INITIALISIERUNG OK ==\n");
	}

	@Test
	public void fullTestSequence() throws IOException {
		step("del mail1", "0");

		step("put mail1 22,24", "1", true);
		step("put mail2 22,23,24", "1", true);
		step("put mail3 2", "1", true);

		stepContains("getall", "1 [2],[22,23,24]", "1 [22,23,24],[2]");

		step("del mail1", "1 22,24");
		step("put mail2 33,34,35", "1 22,23,24");
		step("get mail2", "1 33,34,35");

		step("put mail3 44,45,46", "1 2", true);
		step("put mail4 44,45", "1", true);

		stepContains("getall", "1 [33,34,35],[44,45,46]", "1 [44,45,46],[33,34,35]");

		step("del mail2", "1 33,34,35");
		step("del mail3", "1 44,45,46");
		step("get mail2", "0");
		step("getall", "1 [44,45]");

		step("put mail2 45,46", "1", true);
		step("put mail3 44,46", "1", true);

		stepContains("getall", "1 [44,45],[44,46],[45,46]", "1 [44,46],[45,46],[44,45]", "1 [45,46],[44,46],[44,45]",
				"1 [44,45,46]");

		step("stop", "1", true);
	}

	private void step(String command, String expected) throws IOException {
		step(command, expected, false);
	}

	private void step(String command, String expected, boolean allowSpaceOnlyResponse) throws IOException {
		System.out.println("TEST: " + command);
		sendAndRead(command);
		System.out.println("Antwort: " + line);

		if (allowSpaceOnlyResponse) {
			assertTrue("Erwartet: '" + expected + "' oder Leerfolge, bekommen: " + line,
					line.equals(expected) || line.equals(expected + " ") || line.equals("1") || line.equals("1 "));
		} else {
			assertEquals("Falsche Antwort auf: " + command, expected, line);
		}

		System.out.println("TEST OK\n");
	}

	private void stepContains(String command, String... possibleOutputs) throws IOException {
		System.out.println("TEST: " + command);
		sendAndRead(command);
		System.out.println("Antwort: " + line);

		boolean found = false;
		while (found == false) {
			for (String expected : possibleOutputs) {
				if (line.equals(expected)) {
					found = true;

				}
			}
		}

		if (!found) {
			fail("Antwort '" + line + "' nicht in erlaubten Werten: " + String.join(" | ", possibleOutputs));
		}

		System.out.println("TEST OK\n");
	}
}
