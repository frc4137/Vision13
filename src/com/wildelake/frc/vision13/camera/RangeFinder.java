package com.wildelake.frc.vision13.camera;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.Image;
import edu.wpi.first.wpilibj.image.NIVisionException;

public abstract class RangeFinder {
	private boolean archiveNextRefresh = false;

	/**
	 * This returns the number of centimeters (parallel to the center of the field of view) to the target.
	 */
	public abstract double getDepth();

	/**
	 * Unlike getDepth, this returns a double in the range [-1.0, 1.0]
	 */
	public abstract double getX();

	/**
	 * Unlike getDepth, this returns a double in the range [-1.0, 1.0]
	 */
	public abstract double getY();
	
	
	public abstract BinaryImage threshold(ColorImage img) throws NIVisionException;
	
	protected BinaryImage safeThreshold(ColorImage img) {
		try {
			return threshold(img);
		}
		catch (NIVisionException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public abstract void update();

	public final void refresh() {
		update();
		archiveNextRefresh = false;
	}
	
	public final void archivePhotos() {
		archiveNextRefresh = true;
	}
	
	public final void archive(Image photo, String name) {
		try {
			if (archiveNextRefresh) photo.write(name);
		}
		catch (NIVisionException e) { }
	}
}