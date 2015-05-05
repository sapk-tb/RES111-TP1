package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;

import java.util.*;

public abstract class Entity implements Runnable {

	private EventChannel ec;
	private Queue<Event> eventQueue;
	
	public void produce (Event e) {
		ec.dispatchEvent (e);
	}
	
	public Event consume() throws InterruptedException {	
		synchronized (eventQueue) {
			while (eventQueue.isEmpty())
				try {
					eventQueue.wait();
				}
				catch (InterruptedException ie) {
					throw ie;
				}
			return eventQueue.remove();
		}
	}
	
	public void addEvent (Event e) {
		synchronized (eventQueue) {
			eventQueue.add (e);
			eventQueue.notifyAll ();
		}
		
	}
	public Entity (EventChannel ec, Class <?> produced[], Class <?> consumed[]) {
		this.ec = ec;
		this.ec.addEntity(this, produced, consumed);
		eventQueue = new LinkedList <Event> ();
	}
}
