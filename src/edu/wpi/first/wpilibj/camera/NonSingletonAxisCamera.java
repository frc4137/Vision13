/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.camera;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import javax.microedition.io.*;

import com.sun.cldc.jna.Pointer;
import com.wildelake.frc.vision13.camera.Camera;

import edu.wpi.first.wpilibj.image.CameraImage;
import edu.wpi.first.wpilibj.image.CameraImageManager;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.parsing.ISensor;

public class NonSingletonAxisCamera implements ISensor, Camera, CameraImageManager {
    
    private String IPAddress;
    private int width, height;
    private int i;
    private int refreshRate;
    private Vector usedImages;
    private Stack unusedImages;
    private ColorImage cache;
    
    private static final int DEFAULT_WIDTH = 640;
    private static final int DEFAULT_HEIGHT = 480;
    private static final int DEFAULT_REFRESH = 30;
    
    public NonSingletonAxisCamera(String address, int width, int height) {
        this(address, DEFAULT_REFRESH, width, height);
    }
    public NonSingletonAxisCamera(String address) {
        this(address, DEFAULT_REFRESH, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    public NonSingletonAxisCamera() {
        this("10.41.37.11", DEFAULT_REFRESH, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    
    /**
     * Axis camera constructor that initializes variables.
     * @param IPAddress 
     * @param refreshRate number of ticks during which to skip updates
     * @param res sets resolution of images
     */
    NonSingletonAxisCamera(String IPAddress, int refreshRate, int width, int height) {
    	this.IPAddress = IPAddress;
    	i = 0;
    	this.refreshRate = refreshRate + 1;
    	this.width = width;
    	this.height = height;
    	this.usedImages = new Vector();
    	this.unusedImages = new Stack();
		getImage();
    }

    /**
     * Get an image from the camera. Be sure to free the image when you are done with it.
     * @return A new image from the camera.
     */
    public ColorImage getImage() {
    	if (freshImage()) {
    		ColorImage image = getAndUseImage();
    		byte[] imageb = retrieve("http://"+IPAddress+"/axis-cgi/jpg/image.cgi?resolution=" + width + "x" + height);
    		StringBuffer imageBuffer = new StringBuffer();
    		for (int i = 0; i < imageb.length; i++) imageBuffer.append((char) imageb[i]);
    		NIVision.readJpegString(image.image, Pointer.createStringBuffer(imageBuffer.toString()));
    		cache = image;
    		return image;
    	}
    	return cache;
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
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return data;
    }
    
    /**
     * Tells whether the current image from the camera been retrieved yet.
     * @return true if the latest image from the camera has not been retrieved yet.
     */
    public boolean freshImage() {
    	// TODO actually do this right
    	i++;
        return i % refreshRate !=0;
    }
    
    public void imageFreed(CameraImage image) {
    	usedImages.removeElement(image);
    	if (unusedImages.size() < 5) {
    		unusedImages.push(image);
    	}
    	else {
    		try {
				image.dealloc();
			} catch (NIVisionException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private CameraImage getAndUseImage() {
    	if (unusedImages.size() > 0) {
    		CameraImage image = (CameraImage) unusedImages.pop();
    		usedImages.addElement(image);
    		return image;
    	}
    	CameraImage image = null;
		try {
			image = new CameraImage("/tmp/original.png", this);
		}
		catch (NIVisionException e) {
			System.err.println("Could not create new image.");
			e.printStackTrace();
		}
    	usedImages.addElement(image);
    	return image;
    }
}
