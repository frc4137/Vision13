package com.wildelake.frc.vision13.utils;

public abstract class Function {
	public abstract double f(double x);
	public double of(double x) {
		return f(x);
	}
	
	/**
	 * A convenience method for SpeedController calibrations
	 */
	public static Function calibrator(final double plusa, final double plusb, final double plusc,
			final double minusa, final double minusb, final double minusc) {
		return new Function() {
			public double f(double x) {
				if (x > 0) return plusa * x * x + plusb * x + plusc;
				else if (x < 0) return minusa * x * x + minusb * x + minusc;
				else return 0.0;
			}
		};
	}
}
