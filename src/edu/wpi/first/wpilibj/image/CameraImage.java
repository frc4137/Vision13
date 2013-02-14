package edu.wpi.first.wpilibj.image;

public class CameraImage extends HSLImage {
	private boolean used = true;
	private final CameraImageManager manager;

	public CameraImage(CameraImageManager manager) throws NIVisionException {
		this.manager = manager;
	}

	public CameraImage(HSLImage sourceImage, CameraImageManager manager) {
		super(sourceImage);
		this.manager = manager;
	}

	public CameraImage(String fileName, CameraImageManager manager) throws NIVisionException {
		super(fileName);
		this.manager = manager;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void free() {
		used = false;
	}
	
	public void dealloc() throws NIVisionException {
		super.free();
	}
}
