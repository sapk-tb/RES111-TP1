package eu.telecombretagne.rsm.protocol.test;

import eu.telecombretagne.rsm.base.entity.AltBitEmitterEntity;
import eu.telecombretagne.rsm.base.entity.AppEmitterEntity;
import eu.telecombretagne.rsm.base.entity.NetworkReaderEmitterEntity;
import eu.telecombretagne.rsm.base.entity.NetworkWriterEmitterEntity;
import eu.telecombretagne.rsm.base.entity.TimerEntity;
import eu.telecombretagne.rsm.base.event.*;

public class Emetteur {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		EventChannel ec = new EventChannel ();
		
		new Thread (new NetworkReaderEmitterEntity (ec)).start ();

		new Thread (new NetworkWriterEmitterEntity (ec)).start ();
	
		
		try {
			Thread.sleep (10);
		}
		catch (InterruptedException ie) {
			System.out.println("Emetteur Main: " + ie);
			System.exit(1);
		}
		
		new Thread (new TimerEntity (ec)).start();

		new Thread (new AltBitEmitterEntity (ec)).start();
		
		try {
			Thread.sleep (10);
		}
		catch (InterruptedException ie) {
			System.out.println("Emetteur Main: " + ie);
			System.exit(1);
		}
		
		new Thread (new AppEmitterEntity (ec)).start();
	

		
	}

}
