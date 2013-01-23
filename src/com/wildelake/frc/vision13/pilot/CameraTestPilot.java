package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.camera.Stereoscope;
import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * CameraTestPilot shows range from a SScope on the DSL
 */
public class CameraTestPilot implements Pilot {
	private final Stereoscope sscope;
	private DriverStationLCD dsl;
	private int time;
	
	public CameraTestPilot(Stereoscope sscope) {
		this.sscope = sscope;
		dsl = DriverStationLCD.getInstance();
		time = 0;
	}	
	
	public void tick() {
		if (time % 10 == 0) sscope.refresh();
		time++;
		dsl.println(Line.kMain6, 1, "Don't Press SPACEBAR");
		System.out.println(sscope.getDepth());
		sscope.debugReports();
		dsl.println(Line.kUser2, 1, "Current Range: "+sscope.getDepth()+"                           ");
		dsl.updateLCD();
//		drive.tankDrive(foo.getValue(), foo.getValue());
	}

}
