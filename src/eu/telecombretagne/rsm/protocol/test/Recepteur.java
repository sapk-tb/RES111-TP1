package eu.telecombretagne.rsm.protocol.test;

import eu.telecombretagne.rsm.base.entity.AltBitReceiverEntity;
import eu.telecombretagne.rsm.base.entity.AppReceiverEntity;
import eu.telecombretagne.rsm.base.entity.NetworkReaderReceiverEntity;
import eu.telecombretagne.rsm.base.entity.NetworkWriterReceiverEntity;
import eu.telecombretagne.rsm.base.event.*;

public class Recepteur {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		EventChannel ec = new EventChannel ();
		
		new Thread (new NetworkReaderReceiverEntity (ec)).start ();

		new Thread (new NetworkWriterReceiverEntity (ec)).start ();
		
		try {
			Thread.sleep (10);
		}
		catch (InterruptedException ie) {
			System.out.println("Emetteur Main: " + ie);
			System.exit(1);
		}
		
		new Thread (new AppReceiverEntity (ec)).start();
		
		new Thread (new AltBitReceiverEntity (ec)).start();
		
		
	}

}
