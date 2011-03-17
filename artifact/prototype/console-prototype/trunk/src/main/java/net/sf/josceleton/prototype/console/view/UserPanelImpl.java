package net.sf.josceleton.prototype.console.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class UserPanelImpl extends JPanel implements UserPanel {
	
	private static final long serialVersionUID = -7101424945709184641L;
	
	private static final boolean LEFT_RIGHT_INVERTED = true; // CONFIG OPTION; static coded!
	
	private final User user;
	
	private final JLabel lblState =
		StyleConstantsPool.newUserPanelStateLabel("Calibrating (waiting for psi position) ...");
	
	private boolean skeletonAvailable = false;

	private final Location2DView locationView = new Location2DView();
	
	private final Map<Joint, CoordinatesDrawer> bodyPartViews;
	
	private final Color baseColor; 
	
	@Inject public UserPanelImpl(@Assisted final User user) {
		this.user = user;
		
		final Iterable<Joint> bodyParts = Joints.values();
		this.bodyPartViews = new HashMap<Joint, CoordinatesDrawer>();
		for(final Joint currentBodyPart : bodyParts) {
			this.bodyPartViews.put(currentBodyPart, new CoordinatesDrawer(currentBodyPart.getLabel()));
		}
		
		// FIXME color
		this.baseColor = new Color(50, 50, 0);
//		this.baseColor = ColorUtil.newRandomColor();
//		final Color borderColor = ColorUtil.darken(this.baseColor, 120);
//		final Color backgroundWaitingColor = ColorUtil.darken(this.baseColor, 80);
		
//		this.setBorder(BorderFactory.createLineBorder(borderColor, 3));
//		this.setBackground(backgroundWaitingColor); // when skeleton available, will become brighter
		
		this.initComponents();
	}

	private void initComponents() {
		final JPanel infoPanel = new JPanel(new GridBagLayout());
		infoPanel.setOpaque(true);
//		FIXME color infoPanel.setBackground(ColorUtil.darken(this.baseColor, 90));
		
		final GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		infoPanel.add(StyleConstantsPool.newUserPanelTopLabel("User #" + this.user.getOsceletonId()), c);
		
		c.gridy = 1;
		infoPanel.add(this.lblState, c);
		
		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(this.createHumanCoordinates(), BorderLayout.CENTER);
	}
	
	private JPanel createHumanCoordinates() {
		final JPanel partsWrapper = new JPanel();
		partsWrapper.setOpaque(false);
		partsWrapper.setLayout(new GridBagLayout());
		
		// FIXME better solution for BodyParts thingy in here
	// TODO if skeleton data not yet available, set state of coordinatesDrawer (internally set background color darker)
		final GridBagConstraints c = new GridBagConstraints();
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.HEAD()), c, 2, 0, false);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.NECK()), c, 2, 1, false);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.HAND().LEFT()), c, 0, 1);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.HAND().RIGHT()), c, 4, 1);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.ELBOW().LEFT()), c, 0, 2);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.ELBOW().RIGHT()), c, 4, 2);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.SHOULDER().LEFT()), c, 1, 2);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.SHOULDER().RIGHT()), c, 3, 2);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.TORSO()), c, 2, 2/*2.5*/, false);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.HIP().LEFT()), c, 1, 3);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.HIP().RIGHT()), c, 3, 3);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.KNEE().LEFT()), c, 1, 4);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.KNEE().RIGHT()), c, 3, 4);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.ANKLE().LEFT()), c, 0, 4);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.ANKLE().RIGHT()), c, 4, 4);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.FOOT().LEFT()), c, 0, 5);
		this.addBody(partsWrapper, this.bodyPartViews.get(Joints.FOOT().RIGHT()), c, 4, 5);
		
		c.gridx = 2;
		c.gridy = 5;
		partsWrapper.add(this.locationView, c);
		
		return partsWrapper;
	}
	
	private void addBody(final JPanel parent, final JComponent child, final GridBagConstraints c,
			final int x, final int y) {
		this.addBody(parent, child, c, x, y, true);
	}
	
	private void addBody(final JPanel parent, final JComponent child, final GridBagConstraints c,
			final int x, final int y, final boolean isLeftRightCompatible) {
		if (child == null) { throw new IllegalArgumentException("child == null"); }
		c.gridx = LEFT_RIGHT_INVERTED && isLeftRightCompatible ? Math.abs(x - 4 /*max index of cols*/) : x;
		c.gridy = y;
		parent.add(child, c);
	}

	public final void setSkeletonAvailableTrue() {
		this.skeletonAvailable = true;
		
//		FIXME color this.setBackground(ColorUtil.brighten(this.baseColor, 50)); // indicate enabled
		this.lblState.setText("Calibrated. Skeleton is now available.");
		
		new Timer().schedule(new TimerTask() {
			@Override public void run() {
				UserPanelImpl.this.lblState.setText(""); // shortcut; kind of a hack, yes, i admit it ;)
		}}, 4000);
	}
	
	public final boolean isSkeletonAvailable() {
		return this.skeletonAvailable;
	}

	@Override public final Component asComponent() {
		return this;
	}
	
	@Override public final void updateCoordinate(final Joint bodyPart, final Coordinate coordinate) {
		final CoordinatesDrawer drawer = this.bodyPartViews.get(bodyPart);
		drawer.updateCoordinate(coordinate);
		
		if(bodyPart == Joints.TORSO()) {
			final double x = coordinate.x();
			final double z = coordinate.z();
			this.locationView.updateXy(x, z);
		}
	}

	
}
