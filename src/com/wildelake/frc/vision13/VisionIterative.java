/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.wildelake.frc.vision13;


import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class VisionIterative extends IterativeRobot {
	private MyRobotDrive drive;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	// Robot Drive: Initializes the drive system. Ports used: 0, 1, 2, 3.
    	drive = new MyRobotDrive(0, 1, 2, 3);
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    }

    /**
     * This function is run when the robot first enters teleop mode
     */
    public void teleopInit() {
    	
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        final MyRobotDrive drive = this.drive;
        
        
    }
    
}
