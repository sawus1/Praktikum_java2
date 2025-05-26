import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class KlausurenServerThread extends Thread {
	private final Socket ClientSocket;

	public KlausurenServerThread(Socket client) {
		ClientSocket = client;
	}

	public String getKey(String line) {
		return line.split(" ")[1];
	}

	public ArrayList<Integer> getValue(String line) {
		ArrayList<Integer> value = new ArrayList<>();
		String[] stringValues = line.split(" ")[2].replaceAll("\\[|\\]| ", "").split(",");
		for (String s : stringValues)
			value.add(Integer.parseInt(s));

		return value;
	}

	@Override
	public void run() {
		try (BufferedReader eingang = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
				PrintWriter serverAntwort = new PrintWriter(ClientSocket.getOutputStream(), true);) {
			String anfrage = eingang.readLine();
//			StringBuilder antwort = new StringBuilder("");
			String antwort;

			System.out.println(anfrage);
			String methode = anfrage.split(" ")[0].toUpperCase();
			System.out.println("Methode: " + methode);

			switch (methode) {
			case "PUT":
//				System.out.println("PUT");
				antwort = (KlausurenServer.putValue(getKey(anfrage), getValue(anfrage)));
				break;
			case "GET":
//				System.out.println(methode);
				antwort = (KlausurenServer.getValue(getKey(anfrage)));
				break;
			case "DEL":
//				System.out.println(methode);
				antwort = (KlausurenServer.deleteValue(getKey(anfrage)));
				break;
			case "GETALL":
//				System.out.println(methode);

				antwort = (KlausurenServer.getAllKlausuren());
				break;
			case "STOP":
//				System.out.println(methode);

				KlausurenServer.stopServer();
				antwort = ("1");
				break;
			default:
				antwort = ("0");
			}
			System.out.println("Antwort: " + antwort);
			System.out.println();
			serverAntwort.println(antwort);

		} catch (IOException e) {
			System.err.println("Fehler beim bearbeiten des Threads: ");
			e.printStackTrace();
		}

	}

}
