package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;


public class AppEmitterEntity extends Entity {

	static private Class<?> [] producedEvents = {AppMessageEvent.class};
	static private Class<?> [] consumedEvents = {AppMessageReturnEvent.class};
	
	public AppEmitterEntity (EventChannel ec) {
		super (ec, producedEvents, consumedEvents);
	}
	
	public void run () {
		
		int i = 0;
		while (true) {
			try {
				Thread.sleep (1000);
			}
			catch (InterruptedException ie) {
				System.out.println ("AppEmitterEntity" + ie);
				System.exit(1);
			}
			AppMessageEvent ame = new AppMessageEvent ("Applicative message: " + i++);
			System.out.println ("AppEmitterEntity: sending applicative message:" + ame.getBody ());

			this.produce (ame);
			try {
				Event e = this.consume ();
				if (e.getClass () == AppMessageReturnEvent.class) {
					System.out.println ("AppEmitterEntity: receiving ack for applicative message");
				}
				else
					System.out.println("BUG: AppEmitterEntity receiving " + e.getClass ());
			}
			catch (InterruptedException ie) {
				System.out.println (ie);
				System.exit(1);
			}
		}
	}
}

