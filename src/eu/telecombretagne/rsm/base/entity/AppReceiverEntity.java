package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;


public class AppReceiverEntity extends Entity implements Runnable {

	static private Class<?> [] producedEvents = {};
	static private Class<?> [] consumedEvents = {AppMessageEvent.class};
	
	public AppReceiverEntity (EventChannel ec) {
		super (ec, producedEvents, consumedEvents);
	}
	
	public void run () {
		
		while (true) {
			try {
				Event e = this.consume ();
				if (e.getClass () == AppMessageEvent.class) {
					AppMessageEvent ame = (AppMessageEvent) e;
					System.out.println ("AppReceiverEntity: receiving " + ame.getBody ());
				}
			}
			catch (InterruptedException ie) {
				System.out.println (ie);
				System.exit(1);
			}
		}
	}
}

