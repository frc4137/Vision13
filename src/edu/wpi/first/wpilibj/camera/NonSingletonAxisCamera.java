/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.camera;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import javax.microedition.io.*;

import com.sun.cldc.jna.Pointer;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.parsing.ISensor;

public class NonSingletonAxisCamera implements ISensor {
    
    private String IPAddress;
    private int i;
    
    /**
     * Get a reference to the AxisCamera, or initialize the AxisCamera if it
     * has not yet been initialized. If the camera is connected to the
     * Ethernet switch on the robot, then this address should be 10.x.y.11
     * where x.y are your team number subnet address (same as the other IP
     * addresses on the robot network).
     * @param address A string containing the IP address for the camera in the
     * form "10.x.y.2" for cameras on the Ethernet switch or "192.168.0.90"
     * for cameras connected to the 2nd Ethernet port on an 8-slot cRIO.
     * @return A reference to the AxisCamera.
     */
    public static synchronized NonSingletonAxisCamera getInstance(String address) {
        return new NonSingletonAxisCamera(address);
    }

    /**
     * Get a reference to the AxisCamera, or initialize the AxisCamera if it
     * has not yet been initialized. By default this will connect to a camera
     * with an IP address of 10.x.y.11 with the preference that the camera be
     * connected to the Ethernet switch on the robot rather than port 2 of the
     * 8-slot cRIO.
     * @return A reference to the AxisCamera.
     */
    public static synchronized NonSingletonAxisCamera getInstance() {
        return new NonSingletonAxisCamera("10.41.37.11");
    }

    
    /**
     * Axis camera constructor that calls the C++ library to actually create the instance.
     * @param IPAddress 
     */
    NonSingletonAxisCamera(String IPAddress) {
    	this.IPAddress = IPAddress;
    	i = 0;
    }

    /**
     * Get an image from the camera. Be sure to free the image when you are done with it.
     * @return A new image from the camera.
     */
    public ColorImage getImage() throws AxisCameraException, NIVisionException {
        ColorImage image = new HSLImage("/tmp/original.png");
        try {
            HttpConnection connection = (HttpConnection) Connector.open("http://"+IPAddress+"/axis-cgi/jpg/image.cgi");
            connection.setRequestMethod(HttpConnection.GET);
            DataInputStream s = connection.openDataInputStream();
            StringBuffer imageBuffer = new StringBuffer();
        	try   { while (true) imageBuffer.append(s.readByte()); }
        	catch (EOFException e) { }
        	System.out.println(imageBuffer.toString());
			NIVision.readJpegString(image.image, Pointer.createStringBuffer(imageBuffer.toString()));
			System.out.println(image.getWidth()+"x"+image.getHeight()+" "+image.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return image;
    }
    /**
     * Has the current image from the camera been retrieved yet.
     * @return true if the latest image from the camera has not been retrieved yet.
     */
    public boolean freshImage() {
    	i++;
        return i % 100 !=0;
    }
}
