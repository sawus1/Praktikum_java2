public class Aufgabe2
{
	public static void main(String[] args)
	{
		CharCollection c1 = new CharCollection("");
		CharCollection c2 = new CharCollection("AAAA");
		c1.printCollection();
		System.out.println(c1.toString());
		System.out.println(c1.different());
		System.out.println(c1.top());
		System.out.println(c1.equals(null));
		CharCollection c3 = c2.moreThan(1);
		c3.printCollection();
	}
	
}