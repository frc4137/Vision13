/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.camera;

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.*;

import com.sun.cldc.jna.Pointer;
import com.wildelake.frc.vision13.camera.Camera;

import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.parsing.ISensor;

public class NonSingletonAxisCamera implements ISensor, Camera {
    
    private String IPAddress;
    private int[] res;
    private int i;
    private int refreshRate;
    private HSLImage image;
    
    public static synchronized NonSingletonAxisCamera getInstance(String address, int[] res) {
        return new NonSingletonAxisCamera(address, 60, res);
    }
    
    /**
     * Get a reference to the AxisCamera. If the camera is connected to the
     * Ethernet switch on the robot, then this address should be 10.x.y.11
     * where x.y are your team number subnet address (same as the other IP
     * addresses on the robot network).
     * @param address A string containing the IP address for the camera in the
     * form "10.x.y.2" for cameras on the Ethernet switch or "192.168.0.90"
     * for cameras connected to the 2nd Ethernet port on an 8-slot cRIO.
     * @return A reference to the AxisCamera.
     */
    public static synchronized NonSingletonAxisCamera getInstance(String address) {
        return new NonSingletonAxisCamera(address, 30, new int[] {640, 480});
    }

    /**
     * Get a reference to the AxisCamera. By default this will connect to a camera
     * with an IP address of 10.x.y.11 with the preference that the camera be
     * connected to the Ethernet switch on the robot rather than port 2 of the
     * 8-slot cRIO.
     * @return A reference to the AxisCamera.
     */
    public static synchronized NonSingletonAxisCamera getInstance() {
        return new NonSingletonAxisCamera("10.41.37.11", 30, new int[] {640, 480});
    }

    
    /**
     * Axis camera constructor that initializes variables.
     * @param IPAddress 
     * @param refreshRate number of ticks during which to skip updates
     * @param res sets resolution of images
     */
    NonSingletonAxisCamera(String IPAddress, int refreshRate, int[] res) {
    	this.IPAddress = IPAddress;
    	i = 0;
    	this.refreshRate = refreshRate + 1;
    	this.res = res;
		try {
			image = new HSLImage("/tmp/original.png");
		} catch (NIVisionException e) {
			e.printStackTrace();
		}
    }

    /**
     * Get an image from the camera. Be sure to free the image when you are done with it.
     * @return A new image from the camera.
     */
    public ColorImage getImage() {
    	if (freshImage()) {
    		byte[] imageb = retrieve("http://"+IPAddress+"/axis-cgi/jpg/image.cgi?resolution=" + res[0] + "x" + res[1]);
    		StringBuffer imageBuffer = new StringBuffer();
    		for (int i = 0; i < imageb.length; i++) imageBuffer.append((char) imageb[i]);
    		NIVision.readJpegString(image.image, Pointer.createStringBuffer(imageBuffer.toString()));
    	}
        return image;
    }
    
    public void reGetImage(ColorImage image) {
    	if (freshImage()) {
    		byte[] imageb = retrieve("http://"+IPAddress+"/axis-cgi/jpg/image.cgi?resolution=" + res[0] + "x" + res[1]);
    		StringBuffer imageBuffer = new StringBuffer();
    		for (int i = 0; i < imageb.length; i++) imageBuffer.append((char) imageb[i]);
    		NIVision.readJpegString(image.image, Pointer.createStringBuffer(imageBuffer.toString()));
    	}
    }
    
    private byte[] retrieve(String s) {
    	HttpConnection  hpc = null;
    	DataInputStream dis = null;
    	byte[] data = null;
		try {
    	hpc = (HttpConnection) Connector.open(s);
		int length = (int) hpc.getLength();
		data = new byte[length];
		dis = new DataInputStream(hpc.openInputStream());
			dis.readFully(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
    }
    
    /**
     * Has the current image from the camera been retrieved yet.
     * @return true if the latest image from the camera has not been retrieved yet.
     */
    public boolean freshImage() {
    	i++;
        return i % refreshRate !=0;
    }
}
