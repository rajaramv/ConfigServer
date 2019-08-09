

package com.uber.config.client;

import java.util.ArrayList;
import java.util.List;

import com.uber.config.service.ConfigService;
import com.uber.config.service.Property;

public class DriverProgram {
	
	public static void main(String[] args) throws InterruptedException {
		ConfigClient client = new ConfigClient();
		ConfigClient2 client2 = new ConfigClient2();
		
		List<Property> properties = new ArrayList<>();
		Property prop = new Property("uber", "bangalore");
		properties.add(prop);
		ConfigService.getInstance().subscriberClients("uber",client2);
		ConfigService.getInstance().subscriberClients("uber2",client);
		//ConfigService.getInstance().subscriberClients("uber",client);
		Thread th1 = new Thread( new Runnable() {
			public void run() {
				for(int i=0; i < 2; i ++) {
					List<Property> properties = new ArrayList<>();
					Property prop = new Property("uber"+i+Thread.currentThread().getName(), "bangalore");
					properties.add(prop);
					ConfigService.getInstance().put("uber", properties);
				}
			}
			
		});
		
		Thread th2 = new Thread( new Runnable() {
			public void run() {
				for(int i=0; i < 2; i ++) {
					List<Property> properties = new ArrayList<>();
					Property prop = new Property("uber"+i+Thread.currentThread().getName(), "bangalore");
					properties.add(prop);
					ConfigService.getInstance().put("uber2", properties);
				}
			}
			
		});
		
		th1.start();
		th2.start();
		th1.join();
		th2.join();
		
		
	}

}
