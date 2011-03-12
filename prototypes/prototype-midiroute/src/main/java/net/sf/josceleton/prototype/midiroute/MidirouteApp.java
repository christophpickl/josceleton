package net.sf.josceleton.prototype.midiroute;

import javax.swing.SwingUtilities;

import net.pulseproject.commons.util.GuiUtil;
import net.sf.josceleton.prototype.midiroute.view.MainWindow;
import net.sf.josceleton.prototype.midiroute.view.ProtoUtil;
import net.sf.josceleton.prototype.midiroute.view.ViewMediator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// $ osceleton -i recorded_session.oni
public class MidirouteApp {
	
	private static final Log LOG = LogFactory.getLog(MidirouteApp.class);

	
	public static void main(String[] args) throws Exception {
		LOG.info("START");
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final MainWindow window = new MainWindow(new ViewMediator());
				window.setVisible(true);
				
				ProtoUtil.log("====================================================================");
				ProtoUtil.log("                               WELCOME");
				ProtoUtil.log("====================================================================");
				ProtoUtil.log("");
				ProtoUtil.log("Enter port, configuration and hit start ...");
				ProtoUtil.log("");
				ProtoUtil.log("");
			}
		});
		
	}
	
}
