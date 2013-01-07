package com.wildelake.frc.vision13.components;

import com.wildelake.frc.vision13.utils.Function;

import edu.wpi.first.wpilibj.SpeedController;

public class CalibratedSpeedController implements SpeedController {
	private final SpeedController scontrol;
	private final Function f;
	
	public CalibratedSpeedController(SpeedController scontrol, Function f) {
		this.scontrol = scontrol;
		this.f = f;
	}

	@Override
	public void pidWrite(double output) {
		scontrol.pidWrite(output);
	}

	@Override
	public double get() {
		return scontrol.get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		scontrol.set(f.of(speed), syncGroup);
	}

	@Override
	public void set(double speed) {
		scontrol.set(f.of(speed));
	}

	@Override
	public void disable() {
		scontrol.disable();
	}

}
