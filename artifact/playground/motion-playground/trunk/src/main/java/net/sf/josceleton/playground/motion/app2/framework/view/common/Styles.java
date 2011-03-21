package net.sf.josceleton.playground.motion.app2.framework.view.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class Styles {
	
	public static Color TEXT_PRIMARY = Color.WHITE;
	public static Color TEXT_SECONDARY = Color.GRAY;
	
	public static Color LINE_PRIMARY = new Color(0x222222);
	public static Color BACKGROUND_SECONDARY = new Color(0x111111);
	
	public static Font FONT_REGULAR = new Font("monaco", Font.PLAIN, 36);
	public static Font FONT_COMMENT = new Font("monaco", Font.PLAIN, 16);

	public static void setRegularFont(Graphics2D g) {
		g.setFont(FONT_REGULAR);
		g.setColor(TEXT_PRIMARY);
	}

	public static void setCommentFont(Graphics2D g) {
		g.setFont(FONT_COMMENT);
		g.setColor(TEXT_SECONDARY);
	}

	public static Font styleAsComment(JLabel label) {
		label.setFont(FONT_COMMENT);
		label.setForeground(TEXT_SECONDARY);
		return label.getFont();
	}
	
}
