package com.uber.config.service;

import java.util.List;
import java.util.function.Consumer;

public class Event {
	public Event(String bucket, List<Property> properties2) {
		this.key = bucket;
		this.properties = properties2;
	}
	List<Property> properties;
	String key;
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("Recieved event for the bucket : " +key);
		properties.stream().forEach(new Consumer<Property>() {

			@Override
			public void accept(Property t) {
				str.append("Property Key :  " + t.key + "\n");
				
			}
		});
		return str.toString();
	}
}