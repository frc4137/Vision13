package com.wildelake.frc.vision13.camera;

import edu.wpi.first.wpilibj.camera.NonSingletonAxisCamera;
import edu.wpi.first.wpilibj.image.ColorImage;

/**
 * This wraps an AxisCamera so that it adheres to the Camera interface.
 */
public class MyAxisCamera implements Camera {
	private final NonSingletonAxisCamera cam;
	private ColorImage image;
	
	public MyAxisCamera(NonSingletonAxisCamera nonSingletonAxisCamera) {
		this.cam = nonSingletonAxisCamera;
		do {
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!nonSingletonAxisCamera.freshImage());
		try {
			image = nonSingletonAxisCamera.getImage();
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
