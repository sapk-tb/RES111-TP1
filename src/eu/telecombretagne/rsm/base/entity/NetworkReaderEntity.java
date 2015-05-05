package eu.telecombretagne.rsm.base.entity;

import static eu.telecombretagne.rsm.network.NETWORKPORT.NETWORKPORT;

import java.io.*;
import java.net.*;
import eu.telecombretagne.rsm.base.event.*;


public class NetworkReaderEntity extends Entity {
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	
	public NetworkReaderEntity (EventChannel ec, String who, Class <?> produced[], Class <?> consumed[]) {
		
		super (ec, produced, consumed);
		try {
			socket = new Socket ("localhost", NETWORKPORT);
			oos = new ObjectOutputStream (socket.getOutputStream ());
			oos.writeObject(who);
		}
		catch (SocketException se) {
			System.out.println ("Socket Exception");
			System.exit(0);
		}
		catch (IOException ioe) {
			System.out.println ("IO Exception");
			System.exit(0);
		}
 	}
	
	public void run () {
		
		try {
			ois = new ObjectInputStream (socket.getInputStream ());
		}
		catch (IOException ie) {
			System.out.println (ie);
			System.exit (1);
		}
		while (true) {
			try {
				Event e = (Event) ois.readObject ();
			//	System.out.println ("Reading and producing " + e);
				produce (e);
			}
			catch (IOException ioe) {
				System.out.println (ioe);
				System.exit(1);
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println (cnfe);
				System.exit(1);
			}
		}
		
			
	}
}

