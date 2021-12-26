package main.utils;

import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.util.*;

@Log4j2
public class MqttClientPersistenceImplementation implements MqttClientPersistence {

    private final Boolean activeLog;
    private final Map<String,MqttPersistable> map;
    private  Iterator iterator;
    private  Set entrySet;

    public MqttClientPersistenceImplementation(final Boolean activeLog) {
        this.activeLog = activeLog;
        this.map = new HashMap<>();
        this.entrySet = map.entrySet();
        this.iterator = entrySet.iterator();
    }

    @Override
    public void open(final String clientId,
                     final String serverURI) throws MqttPersistenceException {
          if (activeLog != null && activeLog) {
              log.info("##MqttClientPersistenceImplementation - Open => clientId: " + clientId + " - serverURI: " + serverURI);
          }
    }

    @Override
    public void close() throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - Close");
        }
        this.map.clear();
    }

    @Override
    public void put(final String key,
                    final MqttPersistable persistable) throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - Put => key: " +  key + " persistable: " + persistable.toString());
        }
        this.map.put(key, persistable);
        this.entrySet = map.entrySet();
        this.iterator = entrySet.iterator();
    }

    @Override
    public MqttPersistable get(final String key) throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - Get => key: " +  key);
        }
        return this.map.get(key);
    }

    @Override
    public void remove(final String key) throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - Remove => key: " +  key);
        }
        this.map.remove(key);
        this.entrySet = map.entrySet();
        this.iterator = entrySet.iterator();
    }

    @Override
    public Enumeration keys() throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - Keys");
        }
        return new Enumeration() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public Object nextElement() {
                return iterator.next();
            }
        };
    }

    @Override
    public void clear() throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - Clear");
        }
    }

    @Override
    public boolean containsKey(final String key) throws MqttPersistenceException {
        if (activeLog != null && activeLog) {
            log.info("##MqttClientPersistenceImplementation - containsKey => Key: " + key);
        }
        return map.containsKey(key);
    }
}
