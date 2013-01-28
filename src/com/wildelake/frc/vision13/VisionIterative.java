/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.Port;
import com.wildelake.frc.vision13.camera.MyAxisCamera;
import com.wildelake.frc.vision13.camera.Stereoscope;
import com.wildelake.frc.vision13.components.*;
import com.wildelake.frc.vision13.controls.MyJoystick;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

import com.wildelake.frc.vision13.pilot.*;
import com.wildelake.frc.vision13.pilot.Pilot;
import com.wildelake.frc.vision13.utils.*;

public class VisionIterative extends IterativeRobot {
	private RobotDrive drive;
	private Pilot manual;
	private SpeedController fireMotor;
	private SpeedController[] driveMotors;

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
		for (int i = 0; i < 4; i++) {
			driveMotors[i] = new RampedSpeedController(driveMotors[i], Config.getRamp());
			driveMotors[i] = new CalibratedSpeedController(driveMotors[i], calibrators[i]);
		}
		drive = new RobotDrive(driveMotors[0], driveMotors[1], driveMotors[2], driveMotors[3]);
		fireMotor = new Jaguar(Port.FIRE_MOTOR);
	}

	public void teleopInit() {
		manual = new ManualPilot(new MyJoystick(Port.JOYSTICK1), drive, fireMotor);
//		manual = new CameraTestPilot(new Stereoscope(new MyAxisCamera(AxisCamera.getInstance("10.41.37.11")), new MyAxisCamera(AxisCamera.getInstance("10.41.37.12"))) {
//			public BinaryImage threshold(ColorImage img)
//					throws NIVisionException {
//				if (img == null) {
//					System.out.println("NULL");
//					return null;
//				}
//				return img.thresholdHSL(76, 210, 3, 160, 170, 255);
////				return img.thresholdHSL(25, 255, 0, 45, 0, 47);
//			}
//		});
//		manual = new CalibrationPilot(new MyJoystick(Port.JOYSTICK1), driveMotors);
	}

	public void teleopPeriodic() {
		manual.tick();
	}
	
	// FOR DEBUGGING W/O THE DRIVER STATION ONLY, USE WITH CAUTION
//	public void disabledInit() { teleopInit(); }
//	public void disabledPeriodic() { teleopPeriodic(); }
}
