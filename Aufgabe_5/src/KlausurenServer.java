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
import java.util.stream.Collectors;

public class KlausurenServer {

	private static Map<String, ArrayList<Integer>> klausurInfos = new HashMap<>();
	private static boolean run = true;
	private ServerSocket socket;
	private static File saveFile = new File("/Users/oleksandrsavcenko/Workspace/Java/Praktikum_java2/Aufgabe_5/src/KlausurenInformation");

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

		try {
			KlausurenServer serv = null;

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Geben Sie den Port ein");
			String port = br.readLine();
			int portnum = Integer.parseInt(port);
			serv = new KlausurenServer(portnum);
			System.out.println("Server läuft auf Port: " + port);
			br.close();

			while (run) {
				try (Socket client = serv.socket.accept();) {
					KlausurenServerThread t = new KlausurenServerThread(client, serv);
					t.start();
					//t.join();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saveData();
		}
	}

	public Map<String, ArrayList<Integer>> loadMap(File file) {
		if (!file.exists())
			return new HashMap<>();

		try (ObjectInputStream dateiIn = new ObjectInputStream(new FileInputStream(file));) {
			return (Map<String, ArrayList<Integer>>) dateiIn.readObject();
		} catch (Exception e) {
			System.err.println("Fehler beim Laden der Datei!");
			System.err.println("Es sind keine vorherigen Informationen vorhanden");
			return new HashMap<>();
		}
	}

	public Map<String, ArrayList<Integer>> getKlausurInfos() {
		return klausurInfos;
	}

	public synchronized String putValue(String key, ArrayList<Integer> value) {
		ArrayList<Integer> sortedValues = new ArrayList<>(new TreeSet(value));
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
		for (ArrayList<Integer> originalList : klausurInfos.values()) {
			boolean subset = false;
//				System.out.println("originalList: " + originalList);
			for (ArrayList<Integer> otherList : klausurInfos.values()) {
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

	private String listToString(List<Integer> list) {
		return list.toString().replaceAll("\\[|\\]| ", "");
	}

	private static void saveData() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
			out.writeObject(klausurInfos);
		} catch (IOException e) {
			System.err.println("Fehler beim Speichern: " + e.getMessage());
		}
	}
}
