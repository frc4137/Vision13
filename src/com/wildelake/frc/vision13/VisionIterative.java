/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.Port;
import com.wildelake.frc.vision13.camera.*;
import com.wildelake.frc.vision13.components.*;
import com.wildelake.frc.vision13.controls.MyJoystick;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

import com.wildelake.frc.vision13.pilot.*;
import com.wildelake.frc.vision13.utils.*;

public class VisionIterative extends IterativeRobot {
	private RobotDrive drive;
	private Pilot manual;
	private SpeedController fireMotor;
	private SpeedController[] driveMotors;
	private Piston shooterTable, kicker;

	public void robotInit() {
		driveMotors = new SpeedController[] {
			new Jaguar(Port.BL_MOTOR),
			new Jaguar(Port.BR_MOTOR),
			new Jaguar(Port.FL_MOTOR),
			new Jaguar(Port.FR_MOTOR)
		};
		Function[] calibrators = new Function[] {
			Function.calibrator( 0.006, -1.052,  0.053,  0.004, -1.033,  0.070), // Back Left
			Function.calibrator(-0.005,  1.031, -0.083, -0.005,  1.041, -0.066), // Back Right
			Function.calibrator( 0.006, -1.062,  0.120,  0.004, -1.028,  0.079), // Front Left
			Function.calibrator(-0.004,  1.031, -0.089, -0.005,  1.046, -0.082)  // Front Right
		};
		for (int i = 0; i < driveMotors.length; i++) {
//			driveMotors[i] = new RampedSpeedController(driveMotors[i], Config.getRamp());
			driveMotors[i] = new CalibratedSpeedController(driveMotors[i], calibrators[i]);
		}
		drive = new RobotDrive(driveMotors[0], driveMotors[1], driveMotors[2], driveMotors[3]);
//		fireMotor = new Jaguar(Port.FIRE_MOTOR);
		
//		shooterTable = new Piston(new Solenoid(Port.SHOOTER_TABLE_UP), new Solenoid(Port.SHOOTER_TABLE_DOWN));
//		kicker = new Piston(new Solenoid(Port.KICKER_OUT), new Solenoid(Port.KICKER_IN));
		
//		Camera left  = new NonSingletonAxisCamera("10.41.37.11");
//		Camera right = new NonSingletonAxisCamera("10.41.37.12");
		
//		manual = new CameraTestPilot(
//			new MyJoystick(Port.JOYSTICK1),
//			new Stereoscope(left, right) {
//				public BinaryImage threshold(ColorImage img) throws NIVisionException {
//					if (img == null) {
//						System.out.println("NULL");
//						return null;
//					}
//					return img.thresholdHSL(88, 198, 33, 186, 167, 255);
//				}
//			});
//		manual = new ManualPilot(new MyJoystick(Port.JOYSTICK1), new MyJoystick(Port.JOYSTICK2), drive, fireMotor, shooterTable, kicker);
//		manual = new CalibrationPilot(new MyJoystick(Port.JOYSTICK1), driveMotors);
//		manual = new AutoPilot(
//			new MyJoystick(Port.JOYSTICK1),
//			drive,
//			fireMotor,
//			new Stereoscope(left, right) {
//				public BinaryImage threshold(ColorImage img) throws NIVisionException {
//					if (img == null) return null;
//					// Numbers below are values that the target should be due to lighting and
//					// electrical tape, derived using histograms from actual images in NI Vision Assistant
//					return img.thresholdHSL(88, 198, 33, 186, 167, 255);
//				}
//			});
		manual = new MechanicalPilot();
	}

	public void teleopPeriodic() {
		manual.tick();
	}
	public void disabledPeriodic() {
		System.out.println("You require more Vespene Gas!");
	}
	public void disabledInit() {
		System.out.println("You must construct additional pylons!");
	}
	public void teleopInit() {
		System.out.println("My life for Aiur!");
	}
	public void autonomousInit() {
		System.out.println("I'm sorry Dave, I'm afraid I can't do that.");
	}
}
