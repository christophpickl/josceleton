package net.sf.josceleton.playground.motion.app2.framework.view.resources;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import net.sf.josceleton.playground.motion.ImageLoadingApp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Images {
	
	private static final Log LOG = LogFactory.getLog(Images.class);
	private static final String IMAGE_BASE_PATH = "/image/";
	public static final Image CURSOR = lookupImage("cursor.png");
	public static final Image OKAY   = lookupImage("okay.png");
	public static final Image NOKAY  = lookupImage("nokay.png");
	public static final Image NEXT   = lookupImage("next.png");
	public static final Image DUMB_FACE = lookupImage("dumb_face.png");
	public static final Image PSI_TRANSPARENT = lookupImage("psi_transparent.png");
	public static final Image BOXER = lookupImage("boxer.png");
	public static final Image PUNCH_LEFT = lookupImage("punch_left.png");
	public static final Image PUNCH_RIGHT = lookupImage("punch_right.png");
	
	public static final ImageIcon PSI_TRANSPARENT_LITTLE_ICON = lookupImageIcon("psi_transparent_little.png");
	public static final Image PSI_TRANSPARENT_LITTLE = PSI_TRANSPARENT_LITTLE_ICON.getImage();
	
	
	private static Image lookupImage(String path) {
		return lookupImageIcon(path).getImage();
	}
	
	private static ImageIcon lookupImageIcon(String imageFileName) {
//		final Image foundImage = Toolkit.getDefaultToolkit().getImage(path);
//		if(foundImage == null) {
//			throw new RuntimeException("Could not find image: [" + path + "]!");
//		}
//		return foundImage;
		final String imageClasspathPath = IMAGE_BASE_PATH + imageFileName;
		final URL url = ImageLoadingApp.class.getResource(imageClasspathPath);
		if(url == null) {
			throw new RuntimeException("Could not find image by path [" + imageClasspathPath + "]!");
		}
		final ImageIcon img = new ImageIcon(url);
		LOG.debug("Loaded image by path: " + imageClasspathPath);
		return img;
	}
}
