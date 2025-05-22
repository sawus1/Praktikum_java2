import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class KlausurenServer {

	private static Map<String, ArrayList<Integer>> KlausurInfos = new HashMap<>();
	private static boolean run = true;

	public static void main(String[] args) {

		int port = Integer.valueOf(args[0]).intValue();
		try (ServerSocket server = new ServerSocket(port)) {
			File KlausurInfoDatei = new File("/home/ino/Praktikum_java2/Aufgabe_5/src/KlausurenInformation");
			KlausurInfos = initilizeMap(KlausurInfoDatei);
			
			while (run) {
				Socket client = server.accept();
				KlausurenServerThread t = new KlausurenServerThread(client);
				t.start();
				t.join();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.exit(0);

	}
	
	public static Map<String, ArrayList<Integer>> initilizeMap(File file){
		

		try (ObjectInputStream datei = new ObjectInputStream(new FileInputStream(file));) {
			return  (Map<String, ArrayList<Integer>>) datei.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			return new HashMap<String, ArrayList<Integer>>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new HashMap<String, ArrayList<Integer>>();
	}

	public static Map<String, ArrayList<Integer>> getKlausurInfos() {
		return KlausurInfos;
	}

	public static ArrayList<Integer> putValue(String key, ArrayList<Integer> value) {
		ArrayList<Integer> sortedValues = new ArrayList<>(new TreeSet(value));
		return KlausurInfos.put(key, sortedValues);

	}

	public static String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean deleteValue(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	public static String getAllKlausuren() {
		if (KlausurInfos.size() > 0) {
//			System.out.println("full Map:" + KlausurInfos);
			StringBuilder allKlausuren = new StringBuilder("1 ");
			for (ArrayList<Integer> originalList : KlausurInfos.values()) {
				boolean subset = false;
//				System.out.println("originalList: " + originalList);
				for (ArrayList<Integer> otherList : KlausurInfos.values()) {
					if (originalList != otherList) {
						if (otherList.containsAll(originalList)) {
							subset = true;
						}
//						System.out.println("otherList: " + otherList + subset);
					}
				}
				if (!subset) {
					allKlausuren.append(originalList + ",");
//					System.out.println("All stand i:" + allKlausuren.toString());
				}

			}
			System.out.println(allKlausuren.toString());
			return allKlausuren.toString().substring(0,allKlausuren.length()-1);
		} else
			return "0";

	}

	public static boolean stopServer() {
		run = false;

		return true;
	}

}
