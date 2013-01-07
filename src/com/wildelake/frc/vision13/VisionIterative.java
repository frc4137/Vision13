/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;


import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.ControllerManager;
import com.wildelake.frc.vision13.controls.MyJoystick;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class VisionIterative extends IterativeRobot {
	private ControllerManager controls;
	private MyRobotDrive drive;
	private AutoPilot auto;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	// Joystick Manager: Wrapper for normal Joystick objects, allowing for
    	// calling methods based on button pushes and disabling and enabling
    	// buttons. Ports used: 0, 1
    	controls = new ControllerManager(new Controller[] {
			new MyJoystick(0), new MyJoystick(1)
		});
    	
    	// Robot Drive: Initializes the drive system. Ports used: 0, 1, 2, 3.
    	drive = new MyRobotDrive(0, 1, 2, 3);
    	
    	// Autopilot: Runs periodically, returning events for processing during autonomous
    	auto = new AutoPilot(drive, controls);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	String[] events = auto.getEvents();
    	for (int i = 0; i < events.length; i++) {
    		// TODO: Process Events from the AutoPilot
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
}
