public class GrosseZahlTestMain
{
	public static void main(String[] args)
	{
		GrosseZahl z1 = new GrosseZahl(101);
		GrosseZahl z2 = new GrosseZahl("101");
		z1.printZahl();
		z2.printZahl();
		System.out.println(z2.less(z1));
		System.out.println(z1.add(z2));
		System.out.println(z1.mult(z2));
		System.out.println(z1.subtract(z2));
		System.out.println(z1.ggT(z2));
	}
}