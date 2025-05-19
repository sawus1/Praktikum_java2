import java.util.ArrayList;

public interface IKlausurenServer {
	public static ArrayList<Integer> putValue(String key, ArrayList<Integer> value) {
		return null;
	}
	public String getValue(String key);
	public boolean deleteValue(String key);
	public String getAllKlausuren();
	public boolean stopServer();
}
