package eu.telecombretagne.rsm.network;

import java.util.concurrent.*;
import java.io.*;
import eu.telecombretagne.rsm.base.event.*;

public class ReaderThread extends Thread {
	
	// private Socket _s;
	private ArrayBlockingQueue<Event> _v;
	private ThreadStatus _ts;
	private ObjectInputStream _ois;
	private double _facteur;
	
	
	public ReaderThread (ArrayBlockingQueue<Event> v, ThreadStatus ts, ObjectInputStream ois, double proba) {
		_v = v;
		_ts = ts;
		_ois = ois;
		_facteur = proba;
		
	}
	
	public void run () {
		
		try {
			while (true) {
				Event e = (Event) _ois.readObject();
				double alea = Math.random ();
				if ( alea < _facteur)
					_v.put(e);
				else System.out.println ("Discarding message: " + e);
			}
			
		}
		catch (IOException ioe) {
			System.out.println ("Detecting the end of a process ...");
			_ts.readerisOn = false;
			_ts.writerisOn = false;

		}
		catch (ClassNotFoundException cnfe) {
			System.out.println (cnfe);
			System.exit(1);
		}
		catch (InterruptedException ie) {
			System.out.println (ie);
			System.exit(1);
		}
		
	}
}
