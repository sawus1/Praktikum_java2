import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KlausurenServer {

	private static Map<String, ArrayList<Integer>> KlausurInfos = new HashMap<>();
	private static boolean run = true;

	public static void main(String[] args) {

		int port = Integer.valueOf(args[0]).intValue();
		try (ServerSocket server = new ServerSocket(port)) {
			File KlausurInfoDatei = new File("/home/ino/Praktikum_java2/Aufgabe_5/src/KlausurenInformation");

			try (ObjectInputStream initialdatei = new ObjectInputStream(new FileInputStream(KlausurInfoDatei));) {
				KlausurInfos = (Map<String, ArrayList<Integer>>) initialdatei.readObject();
			}

			while (run) {
				Socket client = server.accept();
				KlausurenServerThread t = new KlausurenServerThread(client);
				t.start();
				t.join();
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);

	}

	public static Map<String, ArrayList<Integer>> getKlausurenInfor() {
		return KlausurInfos;
	}

	public static ArrayList<Integer> putValue(String key, ArrayList<Integer> value) {

		return KlausurInfos.put(key, value);

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
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean stopServer() {
		run = false;

		return true;
	}

}
