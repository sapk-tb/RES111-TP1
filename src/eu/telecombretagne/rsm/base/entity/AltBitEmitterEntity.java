package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;
import eu.telecombretagne.rsm.protocol.ABP.util.*;

public class AltBitEmitterEntity extends Entity {

	static private Class<?> [] producedEvents = {AlarmInitEvent.class,
												FrameEvent.class,
												AppMessageReturnEvent.class};
	static private Class<?> [] consumedEvents = {AppMessageEvent.class, 
												TimerExpiredEvent.class, 
												AckEvent.class};
	private Automata automate;
	
	public AltBitEmitterEntity (EventChannel ec) {
		super (ec, producedEvents, consumedEvents);
		automate = new Automata (AutomataState.REPOS0);
	}
	
	public void run () {
		
		System.out.println ("AlternatingBitEmitter is running ...");
		FrameEvent current = null;
		while (true) {
			Event e = null;
			try {
				e = this.consume ();
			}
			catch (InterruptedException ie) {
				System.out.println ("Fatal Error during event consumption " + ie);
				System.exit(0);
			}
			switch (automate.getState ()) {
			
			case REPOS0: 
				if (e.getClass () == AppMessageEvent.class) {
					AppMessageEvent ame = (AppMessageEvent) e;
					current = new FrameEvent (ame.getBody (), 0);
					this.produce (current);
					this.produce (new AlarmInitEvent (500));
					automate.setState(AutomataState.ATTENTE_ACK0);
				}
				break;

			case REPOS1: 
				if (e.getClass () == AppMessageEvent.class) {
					AppMessageEvent ame = (AppMessageEvent) e;
					current = new FrameEvent (ame.getBody (), 1);
					this.produce (current);
					this.produce (new AlarmInitEvent (500));
					automate.setState(AutomataState.ATTENTE_ACK1);
				}
				break;
			case ATTENTE_ACK0: 
				if (e.getClass () == AckEvent.class ) {
					AckEvent ack = (AckEvent) e;
					if(ack.getNum() == 0){
						this.produce(new AppMessageReturnEvent());
						automate.setState(AutomataState.REPOS1);
					}else{
						this.produce (current);
						this.produce (new AlarmInitEvent (500));
					}
				}else if(e.getClass () == TimerExpiredEvent.class){
					this.produce (current);
					this.produce (new AlarmInitEvent (500));
				}
				break;
			case ATTENTE_ACK1: 
				if (e.getClass () == AckEvent.class ) {
					AckEvent ack = (AckEvent) e;
					if(ack.getNum() == 1){
						this.produce(new AppMessageReturnEvent());
						automate.setState(AutomataState.REPOS0);
					}else{
						this.produce (current);
						this.produce (new AlarmInitEvent (500));
					}
				}else if(e.getClass () == TimerExpiredEvent.class){
					this.produce (current);
					this.produce (new AlarmInitEvent (500));
				}
				break;
			
				
			}
		}
	}
}

