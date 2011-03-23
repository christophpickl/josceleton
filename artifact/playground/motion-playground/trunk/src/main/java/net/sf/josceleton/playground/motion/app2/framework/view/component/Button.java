package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Graphics2D;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.playground.motion.app2.framework.view.Drawable;

public interface Button extends Drawable, Async<ButtonListener> {

	int calculateHalfWidth(Graphics2D g);

	int calculateHeight(Graphics2D g);

}
