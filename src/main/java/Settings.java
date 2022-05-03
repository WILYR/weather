import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Settings {

    public InputStream propertiesStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");

    public String[] getSettings() throws IOException {
        String[] settings = new String[6];
        BufferedReader reader = new BufferedReader(new InputStreamReader(propertiesStream));
        String line;
        System.out.println("Загружаю настройки....");
        while ((line = reader.readLine()) != null) {
            if (line.contains("apikey=")) {
                settings[0] = line.replaceAll("apikey=", "");
                System.out.println("apikey=" + settings[0]);
            }
            if (line.contains("lat=")) {
                settings[1] = line.replaceAll("lat=", "");
                System.out.println("lat=" + settings[1]);
            }
            if (line.contains("lon=")) {
                settings[2] = line.replaceAll("lon=", "");
                System.out.println("lon=" + settings[2]);
            }
        }
        return settings;
    }
}