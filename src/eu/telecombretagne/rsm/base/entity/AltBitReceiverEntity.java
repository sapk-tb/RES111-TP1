package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;
import eu.telecombretagne.rsm.protocol.ABP.util.Automata;
import eu.telecombretagne.rsm.protocol.ABP.util.AutomataState;

public class AltBitReceiverEntity extends Entity {

	static private Class<?>[] producedEvents = { AppMessageEvent.class,
			AckEvent.class };
	static private Class<?>[] consumedEvents = { FrameEvent.class };
	private Automata automate;

	public AltBitReceiverEntity(EventChannel ec) {
		super(ec, producedEvents, consumedEvents);
		automate = new Automata(AutomataState.REPOS0);
	}

	public void run() {

		System.out.println("AlternatingBitReceiver is running ...");

		while (true) {
			Event e = null;
			try {
				e = this.consume();
			} catch (InterruptedException ie) {
				System.out
						.println("Fatal Error during event consumption " + ie);
				System.exit(0);
			}
			switch (automate.getState()) {

			case REPOS0:
				if (e.getClass() == FrameEvent.class) {
					System.out.println("Frame 0 received");
					FrameEvent fr = (FrameEvent) e;
					if (fr.getNum() == 0) {
						this.produce(new AckEvent(0));
						this.produce(new AppMessageEvent(fr.getBody()));
						automate.setState(AutomataState.REPOS1);
					} else {
						this.produce(new AckEvent(fr.getNum()));
					}
				}
				break;

			case REPOS1:
				if (e.getClass() == FrameEvent.class) {
					System.out.println("Frame 1 received");
					FrameEvent fr = (FrameEvent) e;
					if (fr.getNum() == 1) {
						this.produce(new AckEvent(1));
						this.produce(new AppMessageEvent(fr.getBody()));
						automate.setState(AutomataState.REPOS0);
					} else {
						this.produce(new AckEvent(fr.getNum()));
					}
				}
				break;
			}
		}

	}
}
