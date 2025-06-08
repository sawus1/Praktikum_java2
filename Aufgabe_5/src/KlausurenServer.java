import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class KlausurenServer {

	private Map<String, List<Integer>> klausurInfos = new HashMap<>();
	private boolean run = true;
	private ServerSocket socket;
	private final static File saveFile = new File("/home/ino/Praktikum_java2/Aufgabe_5/src/KlausurenInformation");

	public KlausurenServer(int port) {
		try {
			socket = new ServerSocket(port);
			klausurInfos = loadMap(saveFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	public static void main(String[] args) {
		KlausurenServer serv = null;
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Geben Sie den Port ein");
			String port = br.readLine();
			int portnum = Integer.parseInt(port);
			serv = new KlausurenServer(portnum);
			System.out.println("Server läuft auf Port: " + port);
			br.close();

			while (serv.run) {
				
					Socket client = serv.socket.accept();
					KlausurenServerThread t = new KlausurenServerThread(client, serv);
					t.start();
				
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			serv.saveData();
		}
	}

	public Map<String, List<Integer>> loadMap(File file) {
		if (!file.exists())
			return new HashMap<>();

		try (ObjectInputStream dateiIn = new ObjectInputStream(new FileInputStream(file));) {
			return (Map<String, List<Integer>>) dateiIn.readObject();
		} catch (Exception e) {
			System.err.println("Fehler beim Laden der Datei!");
			System.err.println("Es sind keine vorherigen Informationen vorhanden");
			return new HashMap<>();
		}
	}

	public Map<String, List<Integer>> getKlausurInfos() {
		return klausurInfos;
	}

	public synchronized String putValue(String key, List<Integer> value) {
		List<Integer> sortedValues = new ArrayList<>(new TreeSet(value));
		List<Integer> oldValue = klausurInfos.put(key, sortedValues);
		return "1 " + (oldValue == null ? "" : listToString(oldValue));
	}

	public synchronized String getValue(String key) {
		List<Integer> value = klausurInfos.get(key);
		return value == null ? "0" : "1 " + listToString(value);
	}

	public synchronized String deleteValue(String key) {
		List<Integer> deletedValue = klausurInfos.remove(key);
		return deletedValue == null ? "0" : "1 " + listToString(deletedValue);

	}

	public synchronized String getAllKlausuren() {
		if (klausurInfos.isEmpty())
			return "0";

//			System.out.println("full Map:" + KlausurInfos);
		StringBuilder allKlausuren = new StringBuilder("1 ");
		for (List<Integer> originalList : klausurInfos.values()) {
			boolean subset = false;
//				System.out.println("originalList: " + originalList);
			for (List<Integer> otherList : klausurInfos.values()) {
				if (originalList != otherList) {
					if (otherList.containsAll(originalList)) {
						subset = true;
					}
//						System.out.println("otherList: " + otherList + subset);
				}
			}
			if (!subset) {
				allKlausuren.append(originalList.toString().replaceAll(" ", "") + ",");
//					System.out.println("All stand i:" + allKlausuren.toString());
			}

		}
//		System.out.println(allKlausuren.toString());
		return allKlausuren.toString().substring(0, allKlausuren.length() - 1);

	}

	public synchronized void stopServer() throws FileNotFoundException, IOException {
		run = false;
	}

	private static String listToString(List<Integer> list) {
		return list.toString().replaceAll("\\[|\\]| ", "");
	}

	private void saveData() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
			out.writeObject(klausurInfos);
		} catch (IOException e) {
			System.err.println("Fehler beim Speichern: " + e.getMessage());
		}
	}
}
