package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.Port;
import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.VariadicInput;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * MechanicalPilot is the primary set of controls for Mechanical on 01-26-12
 */
public class MechanicalPilot extends ControlSet implements Pilot {
	private final Controller joystick1;
	private final RobotDrive drive;
	private VariadicControl direction;
	private DriverStationLCD dsl;
	
	public MechanicalPilot(Controller joystick1, RobotDrive drive) {
		this.joystick1 = joystick1;
		this.drive = drive;
		Control.buildControlSet(this);
		dsl = DriverStationLCD.getInstance();
	}
	
	
	public void buildControlSet() {
		direction = new ToggleMultiplyVariadicControl(
				new BooleanInput(joystick1, Port.INC_SPEED_BTN),
				new BooleanVariadicControl(
			new ToggleBooleanControl(
				new BooleanInput(joystick1, Port.DEC_SPEED_BTN)),
			1,
			-1.0),
			0
			);
	}
	
	
	public void update() {
		dsl.println(Line.kMain6, 1, "Don't SPACEBRO");
		dsl.println(Line.kUser2, 1, "Direction: "+direction.getValue());
		drive.mecanumDrive_Cartesian(0, direction.getValue(), 0, 0);
		// fireMotor.set(fire.getValue());
		dsl.updateLCD();
//		drive.tankDrive(foo.getValue(), foo.getValue());
	}

}
