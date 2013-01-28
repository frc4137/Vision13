package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.compositions.*;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * MechanicalPilot is the primary set of controls for Mechanical on 01-26-12
 */
public class CalibrationPilot extends ControlSet implements Pilot {
	private final Controller joystick1;
	private final SpeedController[] motors;
	private final int[] currMotor;
	private VariadicControl speed;
	private DriverStationLCD dsl;
	
	public CalibrationPilot(Controller joystick1, SpeedController[] motors) {
		this.joystick1 = joystick1;
		this.motors = motors;
		this.currMotor = new int[] { 0 };
		Control.buildControlSet(this);
		dsl = DriverStationLCD.getInstance();
	}
	
	
	public void buildControlSet() {
		speed = new ToggleMultiplyVariadicControl(
			new ToggleBooleanControl(new BooleanInput(joystick1, 12)),
			new RadioVariadicControl(
				new BooleanControl[] {
					new BooleanInput(joystick1, 7),
					new BooleanInput(joystick1, 8),
					new BooleanInput(joystick1, 9),
					new BooleanInput(joystick1, 11)},
				new double[] {1, .5, .25, 0}),
			-1);
		new ToggleBooleanControl(new OrGateBooleanControl(
			new BooleanControl[] {
				new BooleanInput(joystick1, 3),
				new BooleanInput(joystick1, 4),
				new BooleanInput(joystick1, 5),
				new BooleanInput(joystick1, 6)}))
		.addListener(new BooleanControl.Listener() {
				public void onChange(boolean value) {
				currMotor[0]++;
				if (currMotor[0] >= motors.length) currMotor[0] = 0;
			}
		});
	}
	
	
	public void update() {
		dsl.println(Line.kMain6, 1, "Don't SPACEBRO");
		dsl.println(Line.kUser2, 1, "Speed: "+speed.getValue()+"  ");
		dsl.println(Line.kUser3, 1, "Current Motor: "+(currMotor[0]+1)+"  ");
		dsl.updateLCD();
//		System.out.println("Speed: " + speed.getValue());
		motors[currMotor[0]].set(speed.getValue());
	}

}
