package com.uber.config.client;


import com.uber.config.service.Event;
import com.uber.config.service.EventListener;

public class ConfigClient implements EventListener{

	@Override
	public void handleEvent(Event event) {
		System.out.println(" Recieved event : " + event);
	}

}
