package eu.telecombretagne.rsm.base.event;

public class AckEvent extends Event {

	private static final long serialVersionUID = 7300532197593690995L;

	private int i_;
	
	public AckEvent (int i) {
		i_ = i;
	}
	
	public int getNum () {
		return i_;
	}
}
