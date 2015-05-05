package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;


public class NetworkReaderReceiverEntity extends NetworkReaderEntity {
	
	static private Class<?> [] producedEvents = {FrameEvent.class};
	static private Class<?> [] consumedEvents = {};
	
	public NetworkReaderReceiverEntity (EventChannel ec) {
		super (ec, "READER_RECEIVER", producedEvents, consumedEvents);
 	}
	
}

