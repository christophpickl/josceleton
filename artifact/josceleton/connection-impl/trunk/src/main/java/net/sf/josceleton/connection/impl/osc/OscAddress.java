package net.sf.josceleton.connection.impl.osc;


public enum OscAddress {
	
	JOINT("/joint"),
	NEW_USER("/new_user"),
	NEW_SKEL("/new_skel"),
	LOST_USER("/lost_user");
	
	private final String address;
	
	private OscAddress(final String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
}
