package eu.telecombretagne.rsm.base.event;

import java.util.HashSet;
import java.util.Hashtable;

import eu.telecombretagne.rsm.base.entity.*;

public class EventChannel {

	private Hashtable<Class<?>, HashSet<Entity>> consumersTable;
	private Hashtable<Class<?>, HashSet<Entity>> producersTable;
	
	public EventChannel () {
		consumersTable = new Hashtable <Class<?>, HashSet<Entity>> ();
		producersTable = new Hashtable <Class<?>, HashSet<Entity>> ();
	}
	
	public void addEntity (Entity e, Class <?> produced[], Class <?> consumed[]) {
		
		for (int i = 0; i < produced.length; i++) {
			if (!this.producersTable.containsKey(produced [i]))
				this.producersTable.put(produced[i], new HashSet<Entity> ());
			HashSet<Entity> hs = this.producersTable.get(produced[i]);
			hs.add(e);
			}
		for (int i = 0; i < consumed.length; i++) {
			if (!this.consumersTable.containsKey(consumed [i]))
				this.consumersTable.put(consumed[i], new HashSet<Entity> ());
			HashSet<Entity> hs = this.consumersTable.get(consumed[i]);
			hs.add(e);
			}	
	}	
	
	public synchronized void dispatchEvent (Event e) {
		
		if (!this.consumersTable.containsKey(e.getClass())) {
			System.out.println ("EventChannel: dispatchEvent failed ... for: " + e);
			System.exit(1);
		}
		HashSet<Entity> hs = this.consumersTable.get(e.getClass());
		
		for (Entity currentEntity:hs)
			currentEntity.addEvent(e);		
	}
	
}
