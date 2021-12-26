package main;

import lombok.extern.log4j.Log4j2;
import main.client.MqttClient;

@Log4j2
public class Main {

    private final static String URL = "url";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";

    public static void main (String[] args) {
       MqttClient.buildAndStart(URL, USERNAME, PASSWORD, true);
    }
}
