import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KlausurenServer{
	
	private static Map<String, ArrayList<Integer>> KlausurInfos = new HashMap<>();
	
	public static void main(String[] args) {
		ServerSocket serverSo = null;
		int port = Integer.valueOf(args[0]).intValue();
		try {
			serverSo = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
	
			try {
				Socket client = serverSo.accept();
				new KlausurenServerThread(client);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	

	
	public static ArrayList<Integer> putValue(String key, ArrayList<Integer> value) {
		
		return KlausurInfos.put(key, value);
		
	}

	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteValue(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getAllKlausuren() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean stopServer() {
		// TODO Auto-generated method stub
		return false;
	}

}
