package com.wildelake.frc.vision13.camera;

import java.util.Date;

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
 * 
 *     new Stereoscope(leftCamera, rightCamera) {
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
	private boolean depthCalced = false, xCalced = false, yCalced = false, refreshing = false;
	private double x, y, depth;

	private void setLeftReports(ParticleAnalysisReport[] leftReports) {
		this.leftReports = leftReports;
	}
	
	private void setRightReports(ParticleAnalysisReport[] rightReports) {
		this.rightReports = rightReports;
	}
	
	private void setRefreshing(boolean refreshing) {
		this.refreshing = refreshing;
	}
	
	private void refreshed() {
		 depthCalced = xCalced = yCalced = false;
	}

	public Stereoscope(Camera left, Camera right, double distance, double halfAOV, CriteriaCollection cc) {
		if (cc == null) {
			cc = new CriteriaCollection();
			cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
			cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
		}
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
		this(left, right, 33.0+3.0/16.0, Math.toRadians(47.0/2.0), null);
	}
	
	private class Refresher implements Runnable {
		// Should probably consider using more general synchronization techniques...
		private Stereoscope sscope;
		private ParticleAnalysisReport[][] reports;
		private int index, unindex;
		private String name;
		
		public Refresher(Stereoscope sscope, ParticleAnalysisReport[][] reports, int index, String name) {
			this.sscope = sscope;
			this.reports = reports;
			this.index = index;
			unindex = (index == 0) ? 1 : 0;
			this.name = name;
		}
		
		public void run() {
			long start = new Date().getTime();
			try { reports[index] = sscope.updateSide(left, "/tmp/" + name + "_"); }
			catch (NIVisionException e) { e.printStackTrace(); }
			if (reports[unindex] == null) return;
			sscope.setLeftReports (reports[index]);
			sscope.setRightReports(reports[unindex]);
			sscope.setRefreshing(false);
			sscope.refreshed();
			System.out.println("time to refresh: " + (new Date().getTime() - start) / 1000);
		}
	}
	
	/**
	 * recalculate the leftReports and rightReports variables
	 */
	public void update() {
		if (refreshing) return;
		refreshing = true;
		final ParticleAnalysisReport[][] reports = new ParticleAnalysisReport[][] { null, null };
		
		new Thread(new Refresher(this, reports, 0, "left")).run();
		new Thread(new Refresher(this, reports, 1, "right")).run();
	}
	
	private ParticleAnalysisReport[] updateSide(Camera cam, String prefix) throws NIVisionException {
		ColorImage image = null;
		BinaryImage thresholdImage = null, bigObjectsImage = null, convexHullImage = null, filteredImage = null;
		boolean connectivity8 = true;
		try {
			image = cam.getImage();
			thresholdImage = safeThreshold(image);
			bigObjectsImage = thresholdImage.removeSmallObjects(connectivity8, 2);
			convexHullImage = bigObjectsImage.convexHull(connectivity8);
			filteredImage = convexHullImage.particleFilter(cc);
			archive(image,           prefix + "original.png");
			archive(thresholdImage,  prefix + "thresholded.png");
			archive(bigObjectsImage, prefix + "big_objects.png");
			archive(convexHullImage, prefix + "convex_hull.png");
			archive(filteredImage,   prefix + "filtered.png");
	
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
	
	public double getDepth() {
		if (depthCalced) return depth;
		if (rightReports.length == 0 || leftReports.length == 0)
			return -1.0;
		// Thanks to Edwin Tjandranegara (Purdue University) for "Distance Estimation Algorithm for Stereo Pair Images" (2005).
		double pixelWidth = 640/2; // TODO generalize this, currently it only works for images which are 640 pixels wide
		double[] a = {
			// TODO this currently just picks the first object detect by each camera, which is inherently wrong, 
			// and should only be used for testing with a single goal.
			MoreMath.atan(((pixelWidth - (double) leftReports[0].center_mass_x)
				/ pixelWidth) * tanHalfAOV),
			MoreMath.atan((((double) rightReports[0].center_mass_x - pixelWidth)
				/ pixelWidth) * tanHalfAOV)
		};
		double[] b = {
				Math.tan(Math.PI/2 - a[0]),
				Math.tan(Math.PI/2 - a[1])
		};
		depth = (b[0] * b[1] * distance) / (b[0] + b[1]);
		depthCalced = true;
		return depth;
	}
	
	public double getX() {
		if (xCalced) return x;
		x = (rightReports[0].center_mass_x_normalized + leftReports[0].center_mass_x_normalized) / 2;
		xCalced = true;
		return x;
	}
	
	public double getY() {
		if (yCalced) return y;
		y = (rightReports[0].center_mass_y_normalized + leftReports[0].center_mass_y_normalized) / 2;
		yCalced = true;
		return y;
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
