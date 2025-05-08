
public class GrosseZahl{
	
	private int[] zahl;
	
	public GrosseZahl(int i)
	{
		String d = Integer.toString(i);
		zahl = new int[d.length()];
		for(int j = 0; j < zahl.length - 1; j++)
		{
			zahl[j] = (int)d.charAt(j) - '0';
		}
	}
	public GrosseZahl(String d)
	{
		zahl = new int[d.length()];
		for(int i = 0; i < zahl.length - 1; i++)
		{
			zahl[i] = (int)d.charAt(i) - '0';
		}
	}
	public void printZahl()
	{
		for(int i : zahl)
		{
			System.out.print(i);
		}
		System.out.println();
	}
}
