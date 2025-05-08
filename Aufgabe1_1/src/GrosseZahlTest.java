

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.AssumptionViolatedException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.TestTimedOutException;

public class GrosseZahlTest {

	final static String ERR_PREFIX = "--> ERROR(s) in";

	@BeforeClass
	public static void announcements() {
		System.out.println("ACHTUNG! Pruefen Sie die Rueckgabe Ihrer toString Methode! Darauf basieren einige Tests.");

	}

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
				System.err.println(ERR_PREFIX + " '" + description.getMethodName()
						+ "' - beendet aufgrund eines Timeouts!\nBitte vor Abgabe von Aufgaben pruefen auf Endlosschleifen. ");

			}

			System.out.println("FAILED TEST: " + description.getMethodName());
		}

		protected void skipped(AssumptionViolatedException e, Description description) {
			System.err.println("An error occured during the JUnit-Test '" + description.getMethodName() + "':\n"
					+ e.toString() + "\n\nPLEASE CONTACT AN ADMIN");
		}

	};

	@Test
	public void testConstructorFromString() {
		GrosseZahl g = new GrosseZahl("123456");
		assertEquals("Fehler im String-Konstruktor: Erwartet '123456', aber war '" + g.toString() + "'", "123456",
				g.toString());
	}

	@Test
	public void testConstructorFromInt() {
		GrosseZahl g = new GrosseZahl(789);
		assertEquals("Fehler im Int-Konstruktor: Erwartet '789', aber war '" + g.toString() + "'", "789", g.toString());
	}

	@Test
	public void testLess() {
		GrosseZahl a = new GrosseZahl("123");
		GrosseZahl b = new GrosseZahl("456");
		GrosseZahl c = new GrosseZahl("123");

		assertTrue("Fehler in less: 123 sollte kleiner als 456 sein", a.less(b));
		assertFalse("Fehler in less: 456 sollte nicht kleiner als 123 sein", b.less(a));
		assertFalse("Fehler in less: 123 sollte nicht kleiner als 123 sein", a.less(c));
	}

	@Test
	public void testAdd() {
		GrosseZahl a = new GrosseZahl("123");
		GrosseZahl b = new GrosseZahl("877");
		GrosseZahl c = new GrosseZahl("1001");

		assertEquals("Fehler in add: 123 + 877 sollte 1000 ergeben", "1000", a.add(b).toString());
		assertEquals("Fehler in add: 1001 + 1001 sollte 2002 ergeben", "2002", c.add(c).toString());
		assertEquals("Fehler in add: 999 + 0 sollte 999 ergeben", "999",
				new GrosseZahl("999").add(new GrosseZahl("0")).toString());
		assertEquals("Fehler in add: 0 + 0 sollte 0 ergeben", "0",
				new GrosseZahl("0").add(new GrosseZahl("0")).toString());
	}

	@Test
	public void testSubtract() {
		GrosseZahl a = new GrosseZahl("1000");
		GrosseZahl b = new GrosseZahl("999");
		GrosseZahl c = new GrosseZahl("1");
		GrosseZahl d = new GrosseZahl("500");

		assertEquals("Fehler in subtract: 1000 - 999 sollte 1 ergeben", "1", a.subtract(b).toString());
		assertEquals("Fehler in subtract: 1000 - 1 sollte 999 ergeben", "999", a.subtract(c).toString());
		assertEquals("Fehler in subtract: 1000 - 500 sollte 500 ergeben", "500", a.subtract(d).toString());
	}

	@Test
	public void testSubtractWithLeadingZeros() {
		GrosseZahl a = new GrosseZahl("10000");
		GrosseZahl b = new GrosseZahl("9999");

		assertEquals("Fehler in subtract: 10000 - 9999 sollte 1 ergeben (Problem mit führenden Nullen)", "1",
				a.subtract(b).toString());

		GrosseZahl c = new GrosseZahl("100000");
		GrosseZahl d = new GrosseZahl("99999");

		assertEquals("Fehler in subtract: 100000 - 99999 sollte 1 ergeben", "1", c.subtract(d).toString());
	}

	@Test
	public void testMultiply() {
		GrosseZahl a = new GrosseZahl("123");
		GrosseZahl b = new GrosseZahl("3");
		GrosseZahl c = new GrosseZahl("0");
		GrosseZahl d = new GrosseZahl("2");

		assertEquals("Fehler in mult: 123 * 3 sollte 369 ergeben", "369", a.mult(b).toString());
		assertEquals("Fehler in mult: 123 * 0 sollte 0 ergeben", "0", a.mult(c).toString());
		assertEquals("Fehler in mult: 123 * 2 sollte 246 ergeben", "246", a.mult(d).toString());
		assertEquals("Fehler in mult: 999 * 1 sollte 999 ergeben", "999",
				new GrosseZahl("999").mult(new GrosseZahl("1")).toString());
	}

	@Test
	public void testGGT() {
		GrosseZahl a = new GrosseZahl("48");
		GrosseZahl b = new GrosseZahl("18");
		GrosseZahl c = new GrosseZahl("101");

		assertEquals("Fehler in ggT: ggT(48,18) sollte 6 ergeben", "6", a.ggT(b).toString());
		assertEquals("Fehler in ggT: ggT(101,101) sollte 101 ergeben", "101", c.ggT(c).toString());
		assertEquals("Fehler in ggT: ggT(17,13) sollte 1 ergeben", "1",
				new GrosseZahl("17").ggT(new GrosseZahl("13")).toString());

		GrosseZahl d = new GrosseZahl("270");
		GrosseZahl e = new GrosseZahl("192");

		assertEquals("Fehler in ggT: ggT(270,192) sollte 6 ergeben", "6", d.ggT(e).toString());
	}

	@Test
	public void testToString() {
		GrosseZahl a = new GrosseZahl("0001234");
		assertEquals("Fehler in toString: Erwartet '0001234', aber war '" + a.toString() + "'", "0001234",
				a.toString());

		GrosseZahl b = new GrosseZahl("000000");
		assertEquals("Fehler in toString: Erwartet '000000', aber war '" + b.toString() + "'", "000000", b.toString());

		GrosseZahl c = new GrosseZahl("123456");
		assertEquals("Fehler in toString: Erwartet '123456', aber war '" + c.toString() + "'", "123456", c.toString());
	}

	@Test
	public void testZeroHandling() {
		GrosseZahl zero1 = new GrosseZahl("0000");
		GrosseZahl zero2 = new GrosseZahl("0");
		GrosseZahl zero3 = new GrosseZahl(0);

		assertEquals("Fehler in Zero-Handling: '0000' sollte als '0000' gespeichert sein", "0000", zero1.toString());
		assertEquals("Fehler in Zero-Handling: '0' sollte als '0' gespeichert sein", "0", zero2.toString());
		assertEquals("Fehler in Zero-Handling: 0 sollte als '0' gespeichert sein", "0", zero3.toString());
	}

	@Test
	public void testExtraMethods() {

		List<String> methodNames = List.of("toString", "less", "add", "mult", "ggT", "main");
		GrosseZahl o = new GrosseZahl("0");
		Class<?> c = o.getClass();
		
		Method[] methods = c.getDeclaredMethods();
		String name = null;
		int counter = 0;
		for (Method m : methods) {
			m.setAccessible(true);// falls private
			name = m.getName();
			if (methodNames.contains(name) == false) {
				counter++;
			}
		}
		System.out.println("Anzahl zusaetzlicher Methoden: "+counter);
		assertFalse("Fehler in 'Extra Methods': Sie haetten zusaetzliche Methoden anlegen sollen",counter== 0);
	}
}
