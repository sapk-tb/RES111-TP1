package eu.telecombretagne.rsm.base.event;

public class AlarmInitEvent extends Event {
	
	private static final long serialVersionUID = 7300532209193690995L;

	private int duration_;
	
	public AlarmInitEvent (int duration) {
		
		if ((duration != 0) && ( (duration < 10) || (duration > 10000)))
			duration_= 100;
		else duration_ = duration;
		
	}
	
	public int getDuration () {
		return duration_;
	}
}
