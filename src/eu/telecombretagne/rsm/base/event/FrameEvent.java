package eu.telecombretagne.rsm.base.event;

public class FrameEvent extends Event {
	
	private static final long serialVersionUID = 7300532197983690995L;

	private String body;
	private int num;
	
	public FrameEvent (String b, int numero) {
		body = b;
		num = numero;
	}
	
	public String getBody () {
		return body;
	}
	
	public int getNum () {
		return num;
	}

}
