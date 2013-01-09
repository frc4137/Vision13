/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.components.CalibratedSpeedController;
import com.wildelake.frc.vision13.controls.MyJoystick;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;

import com.wildelake.frc.vision13.pilot.MechanicalPilot;
import com.wildelake.frc.vision13.pilot.Pilot;
import com.wildelake.frc.vision13.utils.*;

public class VisionIterative extends IterativeRobot {
	private RobotDrive drive;
	private Pilot manual;

	public void robotInit() {
		drive = new RobotDrive(
			new CalibratedSpeedController(new Jaguar(Port.FL_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
			new CalibratedSpeedController(new Jaguar(Port.FR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
			new CalibratedSpeedController(new Jaguar(Port.BL_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
			new CalibratedSpeedController(new Jaguar(Port.BR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0))
		);
	}

	public void teleopInit() {
		manual = new MechanicalPilot(new MyJoystick(Port.JOYSTICK1), drive);
	}

	public void teleopPeriodic() {
		manual.tick();
	}
}
