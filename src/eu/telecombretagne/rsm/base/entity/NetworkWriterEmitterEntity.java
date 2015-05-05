package eu.telecombretagne.rsm.base.entity;

import eu.telecombretagne.rsm.base.event.*;

public class NetworkWriterEmitterEntity extends NetworkWriterEntity {
	
	static private Class<?> [] producedEvents = {};
	static private Class<?> [] consumedEvents = {FrameEvent.class};
	
	public NetworkWriterEmitterEntity (EventChannel ec) {
		super (ec, "WRITER_EMITTER", producedEvents, consumedEvents);
	
 	}
}

