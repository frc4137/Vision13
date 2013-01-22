/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;

import com.wildelake.frc.vision13.camera.MyAxisCamera;
import com.wildelake.frc.vision13.camera.Stereoscope;
//import com.wildelake.frc.vision13.components.CalibratedSpeedController;
//import com.wildelake.frc.vision13.controls.MyJoystick;

import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

import com.wildelake.frc.vision13.pilot.CameraTestPilot;
//import com.wildelake.frc.vision13.pilot.ManualPilot;
//import com.wildelake.frc.vision13.pilot.MechanicalPilot;
import com.wildelake.frc.vision13.pilot.Pilot;
//import com.wildelake.frc.vision13.utils.*;

public class VisionIterative extends IterativeRobot {
	private RobotDrive drive;
	private Pilot manual;
	private SpeedController fireMotor;

	public void robotInit() {
//		drive = new RobotDrive(
//			new CalibratedSpeedController(new Jaguar(Port.FL_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
//			new CalibratedSpeedController(new Jaguar(Port.FR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
//			new CalibratedSpeedController(new Jaguar(Port.BL_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0)),
//			new CalibratedSpeedController(new Jaguar(Port.BR_MOTOR), Function.calibrator(0, 1, 0, 0, 1, 0))
//		);
//		fireMotor = new Jaguar(Port.FIRE_MOTOR);
	}

	public void teleopInit() {
//		manual = new ManualPilot(new MyJoystick(Port.JOYSTICK1), drive, fireMotor);
		manual = new CameraTestPilot(new Stereoscope(new MyAxisCamera(AxisCamera.getInstance("10.41.37.11")), new MyAxisCamera(AxisCamera.getInstance("10.41.37.12"))) {
			public BinaryImage threshold(ColorImage img)
					throws NIVisionException {
				 return img.thresholdHSL(160, 200, 127, 255, 50, 180);
			}
		});
	}

	public void teleopPeriodic() {
		manual.tick();
	}
}
