package net.sf.josceleton.prototype.console.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.josceleton.commons.util.MathUtil;
import net.sf.josceleton.commons.util.MathUtil.StartEnd;

class Location2DView extends JPanel {
	
	private static final long serialVersionUID = -2760226484069754420L;
	
	private static final Dimension SIZE = new Dimension(180, 120);
	
	
	private final JLabel lbl = new JLabel("T");
	
	
	Location2DView() {
		this.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		this.setSize(SIZE);
		this.setMaximumSize(SIZE);
		this.setPreferredSize(SIZE);
		
		this.add(this.lbl);
	}
	
	public void updateXz(final int x, int y) {
		final int x2 = MathUtil.relativateTo(new StartEnd(20 /* sogar 0*/, 90), x, new StartEnd(0, this.getWidth()));
		final int y2 = MathUtil.relativateTo(new StartEnd(120 /* sogar 60*/, 250 /* sogar 270*/), y, new StartEnd(0, this.getHeight()));
		this.lbl.setBounds(x2, y2, 10, 10);
	}
}
