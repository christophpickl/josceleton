package net.sf.josceleton.playground.motion.app2.framework.view.common;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import net.sf.josceleton.playground.motion.ImageLoadingApp;

public class Resources {
	
	public static final Image CURSOR = lookupImage("/image/cursor.png");
	public static final Image OKAY   = lookupImage("/image/okay.png");
	public static final Image NOKAY  = lookupImage("/image/nokay.png");
	public static final Image NEXT   = lookupImage("/image/next.png");
	public static final Image DUMB_FACE = lookupImage("/image/dumb_face.png");

	
	private static Image lookupImage(String path) {
//		final Image foundImage = Toolkit.getDefaultToolkit().getImage(path);
//		if(foundImage == null) {
//			throw new RuntimeException("Could not find image: [" + path + "]!");
//		}
//		return foundImage;
		final URL url = ImageLoadingApp.class.getResource(path);
		if(url == null) {
			throw new RuntimeException("Could not find image by path [" + path + "]!");
		}
		final ImageIcon img = new ImageIcon(url);
		System.out.println("Resources: loaded image: " + path);
		return img.getImage();
	}
}
