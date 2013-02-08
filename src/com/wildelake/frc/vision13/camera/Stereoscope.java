package com.wildelake.frc.vision13.camera;

import java.util.Vector;

import com.wildelake.frc.vision13.Config;
import com.wildelake.frc.vision13.utils.MoreMath;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.MonoImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.image.RGBImage;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;

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
public abstract class Stereoscope extends RangeFinder {
	public final Camera left, right;
	private final double distance, tanHalfAOV;
	private final CriteriaCollection cc;
	private ParticleAnalysisReport[] leftReports, rightReports;
	private boolean archiveNextRefresh = false;

	public Stereoscope(Camera left, Camera right, double distance, double halfAOV, CriteriaCollection cc) {
		this.left = left;
		this.right = right;
		this.distance = distance;
		this.tanHalfAOV = Math.tan(halfAOV);
		this.cc = cc;
		refresh();
	}
	
	/**
	 * This constructor is specialized for the 2013 robot's configuration using axis cameras
	 */
	public Stereoscope(Camera left, Camera right) {
		CriteriaCollection cc = new CriteriaCollection();
		cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
		cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
		this.left = left;
		this.right = right;
		this.distance = 33+3/16;
		this.tanHalfAOV = Math.tan(Math.toRadians(47/2));
		this.cc = cc;
		refresh();
	}
	
	/**
	 * recalculate the leftReports and rightReports variables
	 */
	public void refresh() {
		try { leftReports = refreshSide(left, archiveNextRefresh, "/tmp/left_"); }
		catch (NIVisionException e) { e.printStackTrace(); }
		
		try { rightReports = refreshSide(right, archiveNextRefresh, "/tmp/right_"); }
		catch (NIVisionException e) { e.printStackTrace(); }
		
		archiveNextRefresh = false;
	}
	
	private ParticleAnalysisReport[] refreshSide(Camera cam, boolean shouldArchive, String prefix) throws NIVisionException {
		ColorImage image = null;
		BinaryImage thresholdImage = null, bigObjectsImage = null, convexHullImage = null, filteredImage = null;
		boolean connectivity8 = true;
		try {
			// Thanks to WPILIBJ authors. Much of this is copied from sample code.
			image = cam.getImage();
			thresholdImage = safeThreshold(image);
			bigObjectsImage = thresholdImage.removeSmallObjects(connectivity8, 2);
			convexHullImage = bigObjectsImage.convexHull(connectivity8);
			filteredImage = convexHullImage.particleFilter(cc);
			if (shouldArchive) {
				System.out.println(prefix);
				image          .write(prefix + "original.png");
				thresholdImage .write(prefix + "thresholded.png");
				bigObjectsImage.write(prefix + "big_objects.png");
				convexHullImage.write(prefix + "convex_hull.png");
				filteredImage  .write(prefix + "filtered.png");
			}
	
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
			System.out.println("(Left)  Particle: " + i + ",  Center of mass x: " + r.center_mass_x + ",  Center of mass y: " + r.center_mass_y);
		}
		for (int i = 0; i < rightReports.length; i++) {
			ParticleAnalysisReport r = rightReports[i];
			System.out.println("(Right)  Particle: " + i + ",  Center of mass x: " + r.center_mass_x + ",  Center of mass y: " + r.center_mass_y);
		}
	}
	
	public void archivePhotos() {
		archiveNextRefresh = true;
	}
	
	/* (non-Javadoc)
	 * @see com.wildelake.frc.vision13.camera.RangeFinder#getDepth()
	 */
	public double getDepth() {
		if (rightReports.length == 0 || leftReports.length == 0)
			return -1.0;
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
	
	/* (non-Javadoc)
	 * @see com.wildelake.frc.vision13.camera.RangeFinder#getX()
	 */
	public double getX() {
		return (rightReports[0].center_mass_x_normalized + leftReports[0].center_mass_x_normalized) / 2;
	}
	
	/* (non-Javadoc)
	 * @see com.wildelake.frc.vision13.camera.RangeFinder#getY()
	 */
	public double getY() {
		// technically both the masses should be about the same, but this should help make it more accurate
		return (rightReports[0].center_mass_y_normalized + leftReports[0].center_mass_y_normalized) / 2;
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
