package com.wildelake.frc.vision13.camera;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.NonSingletonAxisCamera;
import edu.wpi.first.wpilibj.image.ColorImage;

/**
 * This wraps an AxisCamera so that it adheres to the Camera interface.
 */
public class MyAxisCamera implements Camera {
	private final AxisCamera cam;
	private ColorImage image;
	
	public MyAxisCamera(AxisCamera axisCamera) {
		this.cam = axisCamera;
		do {
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!axisCamera.freshImage());
		try {
			image = axisCamera.getImage();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public ColorImage getImage() {
		try {
			if (cam.freshImage()) image = cam.getImage();
			return image;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
