import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IModulbeschreibungen {
    Set<String> getZertifikate(String studiengang);
    Set<String> getVerzahnteModule();
    int getAnzahlModule(String studiengang, Boolean pflicht);
    int getAnzahlVeranstaltungen(String studiengang, Boolean pflicht);
    Map<Integer, Integer> getECTS(String studiengang);
    Map<Integer, Integer> getSWS(String studiengang);
    List<String> getSortierteStudiengaenge();
    String getJSON(String studiengang);
}
