package net.sf.josceleton.prototype.midiroute.logic;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 2616529626299414181L;
	
	private InvalidInputException(String msg) {
		super(msg);
	}

	public static InvalidInputException newInvalidMidiPort(String port) {
		return new InvalidInputException("Can not find MIDI device with port '" + port + "'!");
	}
	public static InvalidInputException newInvalidNumber(String raw) {
		return new InvalidInputException("Invalid number '" + raw + "'!");
	}

}
