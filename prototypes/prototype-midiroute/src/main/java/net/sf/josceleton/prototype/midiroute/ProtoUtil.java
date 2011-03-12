package net.sf.josceleton.prototype.midiroute;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import net.pulseproject.commons.util.StringUtil;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.prototype.midiroute.logic.MidiMapping;

public class ProtoUtil {
	
	public static final int LOG_JOINT_EVERY = 10;
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss.S");

	private static final int MAX_CHARS = 6000; // 1 page approx 800
	private static final int CHARS_CUT_OFF_TO = 5000;
	
	private static JTextArea logField;
	
	private static int logCount = 0;
	
	
	public static void log(String msg) {
		if(logField == null) {
			return;
		}
		
		String date = FORMAT.format(new Date());
		if(date.length() == 11) {
			date = date.substring(0, date.length() - 2) + "0" + date.substring(date.length() - 2); // 12:18:09.91 ==> 12:18:09.091
		} else if(date.length() == 10) {
			date = date.substring(0, date.length() - 1) + "00" + date.substring(date.length() - 1); // 12:18:09.4 ==> 12:18:09.004
		}
		
		final String newMsg = "[" + date +"] " + msg ;
		String oldText;
		final String origText = logField.getText();
		if(origText.length() < MAX_CHARS) {
			oldText = origText;
		} else {
			oldText = origText.substring(0, CHARS_CUT_OFF_TO);
		}
		logField.setText(newMsg + "\n" + oldText);
		
		logCount++;
	}

	public static void setLogField(JTextArea field) {
		logField = field;
	}
	
	public static void handleException(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, e.getMessage(), "Josceleton Midi Route Error", JOptionPane.ERROR_MESSAGE);
		ProtoUtil.log("Stack trace:\n" + StringUtil.exceptionToString(e));
		ProtoUtil.log("Error: " + e.getMessage());
	}

	public static String fillString(String in, int len) {
		if(in.length() >= len) {
			return in;
		}
		return in + "        ".substring(0, (len - in.length()));
	}
	
	public static void clearLog() {
		logField.setText("");
	}

	public static BodyPart bodyPartByOsceletonId(String rawPart) {
		for (BodyPart part : Body.values()) {
			if(part.getOsceletonId().equals(rawPart)) {
				return part;
			}
		}
		throw new RuntimeException("unkown body part: " + rawPart);
	}
	

	public static int transformControllerValue(Coordinate coordinate, XyzDirection direction) {
		final float f = direction.extractValue(coordinate);
		
		float roughly127 = f * 120;
		int rounded127 = Math.round(roughly127);
		
		if(roughly127 < 0) roughly127 = 0;
		if(roughly127 > 127) roughly127 = 127;
		
		return rounded127;
	}

	public static String toString(final Collection<MidiMapping> maps) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("JointMidiMappings [\n");
		int i = 1;
		for (final MidiMapping map : maps) {
			if(i != 1) sb.append("\n");
			sb.append("  ").append(i).append(". ").append(map);
			i++;
		}
		sb.append("\n]");
		
		return sb.toString();
	}
}
