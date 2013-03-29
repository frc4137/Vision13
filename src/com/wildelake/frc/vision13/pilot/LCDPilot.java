package com.wildelake.frc.vision13.pilot;

import com.wildelake.frc.vision13.VisionIterative;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * LCDPilot updates the Driver Station LCD
 * @author adrusi
 */
public class LCDPilot implements Pilot {
    private DriverStationLCD dsl;
    public LCDPilot(DriverStationLCD dsl) {
        this.dsl = dsl;        
    }

    public void tick() {
        dsl.println(DriverStationLCD.Line.kMain6, 1, "Tick "+VisionIterative.tick+"       ");
        dsl.println(DriverStationLCD.Line.kUser2, 1, VisionIterative.mode+" mode");
        dsl.println(DriverStationLCD.Line.kUser3, 1, VisionIterative.userDef);
        dsl.println(DriverStationLCD.Line.kUser4, 1, VisionIterative.iterDef);
        dsl.println(DriverStationLCD.Line.kUser5, 1, "v1.1 updated 16:05");
        dsl.println(DriverStationLCD.Line.kUser6, 1, ""+VisionIterative.c.getPressureSwitchValue());
        dsl.updateLCD();
    }
    
}
