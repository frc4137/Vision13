package com.wildelake.frc.vision13.camera;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ColorImage;

/**
 * This wraps an AxisCamera so that it adheres to the Camera interface.
 */
public class MyAxisCamera implements Camera {
	private final AxisCamera cam;
	
	public MyAxisCamera(AxisCamera cam) {
		this.cam = cam;
	}
	
	public ColorImage getImage() {
		try {
			return cam.getImage();
		}
		catch (Exception e) {
			return null;
		}
	}
}
