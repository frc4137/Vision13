package com.wildelake.frc.vision13.camera;

import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.RGBImage;

/**
 * A virtual camera which always returns a static image.
 * Primarily for testing, but could also be used for things
 * such as caching.
 *
 */
public class ImageCamera implements Camera {
	private ColorImage img;
	
	public ImageCamera(ColorImage img) {
		this.img = img;
	}
	
	public ImageCamera(String filename) throws NIVisionException {
		this.img = new RGBImage(filename);
	}

	public ColorImage getImage() {
		return img;
	}

}
