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
	Socket ClientSocket;

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
				PrintWriter serverAntwort = new PrintWriter(ClientSocket.getOutputStream(), true);
				ObjectOutputStream dateiSpeichern = new ObjectOutputStream(
						new FileOutputStream("/home/ino/Praktikum_java2/Aufgabe_5/src/KlausurenInformation"));) {
			String anfrage = eingang.readLine();
			StringBuilder antwort = new StringBuilder("");

			System.out.println(anfrage);
			String methode = anfrage.split(" ")[0];
			System.out.println("Methode: " + methode);
			
			switch (methode) {
			case "PUT":
				System.out.println("PUT");
				antwort.append("1 ");
				antwort.append(KlausurenServer.putValue(getKey(anfrage), getValue(anfrage)));
				break;
			case "GET":
				System.out.println(methode);
				antwort.append(KlausurenServer.getValue(getKey(anfrage)));
				break;
			case "DEL":
				System.out.println(methode);
				antwort.append(KlausurenServer.deleteValue(getKey(anfrage)));
				break;
			case "GETALL":
				System.out.println(methode);
			
				antwort.append(KlausurenServer.getAllKlausuren());
				break;
			case "STOP":
				System.out.println(methode);

				if (KlausurenServer.stopServer())
					antwort.append("1");
				else
					antwort.append("0");
				break;
			default:
				antwort.append("0 falsche Anfrage");
			}
			dateiSpeichern.writeObject(KlausurenServer.getKlausurInfos());
			serverAntwort.println(antwort.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
