package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;
import eu.telecombretagne.rsm.protocol.ABP.util.TimerObject;

public class TimerEntity extends Entity implements Runnable {

	static private Class<?> [] producedEvents = {TimerExpiredEvent.class};
	static private Class<?> [] consumedEvents = {AlarmInitEvent.class};
	
	public TimerEntity (EventChannel ec) {
		super (ec, producedEvents, consumedEvents);
	}
	
	public void run () {
		boolean TimerObjectRunning = false;
		TimerObject to = null;
		while (true) {
			try {
				Event e = this.consume ();
				if (e.getClass () == AlarmInitEvent.class) {
					// si c'est un init 0, il faut tuer (interrupt) le TimerObject si il existe 
					// sinon on cree un TimerObject.
					AlarmInitEvent aie = (AlarmInitEvent) e;
					if (aie.getDuration() == 0) {
						if (TimerObjectRunning) 
							to.cancel ();
						TimerObjectRunning = false;
					}
					else {
						if (TimerObjectRunning)
							to.cancel();
						to = new TimerObject (Thread.currentThread (), aie.getDuration ());
						to.start();
						TimerObjectRunning = true;
					}
				}
				else System.out.println ("TimerTask consumes " + e);
			}
			catch (InterruptedException ie) {
				//System.out.println ("Producing a TimerExpiredEvent");
				this.produce(new TimerExpiredEvent ());
			}
			
			}
		}
}

