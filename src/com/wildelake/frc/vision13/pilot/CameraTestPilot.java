package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.camera.RangeFinder;
import com.wildelake.frc.vision13.camera.Stereoscope;
import com.wildelake.frc.vision13.controls.BooleanInput;
import com.wildelake.frc.vision13.controls.Controller;
import com.wildelake.frc.vision13.controls.compositions.*;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * CameraTestPilot shows range from a SScope on the DSL
 */
public class CameraTestPilot extends ControlSet implements Pilot {
	private final RangeFinder sscope;
	private DriverStationLCD dsl;
	private int time;
	private BooleanControl archive;
	private Controller joystick1;
	
	public CameraTestPilot(Controller joystick1, RangeFinder sscope) {
		this.sscope = sscope;
		this.joystick1 = joystick1;
		dsl = DriverStationLCD.getInstance();
		time = 0;
		Control.buildControlSet(this);
	}
	
	public void buildControlSet() {
		archive = new ToggleBooleanControl(
			new OrGateBooleanControl(new BooleanControl[] {
				new BooleanInput(joystick1, 2),
				new BooleanInput(joystick1, 3),
				new BooleanInput(joystick1, 4),
				new BooleanInput(joystick1, 5)}));
	}
	
	public void update() {
		if (time % 180 == 0) {
			sscope.refresh();
			SmartDashboard.putString("anaFeed", "U LOST?");
		}
		time++;
		dsl.println(Line.kMain6, 1, "Don't Press SPACEBRO");
		if (archive.getValue()) ((Stereoscope) sscope).archivePhotos();
		System.out.println(sscope.getDepth());
		((Stereoscope) sscope).debugReports();
		dsl.println(Line.kUser2, 1, "Current Range: "+sscope.getDepth()+"                           ");
		dsl.updateLCD();
	}

}
