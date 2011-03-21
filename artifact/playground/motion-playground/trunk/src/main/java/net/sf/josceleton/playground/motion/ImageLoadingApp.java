package net.sf.josceleton.playground.motion;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImageLoadingApp {

	public static void main(String[] args) throws Exception {
		final URL url = ImageLoadingApp.class.getResource("/image/next.png");
		System.out.println("url: " + url);
		final ImageIcon img = new ImageIcon(url);
		System.out.println("img: " + img);
		
		System.out.println(lookupImage("/image/next.png"));
	}

	
	private static Image lookupImage(String path) {
		final Image foundImage = Toolkit.getDefaultToolkit().getImage(path);
		if(foundImage == null) {
			throw new RuntimeException("Could not find image: [" + path + "]!");
		}
		return foundImage;
	}
}
