package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.Port;
import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.VariadicInput;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * ManualPilot is the primary set of controls for manually operating the robot.
 */
public class ManualPilot extends ControlSet implements Pilot {
	private final Controller joystick1;
	private final RobotDrive drive;
	private VariadicControl joy1x, joy1y, joy1rot;
	private DriverStationLCD dsl;
	
	public ManualPilot(Controller joystick1, RobotDrive drive) {
		this.joystick1 = joystick1;
		this.drive = drive;
		Control.buildControlSet(this);
		dsl = DriverStationLCD.getInstance();
	}
	
	
	public void buildControlSet() {
		VariadicControl
			joy1xRaw = new VariadicInput(joystick1, Port.JOY1X),
			joy1yRaw = new VariadicInput(joystick1, Port.JOY1Y);
		BooleanControl
			fullSpeed   = new BooleanInput(joystick1, Port.FULL_SPEED_BTN),
			grannySpeed = new BooleanInput(joystick1, Port.GRANNY_SPEED_BTN);
		
		// setup the driving joystick (1)
		joy1rot = new VariadicInput(joystick1, Port.JOY1ROT);
		// make the input from the joystick halved by default
		joy1x = new MultiplyVariadicControl(joy1xRaw, 0.5);
		joy1y = new MultiplyVariadicControl(joy1yRaw, 0.5);
		// set up a button to return it to full speed
		joy1x = new ToggleMultiplyVariadicControl(fullSpeed, joy1x, 2.0);
		joy1y = new ToggleMultiplyVariadicControl(fullSpeed, joy1y, 2.0);
		// set up a button to quarter the input for even greater precision
		joy1x = new ToggleMultiplyVariadicControl(grannySpeed, joy1x, 0.5);
		joy1y = new ToggleMultiplyVariadicControl(grannySpeed, joy1y, 0.5);
	}
	
	
	public void update() {
		dsl.println(Line.kMain6, 1, "OK");
		dsl.println(Line.kUser2, 1, "JoyStick 1 X: "+joy1x.getValue());
		dsl.println(Line.kUser3, 1, "JoyStick 1 Y: "+joy1y.getValue());
		dsl.println(Line.kUser4, 1, "JoyStick 1 r" + joy1rot.getValue());
		drive.mecanumDrive_Cartesian(joy1x.getValue(), joy1y.getValue(), joy1rot.getValue(), 0);
		dsl.updateLCD();
//		drive.tankDrive(foo.getValue(), foo.getValue());
	}

}
