import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
			PrintWriter dateiSpeichern = new PrintWriter(new FileWriter("/Aufgabe_5/src/KlausurenInformation"));
				
			String anfrage;
			String methode = "";
			while((anfrage = eingang.readLine()) != null) {
				methode = anfrage.split(" ")[0];
			}
			switch (methode){
				case "PUT": 
					
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}
