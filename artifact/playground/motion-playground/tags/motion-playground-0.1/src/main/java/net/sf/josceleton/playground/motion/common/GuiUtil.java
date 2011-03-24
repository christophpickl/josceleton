package net.sf.josceleton.playground.motion.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class GuiUtil {

    public static void setCenterLocation(final Component component) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        component.setLocation((screenSize.width  - component.getWidth())  / 2,
        					  (screenSize.height - component.getHeight()) / 2);
    }
}
