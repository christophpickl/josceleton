package net.sf.josceleton.prototype.midiroute.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import net.pulseproject.commons.util.StringUtil;

public class ProtoUtil {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss.S");

	private static final int MAX_CHARS = 6000; // 1 page approx 800
	private static final int CHARS_CUT_OFF_TO = 5000;
	
	private static JTextArea logField;
	
	public static final int LOG_JOINT_EVERY = 10;
	private static int logCount = 0;
	
	public static void log(String msg) {
		if(logField == null) {
			return;
		}
		
		String date = FORMAT.format(new Date());
		if(date.length() == 11) {
			// 12:18:09.91 ==> 12:18:09.091
			date = date.substring(0, date.length() - 2) + "0" + date.substring(date.length() - 2);
		} else if(date.length() == 10) {
			// 12:18:09.4 ==> 12:18:09.004
			date = date.substring(0, date.length() - 1) + "00" + date.substring(date.length() - 1);
		}
		
		final String newMsg = "[" + date +"] " + msg ;
		String oldText;
		final String origText = logField.getText();
		if(origText.length() < MAX_CHARS) {
			oldText = origText;
		} else {
			oldText = origText.substring(0, CHARS_CUT_OFF_TO);
//			int endline = oldText.indexOf("\n");
//			if(endline != -1) {
//				oldText = oldText.substring(endline);
//			}
		}
		logField.setText(
			newMsg +
			"\n" + 
//			(origText.isEmpty() ? "" : "\n") +
			oldText
		);
		
		logCount++;
	}

	public static void setLogField(JTextArea field) {
		logField = field;
	}
	
	public static void handleException(Exception e) {
		e.printStackTrace();
		ProtoUtil.log("Error: " + e.getMessage());
		ProtoUtil.log("Stack trace:\n" + StringUtil.exceptionToString(e));
		JOptionPane.showMessageDialog(null, e.getMessage(), "Josceleton Midi Route Error", JOptionPane.ERROR_MESSAGE);
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
}
