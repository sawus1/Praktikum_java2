import java.util.ArrayList;

public interface IKlausurenServer {
	public ArrayList<Integer> putValue(String key, ArrayList<Integer> value);
	public String getValue(String key);
	public boolean deleteValue(String key);
	public String getAllKlausuren();
	public boolean stopServer();
}
