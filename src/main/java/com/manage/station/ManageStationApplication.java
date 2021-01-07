package com.manage.station;

import com.manage.mqtt.client.MqttClientController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManageStationApplication {
	public static void main(String[] args) {
		if (MqttClientController.connect()){
			MqttClientController.subscribe(MqttClientController.topicSub);
		}
		SpringApplication.run(ManageStationApplication.class, args);
	}

}
