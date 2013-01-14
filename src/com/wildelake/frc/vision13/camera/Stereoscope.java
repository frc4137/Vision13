package com.wildelake.frc.vision13.camera;

import com.wildelake.frc.vision13.Config;
import com.wildelake.frc.vision13.utils.MoreMath;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;

/**
 * Manages two Cameras to allow for calculating the 3d position of targets.
 * 
 * Must be initialized by providing a custom threshold method like so:
 *     new Stereoscope(leftCamera, rightCamera, distance, halfAOV, cc) {
 *         public BinaryImage threshold(ColorImage img) {
 *             return img.thresholdRGB(25, 255, 0, 45, 0, 47);
 *         }
 *     }
 */
public abstract class Stereoscope {
	private final Camera left, right;
	private final double distance, tanHalfAOV;
	private final CriteriaCollection cc;
	private ParticleAnalysisReport[] leftReports, rightReports;

	public Stereoscope(Camera left, Camera right, double distance, double halfAOV, CriteriaCollection cc) {
		this.left = left;
		this.right = right;
		this.distance = distance;
		this.tanHalfAOV = Math.tan(halfAOV);
		this.cc = cc;
	}
	
	public abstract BinaryImage threshold(ColorImage img);
	
	/**
	 * recalculate the leftReports and rightReports variables
	 */
	public void refresh() {
		try { leftReports = refreshSide(left); }
		catch (NIVisionException e) { e.printStackTrace(); }
		
		try { rightReports = refreshSide(right); }
		catch (NIVisionException e) { e.printStackTrace(); }
	}
	
	private ParticleAnalysisReport[] refreshSide(Camera cam) throws NIVisionException {
		ColorImage image = null;
		BinaryImage thresholdImage = null, bigObjectsImage = null, convexHullImage = null, filteredImage = null;
		try {
			// Thanks to WPILIBJ authors. Much of this is copied from sample code bundled with the library.
			image = cam.getImage();
			thresholdImage = threshold(image);
			bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);
			convexHullImage = bigObjectsImage.convexHull(false);
			filteredImage = convexHullImage.particleFilter(cc);
	
			return filteredImage.getOrderedParticleAnalysisReports();
		}
		catch (NIVisionException e) {
			throw e;
		}
		finally {
			try { // for whatever reason `free' can throw exceptions, which is just begging for memory leaks...
				if (filteredImage != null) filteredImage.free();
				if (convexHullImage != null) convexHullImage.free();
				if (bigObjectsImage != null) bigObjectsImage.free();
				if (thresholdImage != null) thresholdImage.free();
				if (image != null) image.free();
			}
			catch (Exception e) {
				System.err.println("Something broke while freeing an image buffer. Looks like it's gonna be a rough night.");
				e.printStackTrace();
				if (!Config.getMarchOn()) System.exit(1);
			}
		}
	}
	
	public void debugReports() {
		for (int i = 0; i < leftReports.length; i++) {
			ParticleAnalysisReport r = leftReports[i];
			System.out.println("(Left)  Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
		}
		for (int i = 0; i < rightReports.length; i++) {
			ParticleAnalysisReport r = rightReports[i];
			System.out.println("(Right) Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
		}
	}
	
	public double getDepth() {
		// Thanks to Edwin Tjandranegara (Purdue University) for "Distance Estimation Algorithm for Stereo Pair Images" (2005).
		double pixelWidth = 640/2; // TODO generalize this, currently it only works for images which are 640 pixels wide
		double[] a = {
			// TODO this currently just picks the first object detect by each camera, which is inherently wrong, 
			// and should only be used for testing with a single goal.
			MoreMath.atan(((rightReports[0].center_mass_x - pixelWidth)
					/ pixelWidth) * tanHalfAOV),
			MoreMath.atan(((leftReports[0].center_mass_x  - pixelWidth)
					/ pixelWidth) * tanHalfAOV)
		};
		double[] b = {
				Math.tan(Math.PI/2 - a[0]),
				Math.tan(Math.PI/2 - a[1])
		};
		return (b[0] * b[1] * distance) / (b[0] + b[1]);
	}
	
	public ColorImage getAnaglyph() throws NIVisionException {
		ColorImage rawLeft = null, rawRight = null;
		MonoImage red = null, green = null, blue = null;
		try {
			rawLeft = left.getImage();
			red = rawLeft.getRedPlane();
			
			rawRight = right.getImage();
			green = rawRight.getGreenPlane();
			blue = rawRight.getBluePlane();
			
			RGBImage anaglyph = new RGBImage();
			anaglyph.replaceRedPlane(red);
			anaglyph.replaceGreenPlane(green);
			anaglyph.replaceBluePlane(blue);
			
			return anaglyph;
		}
		catch (NIVisionException e) {
			throw e;
		}
		finally {
			try {
				if (rawLeft != null) rawLeft.free();
				if (rawRight != null) rawRight.free();
				if (red != null) red.free();
				if (green != null) green.free();
				if (blue != null) blue.free();
			}
			catch (Exception e) {
				System.err.println("Something broke while freeing an image buffer. Looks like it's gonna be a rough night.");
				e.printStackTrace();
				if (!Config.getMarchOn()) System.exit(1);
			}
		}
	}
}
