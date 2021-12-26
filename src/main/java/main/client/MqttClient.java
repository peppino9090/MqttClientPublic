package main.client;


import lombok.extern.log4j.Log4j2;
import main.utils.MqttClientPersistenceImplementation;
import main.utils.enums.SensorEnum;
import org.eclipse.paho.client.mqttv3.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Log4j2
public class MqttClient {



    private IMqttClient publisher = null;
    private MqttConnectOptions options;
    private List<SensorEnum> sensors;

    private MqttClient(final String url,
                       final Boolean start,
                       final String username,
                       final String password,
                       final Boolean verbose) {
        String publisherId = UUID.randomUUID().toString();
        try {
            this.publisher = new org.eclipse.paho.client.mqttv3.MqttClient(url,
                                                                           publisherId,
                                                                           new MqttClientPersistenceImplementation(verbose));
            this.options = new MqttConnectOptions();
            this.options.setAutomaticReconnect(true);
            this.options.setCleanSession(true);
            this.options.setConnectionTimeout(15);
            this.options.setKeepAliveInterval(30);
            if (username != null && password != null) {
                this.options.setUserName(username);
                this.options.setPassword(password.toCharArray());
            }
            this.sensors = SensorEnum.getSensorList();
            if (start) {
                this.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MqttClient build(final String url,
                                   final String username,
                                   final String password,
                                   final Boolean verbose) {
        return new MqttClient(url, false, username, password, verbose);
    }

    public static void buildAndStart(final String url,
                                     final String username,
                                     final String password,
                                     final Boolean verbose) {
        new MqttClient(url, true,  username, password, verbose);
    }

    public void start() throws Exception {

        this.publisher.connect(options);

        if(publisher.isConnected()) {
            log.info("Connected..");
        } else {
            log.error("Unable to connect");
            System.exit(0);
        }


        for (SensorEnum sensor : this.sensors) {
            log.info("##CONNECT SENSOR -> " + sensor.name);
            publisher.subscribe(sensor.address,1, (topic, message) -> {
                    log.info("##Address: " + topic);
                    log.info("##SensorName: " + sensor.name);
                    log.info("##EventDate: " + new Date());
                    log.info("##Message: " + message);
                    log.info("------------------------------------------------------------");
            });
        }
    }


}
