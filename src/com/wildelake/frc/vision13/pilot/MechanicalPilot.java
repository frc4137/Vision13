package com.wildelake.frc.vision13.pilot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;

/**
 * MechanicalPilot is the primary set of controls for Mechanical on 03-28-12
 */
public class MechanicalPilot implements Pilot {
	private DriverStationLCD dsl;
	private Compressor c;
	public MechanicalPilot() {
		c = new Compressor(6, 6);
		c.start();
		dsl = DriverStationLCD.getInstance();
	}
	
	public void tick() {
		dsl.println(Line.kMain6, 1, "Don't SPACEBRO");
		dsl.println(Line.kUser2, 1, "DigIO: " + c.getPressureSwitchValue());
		dsl.updateLCD();
	}

}
