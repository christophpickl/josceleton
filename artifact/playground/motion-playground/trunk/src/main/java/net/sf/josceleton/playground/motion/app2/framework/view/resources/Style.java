package net.sf.josceleton.playground.motion.app2.framework.view.resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.JLabel;

public class Style {

	// TODO have a look at java 2d text advanced rendering: http://java.sun.com/developer/onlineTraining/Media/2DText/style.html

	public enum Text {

		MAIN() { @Override public void on(Graphics2D g) {
			g.setFont(Style.FONT_REGULAR);
			g.setColor(Style.TEXT_PRIMARY);
		}}
		,
		BUTTON_LABEL() { @Override public void on(Graphics2D g) {
			g.setFont(Style.FONT_REGULAR);
			g.setColor(Style.TEXT_PRIMARY);
		}},
		H1() { @Override public void on(Graphics2D g) {
			g.setFont(Style.FONT_COMMENT);
			g.setColor(Style.TEXT_PRIMARY);
		}},
		COMMENT() { @Override public void on(Graphics2D g) {
			g.setFont(Style.FONT_COMMENT);
			g.setColor(Style.TEXT_SECONDARY);
		}},
		TIME_INDICATOR() { @Override public void on(Graphics2D g) {
			g.setFont(Style.FONT_TIME_INDICATOR);
			g.setColor(Style.TEXT_PRIMARY);
		}},
		BOXER_BUBBLE() { @Override public void on(Graphics2D g) {
			g.setFont(Style.FONT_BOXER_BUBBLE);
			g.setColor(Color.RED);
		}}
		;
		
		public abstract void on(Graphics2D g);

		public Dimension calculateSize(String label, Graphics2D g) {
			final FontMetrics metrics = g.getFontMetrics();
			return new Dimension(metrics.stringWidth(label), metrics.getAscent());
		}
	}
	public enum Shape {
		BUTTON_BOX_BG() { @Override public void on(Graphics2D g) {
			g.setPaint(Style.BACKGROUND_SECONDARY);
		}},
		BUTTON_BOX_LINE() { @Override public void on(Graphics2D g) {
			g.setColor(Style.LINE_PRIMARY);
		}}
		;
		public abstract void on(Graphics2D g);
	}
	public static Color TEXT_PRIMARY = Color.WHITE;
	public static Color TEXT_SECONDARY = Color.GRAY;
	
	public static Color LINE_PRIMARY = new Color(0x222222);
	public static Color BACKGROUND_SECONDARY = new Color(0x111111);
	
	private static int FONT_REGULAR_SIZE = 36;
	public static Font FONT_REGULAR = new Font("monaco", Font.PLAIN, FONT_REGULAR_SIZE);
	public static Font FONT_TIME_INDICATOR = new Font("monaco", Font.ITALIC, 24);
	public static Font FONT_COMMENT = new Font("monaco", Font.PLAIN, 16);
	public static Font FONT_BOXER_BUBBLE = new Font("Marker Felt", Font.ITALIC, 48);

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
