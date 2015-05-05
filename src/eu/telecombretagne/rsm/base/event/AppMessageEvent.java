package eu.telecombretagne.rsm.base.event;


public class AppMessageEvent extends Event {
	
	private static final long serialVersionUID = 7309032197593690995L;

		private String body_;
		
		public AppMessageEvent (String b) {
			body_ = b;
		}
		public String getBody () {
			return body_;
		}
}
