package com.manage.station;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageStationApplication {

	public static void main(String[] args) {

		try {
			MqttClient client = new MqttClient(
					"tcp://127.0.0.1:1883", //URI
					MqttClient.generateClientId(), //ClientId
					new MemoryPersistence());
			client.connect();
			if (client.isConnected()){
				System.out.print("connected");
			}else{
				System.out.print("connect fail");
			}

		}catch (Exception e){
			System.out.print("Exception");
		}

		SpringApplication.run(ManageStationApplication.class, args);
	}

}
