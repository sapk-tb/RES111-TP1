package eu.telecombretagne.rsm.network;

import java.util.concurrent.*;
import java.io.*;
import java.net.*;


import eu.telecombretagne.rsm.base.event.Event;

public class WriterThread extends Thread {
	
	private Socket _s;
	private ArrayBlockingQueue<Event> _v;
	private ThreadStatus _ts;
	
	
	public WriterThread (Socket s, ArrayBlockingQueue<Event> v, ThreadStatus ts) {
		_v = v;
		_s = s;
		_ts = ts;
	}
	
	public void run () {
		
		try {
			ObjectOutputStream ois = new ObjectOutputStream (_s.getOutputStream ());
			while (true) {
				Object o = _v.take ();
			//	System.out.println ("Writing a " + o);
				ois.writeObject(o);
			}
		}
		catch (IOException ioe) {
			_ts.writerisOn = false; //
			_ts.readerisOn = false; //

		}
		catch (InterruptedException ie) {
			System.out.println (ie);
			System.exit(1);
		}
	}
		
}
