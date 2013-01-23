package com.wildelake.frc.vision13.components;

import com.wildelake.frc.vision13.utils.Function;

import edu.wpi.first.wpilibj.SpeedController;

public class RampedSpeedController implements SpeedController {
	private final SpeedController scontrol;
	private final double deltav;
	
	public RampedSpeedController(SpeedController scontrol, double deltav) {
		this.scontrol = scontrol;
		this.deltav = deltav;
	}

	
	public void pidWrite(double output) {
		scontrol.pidWrite(output);
	}

	
	public double get() {
		return scontrol.get();
	}

	
	public void set(double speed, byte syncGroup) {
		this.set(speed); // TODO handle this deprecated shit properly
	}

	
	public void set(double speed) {
		double difference = Math.min(deltav, Math.abs(scontrol.get() - speed));
		System.out.println(speed + " " + difference);
		scontrol.set(scontrol.get() > speed ? get() - difference : get() + difference);
	}

	
	public void disable() {
		scontrol.disable();
	}

}
