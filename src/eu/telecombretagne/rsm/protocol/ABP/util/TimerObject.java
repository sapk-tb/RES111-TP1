package eu.telecombretagne.rsm.protocol.ABP.util;

public class TimerObject extends Thread {

	private Thread t_;
	private boolean canceled_;
	private int duration_;
	
	public TimerObject (Thread t, int duration) {
		canceled_ = false;
		t_ = t;
		duration_ = duration;
	}
	
	public void cancel () {
		canceled_ = true;
	}
	
	public void run () {
		try {
			Thread.sleep(duration_);
		}
		catch (InterruptedException ie) {
		}
		if (!canceled_)
			t_.interrupt();
	}
}
