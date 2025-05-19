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

public class KlausurenServerThread extends Thread{
	Socket ClientSocket;
	
	public KlausurenServerThread(Socket client) {
		ClientSocket = client;
	}

	@Override
	public void run() {
		try {
			BufferedReader eingang = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
			PrintWriter serverAntwort = new PrintWriter(ClientSocket.getOutputStream(),true);
			ObjectOutputStream dateiSpeichern = new ObjectOutputStream(new FileOutputStream("/Aufgabe_5/src/KlausurenInformation"));
				
			String anfrage;
			String methode = "";
			String key = "";
			ArrayList<Integer> value = new ArrayList<>();
			
			StringBuilder antwort = new StringBuilder();
			while((anfrage = eingang.readLine()) != null) {
				methode = anfrage.split(" ")[0];
				key = anfrage.split(" ")[1];
				for(String s : anfrage.split(" ")[2].replaceAll("[", "").replaceAll("]", "").split(","))
					value.add(Integer.parseInt(s));
				
			}
			switch (methode){
				case "PUT": 
					System.out.println("PUT");
					antwort.append(KlausurenServer.putValue(key, value));
				case "GET":
					
				case "DEL":
					
				case "GETALL":
					
				case "STOP":
					
			}
			dateiSpeichern.writeObject(KlausurenServer.getKlausurenInfor());
			serverAntwort.println(antwort.toString());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}
