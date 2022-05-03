import au.com.bytecode.opencsv.CSVWriter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Runner {
    public static void main(String[] args) throws IOException {

        final URL url;
        String request = "";

        String settings[] = new Settings().getSettings();

        try {
            url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + settings[1] + "&lon=" + settings[2] +
                    "&appid=" + settings[0] + "&lang=ru&units=metric&mode=xml");
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                final StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                request = content.toString();
            } catch (final Exception ex) {
                ex.printStackTrace();
                request = "";
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String district = "";

        String temperature = "";

        String humidity = "";

        String pressure = "";

        String windSpeed = "";

        String clouds = "";

        String visibility = "";

        String weather = "";

        Date currentDate = new Date();

        System.out.println("Сырые данные: \n" + request);

        XPathFactory xpathFactory = XPathFactory.newInstance();

        XPath xpath = xpathFactory.newXPath();

        System.out.println("Получено из XML: ");

        district = getData(request, xpath, "city/@name");
        temperature = getData(request, xpath, "temperature/@value");
        humidity = getData(request, xpath, "humidity/@value");
        pressure = getData(request, xpath, "pressure/@value");
        windSpeed = getData(request, xpath, "wind/speed/@value");
        clouds = getData(request, xpath, "clouds/@value");
        visibility = getData(request, xpath, "visibility/@value");
        weather = getData(request, xpath, "weather/@value");

        System.out.println("Текущее время: " + currentDate + "|" + "Район: " + district + "|" + "Температура: " + temperature + "|" + "Влажность(%): "
                + humidity + "|" + "Давление(гПа): " + pressure
                + "|" + "Скорость ветра(м/c): " + windSpeed + "|" + "Облачность(%): " + clouds + "|" + "Видимость(м): " + visibility + "|" + "Погода: " + weather);

        String record[] = new String[]{currentDate.toString(), district, temperature, humidity, pressure, windSpeed, clouds, visibility, weather};
        writeToCsv(record);

    }

    private static String getData(String request, XPath xpath, String attribute) {
        String dataValue = null;
        DocumentBuilder db = null;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            Document document = db.parse(new ByteArrayInputStream(request.getBytes("UTF-8")));
            try {
                XPathExpression xPathExpression = xpath.compile(
                        "/current/" + attribute
                );
                dataValue = (String) xPathExpression.evaluate(document, XPathConstants.STRING);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataValue;
    }

    private static void writeToCsv(String[] record) throws IOException {
        String csv = "weather.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        writer.writeNext(record);
        writer.close();
    }


}
