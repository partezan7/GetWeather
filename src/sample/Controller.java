package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {


    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
       getData.setOnAction(event -> {
           String getUserCity=city.getText().trim();
           String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" +
                   getUserCity + "&appid=1042c83ed9be3c521686b02f843e2c82&units=metric");

           if (!output.isEmpty()){
               JSONObject obj = new JSONObject(output);
               temp_info.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
               temp_feels.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
               temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
               temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
               pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
           }
       });
    }

    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line=bufferedReader.readLine())!=null){
                content.append(line+"\n");
            }
            bufferedReader.close();
        }catch (Exception e){
            System.out.println("This city was not found!");
        }
        return content.toString();

    }
}
