package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * MechanicalPilot is the primary set of controls for Mechanical on 01-09-12
 */
public class MechanicalPilot extends ControlSet implements Pilot {
	private final Controller joystick1;
	private final SpeedController motor1, motor2;
	private VariadicControl speed;
	private DriverStationLCD dsl;
	
	public MechanicalPilot(Controller joystick1, SpeedController motor1, SpeedController motor2) {
		this.joystick1 = joystick1;
		this.motor1 = motor1;
		this.motor2 = motor2;
		Control.buildControlSet(this);
		dsl = DriverStationLCD.getInstance();
	}
	
	
	public void buildControlSet() {
		BooleanControl reset = new OrGateBooleanControl(
			new BooleanInput(joystick1, 9),
			new BooleanInput(joystick1, 10)
		);
		VariadicControl corse = new CounterVariadicControl(
			new BooleanInput(joystick1, 7),
			reset,
			new BooleanInput(joystick1, 11),
			0.1
		);
		VariadicControl fine = new CounterVariadicControl(
			new BooleanInput(joystick1, 8),
			reset,
			new BooleanInput(joystick1, 12),
			0.01
		);
		speed = new SummedVariadicControl(corse, fine);
	}
	
	
	public void update() {
		dsl.println(Line.kMain6, 1, "Don't Press SPACEBAR");
		dsl.println(Line.kUser2, 1, "Current Speed: "+speed.getValue()+"                           ");
		motor1.set(speed.getValue());
		motor2.set(-speed.getValue());
		dsl.updateLCD();
//		drive.tankDrive(foo.getValue(), foo.getValue());
	}

}
