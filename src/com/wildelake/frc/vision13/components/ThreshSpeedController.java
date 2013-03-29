package com.wildelake.frc.vision13.components;

import com.wildelake.frc.vision13.utils.Function;

import edu.wpi.first.wpilibj.SpeedController;

public class ThreshSpeedController implements SpeedController {
	private final SpeedController scontrol;
	private final double[] range;
	
	public ThreshSpeedController(SpeedController scontrol, double[] range) {
		this.scontrol = scontrol;
		this.range = range;
	}

	
	public void pidWrite(double output) {
		scontrol.pidWrite(output);
	}

	
	public double get() {
		return scontrol.get();
	}

	
	public void set(double speed, byte syncGroup) {
		set(speed);
	}

	
	public void set(double speed) {
            if (range[0] <= speed && speed <= range[1]) {
		scontrol.set(0);
            } else {
                scontrol.set(speed);
            }
	}

	
	public void disable() {
		scontrol.disable();
	}

}
