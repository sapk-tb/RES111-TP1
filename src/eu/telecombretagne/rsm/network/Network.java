package eu.telecombretagne.rsm.network;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;

import eu.telecombretagne.rsm.base.event.*;

import static eu.telecombretagne.rsm.network.NETWORKPORT.NETWORKPORT;

public class Network implements Runnable {
	
	private ServerSocket serverSocket_;
	private double proba_;
	
	private ArrayBlockingQueue<Event> _vA = new ArrayBlockingQueue<Event> (100);
	private ArrayBlockingQueue<Event> _vB = new ArrayBlockingQueue<Event> (100);

	
	public Network (double proba) {
		System.out.println ("Initializing the network ...");
		
		proba_ = proba;
		try {
			serverSocket_ = new ServerSocket (NETWORKPORT);
		}
		catch (BindException be) {
			System.out.println ("Please, kill any previous instance of Network before starting a new one");
			System.exit (1);
		}
		catch (IOException ioe) {
			System.out.println ("Exception " + ioe);
			System.exit(1);
		}
		System.out.println ("Network is up.");

	}
	
	public void run () {

		ThreadStatus emitter = new ThreadStatus ();
		ThreadStatus receiver = new ThreadStatus ();
		
		
		while (true) {

			try {
				Socket s = serverSocket_.accept();

				ObjectInputStream ois = new ObjectInputStream (s.getInputStream ());
				String message = (String) ois.readObject();
			//	System.out.println ("Received ..." + message);

				if (!(message.equals("READER_EMITTER") | message.equals("READER_RECEIVER") | message.equals("WRITER_EMITTER") | message.equals("WRITER_RECEIVER"))) {
					System.out.println ("Connection refused ...");
					ois.close();
					s.close ();
				}

				if (message.equals("READER_EMITTER") && (emitter.writerisOn == false)) {
					synchronized (emitter) {
					//	_vA.clear();
						Thread t = new WriterThread (s, _vA, emitter);
						emitter.t = t;
						emitter.writerisOn = true;
						emitter.t.start();
					}
				}
				if (message.equals("WRITER_RECEIVER") && (receiver.readerisOn == false)){
					synchronized (receiver) {
				//		_vA.clear();
						Thread t = new ReaderThread (_vA, receiver, ois, proba_);
						receiver.t = t;
						receiver.readerisOn = true;
						receiver.t.start();
					}
				}	
				if (message.equals("READER_RECEIVER") && (receiver.writerisOn == false)) {
					synchronized (receiver) {
					//	_vB.clear();
						Thread t = new WriterThread (s, _vB, receiver);
						receiver.t = t;
						receiver.writerisOn = true;
						receiver.t.start();
					}	
				}
				if (message.equals("WRITER_EMITTER") && (emitter.readerisOn == false)) {
					synchronized (emitter) {
				//		_vB.clear();
						Thread t = new ReaderThread (_vB, emitter, ois, proba_);
						emitter.t = t;
						emitter.readerisOn = true;
						emitter.t.start();
					}
				}		
			}
			catch (IOException ioe) {
				System.out.println (ioe);
				System.exit(1);
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println (cnfe);
				System.exit (1);
			}
		}
	}
		
	public static void main (String args[]) {
			
		double proba = 1;
		try {
			proba = Double.parseDouble(args[0]);
			if (proba < 0 || proba > 1)
				throw new NumberFormatException ();
		}
		catch (RuntimeException nfe) {
			System.out.println ("The argument is not a probability; the network will be run without any loss");
		}
		
		new Thread (new Network (proba)).start();
	}
}
