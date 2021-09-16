package sample;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;


public class Controller {

    private static String userCity = "";


    @FXML
    private TextField textFieldCity;

    @FXML
    private Button buttonGetData;

    @FXML
    private Button buttonMoscow;

    @FXML
    private Button buttonSpb;

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

        buttonGetData.setOnAction(event -> getWeather(textFieldCity.getText()));
        buttonMoscow.setOnAction(event -> getWeather(buttonMoscow.getText()));
        buttonSpb.setOnAction(event -> getWeather(buttonSpb.getText()));

    }


    private void getWeather(String city) {
        userCity = city.trim();

        String output = getUrlContent(
                "http://api.openweathermap.org/data/2.5/weather?q=" +
                        userCity + "&appid=" + getAppID() + "&units=metric");

        if (!output.isEmpty()) {
            JSONObject obj = new JSONObject(output);
            temp_info.setText("Температура: " + Math.round(obj.getJSONObject("main").getDouble("temp")) + "\u00B0");
            temp_feels.setText("Ощущается: " + Math.round(obj.getJSONObject("main").getDouble("feels_like"))+ "\u00B0");
            temp_max.setText("Максимум: " + Math.round(obj.getJSONObject("main").getDouble("temp_max"))+ "\u00B0");
            temp_min.setText("Минимум: " + Math.round(obj.getJSONObject("main").getDouble("temp_min"))+ "\u00B0");
            pressure.setText("Давление: " + Math.round((obj.getJSONObject("main").getDouble("pressure")/133*100))+" мм.рт.ст.");
        }
    }

    private static String getAppID() {
        StringBuilder appID = new StringBuilder();
        BufferedReader reader = null;
        String path = new File("").getAbsolutePath();

        try {
            reader = new BufferedReader(new FileReader(new File(path + "\\src\\sample\\AppID")));

            String line;
            while ((line = reader.readLine()) != null) {
                appID.append(line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("File not found!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return appID.toString();
    }

    private static String getUrlContent(String urlAdress) {
        StringBuilder content = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Сity " + userCity + " not found!");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();

    }
}
