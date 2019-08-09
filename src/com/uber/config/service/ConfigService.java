package com.uber.config.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import com.uber.config.client.ConfigClient;

public class ConfigService {
	
	Map<String,List<Property>> configServerProps = null;
	SubscribeService service = new SubscribeService();
	
	private static ConfigService instance = new ConfigService();
	
	public static ConfigService getInstance() {
		return instance;
	}
	
	private ConfigService() {
		configServerProps = new ConcurrentHashMap<>();
	}
	
	public List<Property> put(String bucket,List<Property> properties ) {
		List<Property> list = null;
		if(configServerProps.containsKey(bucket)) {
			list = configServerProps.get(bucket);
			list.addAll(properties);
		}else {
			list = new CopyOnWriteArrayList<>();
			list.addAll(properties);
			configServerProps.put(bucket, list);
		}
		service.publishEvent(new Event(bucket,properties));
		return properties;
	}
	
	public List<Property> get(String bucket) {
		if(configServerProps.containsKey(bucket)) {
			return configServerProps.get(bucket);
		}
		return null;
	}

	public  void subscriberClients(String bucket, EventListener client) {
		service.addSubscribers(bucket, client);
		
	}
	

}
