package com.wildelake.frc.vision13;

/**
 * Class Location
 * Wrapper class for a set of 3D coordinates (mapped to 2D).
 * @author Satyajit DasSarma
 *
 */
public class Location {
	private double[] l;
	public Location(double x, double y, double z) {
		l = new double[]{x,y,z};
	}
	public double[] getCartesian() {
		return new double[]{l[0],l[1]};
	}
	public double getX() {
		return l[0];
	}
	public double getY() {
		return l[1];
	}
	public void move(double x, double y) {
		l[0]+=x;
		l[1]+=y;
	}
}
