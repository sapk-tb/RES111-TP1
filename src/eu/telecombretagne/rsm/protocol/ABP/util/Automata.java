package eu.telecombretagne.rsm.protocol.ABP.util;


public class Automata {
	AutomataState state;

	public Automata(AutomataState state) {
		super();
		this.state = state;
	}

	public AutomataState getState() {
		return state;
	}

	public void setState(AutomataState state) {
		this.state = state;
	}
	
}
