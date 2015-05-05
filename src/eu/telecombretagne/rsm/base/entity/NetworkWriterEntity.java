package eu.telecombretagne.rsm.base.entity;

import static eu.telecombretagne.rsm.network.NETWORKPORT.NETWORKPORT;

import java.io.*;
import java.net.*;

import eu.telecombretagne.rsm.base.event.*;


public class NetworkWriterEntity extends Entity {
	
	private Socket socket;
	private ObjectOutputStream oos;
	
	public NetworkWriterEntity (EventChannel ec, String who, Class <?> produced[], Class <?> consumed[]) {
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
		
		while (true) {
			try {
				Event e = this.consume ();
				oos.writeObject (e);
				}

			catch (InterruptedException ie) {
				System.out.println (ie);
				System.exit(0);
			}
			catch (IOException ioe) {
				System.out.println (ioe);
				System.exit(0);
			}
		}
	}
}

