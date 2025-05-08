import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Modulbeschreibungen implements IModulbeschreibungen{

	Map<String, Modul> module = new HashMap<>();
	String filename;

	public Modulbeschreibungen(String file) throws IOException {
		filename = file;
		List<List<String>> fullFile = load(filename);

		for(int i = 0; i < fullFile.size(); i++) {
			
//			System.out.println(fullFile.get(i));
//			System.out.println(fullFile.get(i+1));
//			System.out.println(fullFile.get(i+2));		
//			System.out.println("---");
			
			
			String key = fullFile.get(i).get(0);
//			System.out.println(key);
			Modul m = new Modul(fullFile.get(i));
			
			while((i+1) < fullFile.size() && (fullFile.get(i+1).size() > 1)) {
				m.Veranstaltungen.add(new Veranstaltung(fullFile.get(i+1)));
				i++;
			}
			i++;
			module.put(key, m);	
		}
		
	
		// lade die datei
	}

	public static List<List<String>> load(String filename) throws IOException {		// gibt jede Zeile als Liste an Strings zurück
		List<List<String>> result = new ArrayList<List<String>>();

		BufferedReader br = new BufferedReader(new FileReader(filename));

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			result.add(Arrays.asList(line.split("\\|")));
		}
		br.close();
		return result;
	}

	public Set<String> getZertifikate(String Studiengang){
		Set<String> zertifikate = new HashSet<String>();

	}
	public static void main(String[] args) {
		try {
			Modulbeschreibungen mb = new Modulbeschreibungen(
					"/Users/oleksandrsavcenko/Workspace/Java/Praktikum_Programmieren2/Aufgabe_4/Modulbeschreibungen");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
