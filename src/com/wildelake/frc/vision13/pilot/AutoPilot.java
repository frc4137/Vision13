package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.Port;
import com.wildelake.frc.vision13.camera.RangeFinder;
import com.wildelake.frc.vision13.camera.Stereoscope;
import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.VariadicInput;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * ManualPilot is the primary set of controls for manually operating the robot.
 */
public class AutoPilot extends ControlSet implements Pilot {
	private final Controller joystick1;
	private final RangeFinder sscope;
	private final RobotDrive drive;
	private SpeedController fireMotor;
	private VariadicControl joy1x, joy1y, joy1rot, fire;
	private BooleanControl archive, refresh;
	private DriverStationLCD dsl;
	private int time;
	

	public AutoPilot(Controller joystick1, RobotDrive drive, SpeedController fireMotor, RangeFinder sscope) {
		this.joystick1 = joystick1;
		this.sscope = sscope;
		this.drive = drive;
		this.fireMotor = fireMotor;
		time = 0;
		Control.buildControlSet(this);
		dsl = DriverStationLCD.getInstance();
	}
	
	
	public void buildControlSet() {
		// setup the driving joystick (1)
		VariadicControl
			joy1xRaw = new VariadicInput(joystick1, Port.JOY1X),
			joy1yRaw = new VariadicInput(joystick1, Port.JOY1Y);
		joy1rot = new MultiplyVariadicControl(
			new VariadicInput(joystick1, Port.JOY1ROT),
			new ReboundedVariadicControl(
				new BoundedVariadicControl(
					new CounterVariadicControl(
						new BooleanInput(joystick1, Port.INC_SPEED_BTN),
						new BooleanInput(joystick1, Port.RST_SPEED_BTN),
						new BooleanInput(joystick1, Port.DEC_SPEED_BTN),
						.1)),
					0, 2));
		
		BooleanControl
			fullSpeed   = new BooleanInput(joystick1, Port.FULL_SPEED_BTN),
			grannySpeed = new BooleanInput(joystick1, Port.GRANNY_SPEED_BTN);
		
		
		// make the input from the joystick halved by default
		joy1x = new MultiplyVariadicControl(joy1xRaw, 0.5);
		joy1y = new MultiplyVariadicControl(joy1yRaw, 0.5);
		// set up a button to return it to full speed
		joy1x = new ToggleMultiplyVariadicControl(fullSpeed, joy1x, 2.0);
		joy1y = new ToggleMultiplyVariadicControl(fullSpeed, joy1y, 2.0);
		// set up a button to quarter the input for even greater precision
		joy1x = new ToggleMultiplyVariadicControl(grannySpeed, joy1x, 0.5);
		joy1y = new ToggleMultiplyVariadicControl(grannySpeed, joy1y, 0.5);
		
		// Fire button
		fire = new ToggleMultiplyVariadicControl(
			new ToggleBooleanControl(new BooleanInput(joystick1, 8)),
			new BooleanVariadicControl(
				new ToggleBooleanControl(
					new BooleanInput(joystick1, Port.FIRE_BTN)),
				0,
				1.0),
			-1);
		archive = new ToggleBooleanControl(
				new OrGateBooleanControl(new BooleanControl[] {
					new BooleanInput(joystick1, 2),
					new BooleanInput(joystick1, 3),
					new BooleanInput(joystick1, 4),
					new BooleanInput(joystick1, 5)}));
		refresh = new BooleanInput(joystick1, Port.STEREOSCOPE_BTN);

	}
	
	
	public void update() {
		if (time % 180 == 0) {
			sscope.refresh();
			((Stereoscope) sscope).debugReports();
			SmartDashboard.putString("anaFeed", "U LOST?");
		}
		time++;
		dsl.println(Line.kMain6, 1, "OK");
		dsl.println(Line.kUser2, 1, "JoyStick 1 X: "+joy1x.getValue());
		dsl.println(Line.kUser3, 1, "JoyStick 1 Y: "+joy1y.getValue());
		dsl.println(Line.kUser4, 1, "JoyStick 1 r: " + joy1rot.getValue());
		dsl.println(Line.kUser5, 1, String.valueOf(fire.getValue()));
		dsl.println(Line.kUser6, 1, "Current Range: "+sscope.getDepth()+"                           ");
		drive.mecanumDrive_Cartesian(-joy1x.getValue(), joy1y.getValue(), -joy1rot.getValue(), 0);
		fireMotor.set(fire.getValue());
		if (refresh.getValue()) sscope.refresh();
		if (archive.getValue()) ((Stereoscope) sscope).archivePhotos();
		dsl.updateLCD();
		System.out.println(System.currentTimeMillis());
	}

}
