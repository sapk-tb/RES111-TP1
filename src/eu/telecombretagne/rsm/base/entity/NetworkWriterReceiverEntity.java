package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;

public class NetworkWriterReceiverEntity extends NetworkWriterEntity {
	
	static private Class<?> [] producedEvents = {};
	static private Class<?> [] consumedEvents = {AckEvent.class};
	
	public NetworkWriterReceiverEntity (EventChannel ec) {
		super (ec, "WRITER_RECEIVER", producedEvents, consumedEvents);
 	}
}

