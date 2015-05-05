package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;


public class NetworkReaderEmitterEntity extends NetworkReaderEntity {
	
	static private Class<?> [] producedEvents = {AckEvent.class};
	static private Class<?> [] consumedEvents = {};
	
	public NetworkReaderEmitterEntity (EventChannel ec) {
		super (ec, "READER_EMITTER", producedEvents, consumedEvents);
	}
}

