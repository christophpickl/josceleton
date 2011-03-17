package net.sf.josceleton.commons.test;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

/**
 * @since 0.4
 */
public class TestableComponent extends Component {
	
	private static final long serialVersionUID = 2138244733327054729L;
	
	private final List<MouseListener> listeners = new LinkedList<MouseListener>();
	
	@Override public final synchronized void addMouseListener(final MouseListener listener) {
		this.listeners.add(listener);
	}
	
	public final List<MouseListener> getListeners() {
		return this.listeners;
	}
	
}
