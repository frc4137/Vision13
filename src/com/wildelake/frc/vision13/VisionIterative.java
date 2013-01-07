/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.components.CalibratedSpeedController;
import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.MyJoystick;
import com.wildelake.frc.vision13.controls.VariadicInput;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import com.wildelake.frc.vision13.utils.*;

public class VisionIterative extends IterativeRobot {
	private RobotDrive drive;
	private VariadicControl joy1x, joy1y, joy1rot;

	public void robotInit() {
		drive = new RobotDrive(
			new CalibratedSpeedController(new Jaguar(Port.FL_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
			new CalibratedSpeedController(new Jaguar(Port.FR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
			new CalibratedSpeedController(new Jaguar(Port.BL_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
			new CalibratedSpeedController(new Jaguar(Port.BR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0))
		);
	}

	public void autonomousPeriodic() {

	}

	public void teleopInit() {
		Controller joystick1 = new MyJoystick(Port.JOYSTICK1); // TODO make sure that this is the correct port
		Controller joystick2 = new MyJoystick(Port.JOYSTICK2);
		VariadicControl
			joy1xRaw = new VariadicInput(joystick1, Port.JOY1X),
			joy1yRaw = new VariadicInput(joystick1, Port.JOY1Y),
			joy2xRaw = new VariadicInput(joystick2, Port.JOY2X),
			joy2yRaw = new VariadicInput(joystick2, Port.JOY2Y);
		BooleanControl
			fullSpeed   = new BooleanInput(joystick1, Port.FULL_SPEED_BTN), // TODO these ports are made up
			grannySpeed = new BooleanInput(joystick1, Port.GRANNY_SPEED_BTN);
		
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
		// TODO if after testing it is found that there is lag when pressing buttons, move this to teleopContinuous
		Control.tickAll();
		drive.mecanumDrive_Cartesian(joy1x.getValue(), joy1y.getValue(), joy1rot.getValue(), 0);
	}
}
