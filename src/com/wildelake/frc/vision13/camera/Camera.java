package com.wildelake.frc.vision13.camera;

import edu.wpi.first.wpilibj.image.ColorImage;

/**
 * A generic interface for various entities which can provide images
 * This API is unstable.
 */
public interface Camera {
	/**
	 * This should return null if there is an error in getting the image.
	 * The caller really shouldn't need to know anything more than that
	 * and image could not be fetched.
	 */
	public ColorImage getImage();
}
