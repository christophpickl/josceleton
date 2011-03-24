package net.sf.josceleton.playground.motion.app2.game1;

import java.util.Timer;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.page.PageArgs;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MenuPage extends Page<MenuView> implements Runnable {
	
	private static final Log LOG = LogFactory.getLog(MenuPage.class);
	public static final String ID = MenuPage.class.getName();
	private static final int TIME_TO_ROTATE_BOXER_MSG = 3 * 1000;
	private static final String[] BOXER_MESSAGES = new String[] {
		"Wanna fight me?!", "Gonna beat your ass!", "MUHAHAHAHAHA!", "You have NO CHANCE!"
	};
	
	private final MenuView view;
	private int currentMessageIndex = 0;
	private Timer timer;
	
	
	public MenuPage() {
		super(ID, new MenuView());
		this.view = this.getView();
	}

	@Override protected void _start(WorldSnapshot world, PageArgs args) {
		LOG.info("start()");
		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(new TimerTaskRunner(this), 0, TIME_TO_ROTATE_BOXER_MSG);
	}

	@Override public void stop() {
		LOG.info("start()");
		this.timer.cancel();
		this.timer = null;
	}

	@Override
	public void run() {
		LOG.debug("run() ... rotating boxer message");
		
		String currentMessage = BOXER_MESSAGES[this.currentMessageIndex];
		this.view.setBoxerMessage(currentMessage);
		
		this.currentMessageIndex++;
		if(this.currentMessageIndex == BOXER_MESSAGES.length) {
			this.currentMessageIndex = 0;
		}
	}
}
