package main.java.ru.partezan7.controller;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.ru.partezan7.util.DBjdbc;
import org.json.JSONObject;


public class Controller {

    private static String cityName = "";
    private static int counter = 0;

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
        cityName = city.trim();
        counter++;

        String response = getUrlResponse(
                "http://api.openweathermap.org/data/2.5/weather?q=" +
                        cityName + "&appid=" + getAppID() + "&units=metric");

        if (!response.isEmpty()) {
            JSONObject obj = new JSONObject(response);

            temp_info.setText("Температура: " + Math.round(obj.getJSONObject("main").getDouble("temp")) + "\u00B0");
            temp_feels.setText("Ощущается: " + Math.round(obj.getJSONObject("main").getDouble("feels_like")) + "\u00B0");
            temp_max.setText("Максимум: " + Math.round(obj.getJSONObject("main").getDouble("temp_max")) + "\u00B0");
            temp_min.setText("Минимум: " + Math.round(obj.getJSONObject("main").getDouble("temp_min")) + "\u00B0");
            pressure.setText("Давление: " + Math.round((obj.getJSONObject("main").getDouble("pressure") / 133 * 100)) + " мм.рт.ст.");

            DBjdbc dbJDBC = new DBjdbc();
            Connection connection = dbJDBC.getConnection();

            String createTableString = "create table if not exists weatherlog (" +
                    " weatherlog_id int not null AUTO_INCREMENT," +
                    " city varchar(30) not null," +
                    " temp FLOAT not null," +
                    " pressure FLOAT not null," +
                    " date DATE not null," +
                    " primary key (weatherlog_ID)" +
                    ")" +
                    " ENGINE=MyISAM  DEFAULT  charset=utf8";


            String INSERT_NEW = "INSERT INTO weatherlog VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = null;

            try {
                System.out.println(createTableString);
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableString);
                preparedStatement = connection.prepareStatement(INSERT_NEW);
                preparedStatement.setInt(1, counter);
                preparedStatement.setString(2, city);
                preparedStatement.setFloat(3, obj.getJSONObject("main").getFloat("temp"));
                preparedStatement.setFloat(4, obj.getJSONObject("main").getFloat("pressure") / 133 * 100);
                preparedStatement.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));
                preparedStatement.execute();
            } catch (SQLException throwables) {
                System.out.println("SQL Exception!");
                throwables.printStackTrace();
            } finally {
                try {
                    dbJDBC.getConnection().close();
                } catch (SQLException throwables) {
                    System.out.println("Failed to close connection!");
                    throwables.printStackTrace();
                }
            }
        }
    }

    private String getAppID() {

        StringBuilder appID = new StringBuilder();
        BufferedReader reader = null;
        String path = new File("").getAbsolutePath();

        try {
            reader = new BufferedReader(new FileReader(new File(path + "\\src\\main\\resources\\ru\\partezan7\\AppID")));
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


    private static String getUrlResponse(String urlRequest) {
        StringBuilder urlResponse = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(urlRequest);
            URLConnection urlConnection = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                urlResponse.append(line + "\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Сity " + cityName + " not found!");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return urlResponse.toString();
    }
}
