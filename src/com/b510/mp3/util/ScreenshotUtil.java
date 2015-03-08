/**
 * 
 */
package com.b510.mp3.util;

import com.b510.mp3.ui.MainUI;
import com.b510.mp3.util.sreenshot.DesktopCapture;

/**
 * @author Hongten
 * @created 2014-7-28
 */
public class ScreenshotUtil {

	public static DesktopCapture deskTopCapture;

	public static void screenshot(MainUI mainUI) {
		deskTopCapture = new DesktopCapture(mainUI);
	}
}
