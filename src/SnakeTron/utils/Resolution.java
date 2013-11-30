package SnakeTron.utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * 
 * @author Yoann CAPLAIN
 *
 */
public class Resolution implements Comparable<Resolution> {

	private int width;
	private int height;

	public Resolution(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public String toString() {
		return width + "x" + height;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Resolution) {
			Resolution r = (Resolution) obj;
			return this.width == r.width && height == r.height;
		} else
			return false;
	}

	@Override
	public int compareTo(Resolution o) {
		if (o.getWidth() == width) {
			return this.height - o.getHeight();
		} else
			return this.width - o.getWidth();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public static DisplayMode[] getAvailableResolution(){
		//Pour des tests
		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			/*
			  for (int i=0;i<modes.length;i++) {
	            DisplayMode current = modes[i];
	            System.out.println(current.getWidth() + "x" + current.getHeight() + "x" +
	                                current.getBitsPerPixel() + " " + current.getFrequency() + "Hz full: "+  current.isFullscreenCapable());
	        }//*/
			return modes;
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
