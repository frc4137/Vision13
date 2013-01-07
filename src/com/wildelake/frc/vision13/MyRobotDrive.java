package com.wildelake.frc.vision13;

import edu.wpi.first.wpilibj.RobotDrive;
/**
 * Class MyRobotDrive
 * extends RobotDrive to incorporate Location and MotorSpeed information for smarter driving
 * Overrides arcadeDrive to update location
 * Provides getLocation, getMotorSpeed
 * @author Satyajit DasSarma
 *
 */
public class MyRobotDrive extends RobotDrive {
	private Location whereAmI;
	private long lastCalled;
	public MyRobotDrive(int frontLeftMotor, int rearLeftMotor,
			int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		whereAmI = new Location(0,0,0);
		setLastCalled(System.currentTimeMillis());
	}
	
	public Location getLocation() {
		return whereAmI;
	}
	
	/**
	 * public double[] getMotorSpeed()
	 * @return {frontLeft, frontRight,
	 * 			backLeft,  backRight}
	 */
	public double[] getMotorSpeed() {
		return new double[] {
			this.m_frontLeftMotor.get(), this.m_frontRightMotor.get(),
			this.m_rearLeftMotor .get(), this.m_rearRightMotor .get()
		};
	}
	
	@Override
	public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
        // local variables to hold the computed PWM values for the motors
        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        if (squaredInputs) {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
            if (rotateValue >= 0.0) {
                rotateValue = (rotateValue * rotateValue);
            } else {
                rotateValue = -(rotateValue * rotateValue);
            }
        }

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }

        setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
        // TODO Fix the code below for setting location
        // whereAmI.move(x, y);
        setLastCalled(System.currentTimeMillis());
	}

	/**
	 * @return the lastCalled
	 */
	public long getLastCalled() {
		return lastCalled;
	}

	/**
	 * @param lastCalled the lastCalled to set
	 */
	public void setLastCalled(long lastCalled) {
		this.lastCalled = lastCalled;
	}
	
}
