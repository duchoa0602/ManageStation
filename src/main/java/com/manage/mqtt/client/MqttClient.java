package com.manage.mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttMessage;
public class MqttClient {
    public static MqttClient client;
    public static final String url = "tcp://127.0.0.1:1883";
    public static final String user = "jhqlhwjr";
    public static final String pass = "26Yvz_31t907";
    public static final String topicSub = "TopicTest";
    public static final String topicPud = "cmd/355684292";
    public static String request;
    public static boolean checkConnectSv = false;


    public void connect(String url){

        try {
            client.connect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribeToTopic(final String subscriptionTopic) {
        try{
        } catch (Exception ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    public void publish(String mess, String topic){
        String payload = mess;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(true);
//            client.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseData(String url){

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
