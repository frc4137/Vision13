package com.wildelake.frc.vision13.camera;

import com.wildelake.frc.vision13.Config;

import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public abstract class Monoscope extends RangeFinder {
	
	private Camera cam;
	private CriteriaCollection cc;
	private double factor0, factor1;
	
	public Monoscope(Camera cam, double factor0, double factor1) {
		CriteriaCollection cc = new CriteriaCollection();
		cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
		cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
		this.cam = cam;
		this.cc = cc;
		this.factor0 = factor0;
		this.factor1 = factor1;
	}

	public double getDepth() {
		ParticleAnalysisReport[] rr = generateReport();
		if (rr.length>=1) return factor0/rr[0].particleArea*factor1;
		return -1;
	}

	private ParticleAnalysisReport[] generateReport() {
		ColorImage image = null;
		BinaryImage thresholdImage = null, bigObjectsImage = null, convexHullImage = null, filteredImage = null;
		boolean connectivity8 = false;
		try {
			image = cam.getImage();
			thresholdImage = safeThreshold(image);
			bigObjectsImage = thresholdImage.removeSmallObjects(connectivity8, 2);
			convexHullImage = bigObjectsImage.convexHull(connectivity8);
			filteredImage = convexHullImage.particleFilter(cc);
			return filteredImage.getOrderedParticleAnalysisReports();
		} catch (NIVisionException e) {
			e.printStackTrace();
		} finally {
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
		return null;

	}

	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
