package net.sf.josceleton.playground.motion.app2.framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class StyleConstants {
	
	private static Font FONT_REGULAR = new Font("monaco", Font.PLAIN, 36);
	private static Font FONT_COMMENT = new Font("monaco", Font.PLAIN, 16);

	public static void setRegularFont(Graphics2D g) {
		g.setFont(FONT_REGULAR);
		g.setColor(Color.WHITE);
	}

	public static void setCommentFont(Graphics2D g) {
		g.setFont(FONT_COMMENT);
		g.setColor(Color.GRAY);
	}
	
}
