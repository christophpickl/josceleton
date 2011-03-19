package net.sf.josceleton.prototype.console.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.CoordinateUtil;
import net.sf.josceleton.core.api.entity.Direction;

public class JointPanel extends JPanel {
	
	private static final long serialVersionUID = 9095756747624408284L;

	private static final int COORDINATE_COLUMNS = 3;

	private static final Dimension SIZE = new Dimension(180, 120);
	
	private final String label;
	
	private final Map<Direction, JTextField> labelsByDirection;
	{
		final Map<Direction, JTextField> tmp = new HashMap<Direction, JTextField>();
		tmp.put(Direction.X, new JTextField());
		tmp.put(Direction.Y, new JTextField());
		tmp.put(Direction.Z, new JTextField());
		this.labelsByDirection = Collections.unmodifiableMap(tmp);
	}
	
	// MINOR implement feature which displays global min/max for xyz coordinates
	//       also ueber den gesamten zeitraum, um endpunkte/extrempunkte festzustellen
	
	public JointPanel(final String label) {
		this.label = label;

		this.setSize(SIZE);
		this.setMaximumSize(SIZE);
		this.setPreferredSize(SIZE);
		
		this.setBackground(Color.PINK);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		this.initComponents();
	}
	
	private void initComponents() {
		this.setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
		final JLabel lblHeader = StyleConstantsPool.newCoordinatesHeader(this.label);
		this.add(lblHeader, c);
		
		c.gridx = 0; c.gridy = 1; c.gridwidth = 1;
		this.initCoordinateField(Direction.X, c);

		c.gridx = 1; c.gridy = 1;
		this.initCoordinateField(Direction.Y, c);

		c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
		this.initCoordinateField(Direction.Z, c);
	}
	
	private void initCoordinateField(final Direction direction, final GridBagConstraints c) {
		final JTextField field = this.labelsByDirection.get(direction);
		field.setColumns(COORDINATE_COLUMNS);
		field.setFont(StyleConstantsPool.FONT_COORDINATE_VALUE);
		field.setOpaque(false);
		field.setEditable(false);
		field.setBorder(BorderFactory.createEmptyBorder());
		
		final String labelText = direction.name().toLowerCase();
		final JPanel labelAndFieldWrapper = new JPanel();
		labelAndFieldWrapper.setOpaque(false);
		labelAndFieldWrapper.add(StyleConstantsPool.newCoordinatesXyzLabel(labelText + ":"));
		labelAndFieldWrapper.add(field);
		
		this.add(labelAndFieldWrapper, c);
	}

	public final void updateCoordinate(final Coordinate coordinate) {
		for(final Entry<Direction, JTextField> entry : this.labelsByDirection.entrySet()) {
			final String newText = String.valueOf(CoordinateUtil.prettyPrint(coordinate, entry.getKey()));
			entry.getValue().setText(newText);
		}
	}
	
}
