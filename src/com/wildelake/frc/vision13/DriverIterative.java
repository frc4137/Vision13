/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.components.*;
import com.wildelake.frc.vision13.controls.MyJoystick;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

import com.wildelake.frc.vision13.pilot.ManualPilot;
import com.wildelake.frc.vision13.pilot.Pilot;
import com.wildelake.frc.vision13.utils.*;

public class DriverIterative extends IterativeRobot {
	private RobotDrive drive;
	private Pilot manual;
	private SpeedController fireMotor;

	public void robotInit() {
		drive = new RobotDrive(
			new RampedSpeedController(new CalibratedSpeedController(new Jaguar(Port.BL_MOTOR), Function.calibrator(0, -1, 0, 0, -1, 0)), Config.getRamp()),
			new RampedSpeedController(new CalibratedSpeedController(new Jaguar(Port.BR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)), Config.getRamp()),
			new RampedSpeedController(new CalibratedSpeedController(new Jaguar(Port.FL_MOTOR), Function.calibrator(0, -1, 0, 0, -1, 0)), Config.getRamp()),
			new RampedSpeedController(new CalibratedSpeedController(new Jaguar(Port.FR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)), Config.getRamp())
		);
		fireMotor = new Jaguar(Port.FIRE_MOTOR);
	}

	public void teleopInit() {
		manual = new ManualPilot(new MyJoystick(Port.JOYSTICK1), drive, fireMotor);
	}

	public void teleopPeriodic() {
		manual.tick();
	}
}
