/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.MyJoystick;
import com.wildelake.frc.vision13.controls.VariadicInput;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;

public class VisionIterative extends IterativeRobot {
	private RobotDrive drive;
	private VariadicControl joy1x, joy1y, joy1rot;

	public void robotInit() {
		drive = new RobotDrive(0, 1, 2, 3); // the ports of the motors TODO the ports are fake
	}

	public void autonomousPeriodic() {

	}

	public void teleopInit() {
		Controller joystick1 = new MyJoystick(0); // TODO make sure that this is the correct port
		Controller joystick2 = new MyJoystick(1);
		VariadicControl
			joy1xRaw = new VariadicInput(joystick1, 0),
			joy1yRaw = new VariadicInput(joystick1, 1),
			joy2xRaw = new VariadicInput(joystick2, 0),
			joy2yRaw = new VariadicInput(joystick2, 1);
		BooleanControl
			fullSpeed   = new BooleanInput(joystick1, 0), // TODO these ports are made up
			grannySpeed = new BooleanInput(joystick1, 1);
		
		// setup the driving joystick (1)
		joy1rot = new VariadicInput(joystick1, 2);
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

	public void teleopPeriodic() {
		Control.tickAll();
		drive.mecanumDrive_Cartesian(joy1x.getValue(), joy1y.getValue(), joy1rot.getValue(), 0);
	}
}
