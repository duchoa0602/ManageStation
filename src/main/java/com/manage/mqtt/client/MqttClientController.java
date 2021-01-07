package com.manage.mqtt.client;

import com.manage.station.entity.DeviceEntity;
import com.manage.station.entity.ValueEntity;
import com.manage.station.service.UserService;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class MqttClientController {
    public static MqttClient client;
    public static final String url = "tcp://127.0.0.1:1883";
    public static final String user = "username";
    public static final String pass = "pass";
    public static final String topicSub = "value";
    public static final String topicPud = "control";
    public static boolean checkConnect;
    private static UserService userService;

    public MqttClientController(UserService userService) {
        this.userService = userService;
    }

    public static boolean connect(){
        checkConnect = false;
        try {
            // The domain name to access IoT Platform.
            String clientId = UUID.randomUUID().toString();
            // Paho MQTT client
            client = new MqttClient(url, clientId);
            // Connection parameters for Paho MQTT.
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(180);
            connOpts.setUserName(user);
            connOpts.setPassword(pass.toCharArray());
            client.connect(connOpts);
            checkConnect = true;
            System.out.println("Broker: "+ url +" Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkConnect;
    }

    static class MqttPostPropertyMessageListener implements IMqttMessageListener {
        private final UserService userService;

        MqttPostPropertyMessageListener(UserService userService) {
            this.userService = userService;
        }

        @Override
        public void messageArrived(String var1, MqttMessage var2) throws Exception {
            System.out.println("topic : " + var1 + " reply payload: " + var2.toString());
            if (var1.equals(topicSub)){
                try {
                    JSONObject json = new JSONObject(var2.toString());
                    DeviceEntity deviceEntity = userService.getDeviceById(json.getLong("id"));
                    if (deviceEntity != null){
                        ValueEntity valueEntity = new ValueEntity();
                        valueEntity.setDevice(deviceEntity);
                        Date createdDate = null;
                        try {
                            createdDate = new Date(json.getLong("createdDate"));
                        }catch (Exception e){
                            createdDate = new Date();
                        }
                        valueEntity.setCreatedDate(createdDate);
                        valueEntity.setVal(json.getDouble("value"));
                        userService.saveValue(valueEntity);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void subscribe(String topic) {
        try{
            client.subscribe(topic, new MqttPostPropertyMessageListener(userService));
            System.out.println("Subscribe to topic: " + topic);
        } catch (Exception ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public static void publish(String content, String topic){
        try {
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(0);
            client.publish(topic, message);
            System.out.println("Publish message: " + content + " to topic: " + topic + " success");
        } catch (Exception e) {
            System.err.println("Exception whilst publishing");
            e.printStackTrace();
        }
    }
}
