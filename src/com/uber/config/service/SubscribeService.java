package com.uber.config.service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class SubscribeService {
	
	BlockingQueue<Event> queue = null;
	
	Map<String,List<EventListener>> subscribers = null;
	
	public SubscribeService() {
		queue = new LinkedBlockingQueue<>(10);
		subscribers = new ConcurrentHashMap<>();
		initThread();
	}
	
	private void initThread() {
		
		Thread consumer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					Event event = null;
					try {
						event = queue.take();
						List<EventListener> listeners = subscribers.get(event.key);
						if(listeners!=null) {
							for(EventListener list : listeners) {
								list.handleEvent(event);
							}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
				
			}
		});
		consumer.start();
		
	}
	
	public void addSubscribers(String bucket, EventListener listener) {
		List<EventListener> lists = null;
		if(subscribers.containsKey(bucket)) {
			lists = subscribers.get(bucket);
			lists.add(listener);
		} else{
			lists = new CopyOnWriteArrayList<>();
			lists.add(listener);
		}
		subscribers.put(bucket, lists);
	}

	public void publishEvent(Event event) {
		queue.offer(event);
		
	}
	
	

}


