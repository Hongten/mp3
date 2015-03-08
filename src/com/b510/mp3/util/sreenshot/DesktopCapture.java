package com.b510.mp3.util.sreenshot;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

public class DesktopCapture extends JFrame{
	private static final long serialVersionUID = 1L;
	
	BufferedImage desktopImg;
	CaptureView view;
	boolean iconed = false;
	
	private MainUI mainUI;

	public DesktopCapture(MainUI mainUI) {
		this.mainUI = mainUI;
		capture();
	}

	public void captureDesktop() throws Exception {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(d);
		desktopImg = new BufferedImage((int) d.getWidth(), (int) d.getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		Robot robot = new Robot(device);
		desktopImg = robot.createScreenCapture(rect);
	}

	public void capture() {
		this.setVisible(false);
		try {
			Thread.sleep(200);
			captureDesktop();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (view == null) {
			view = new CaptureView(this, desktopImg, this.mainUI);
		} else {
			view.refreshBackGround(desktopImg);
		}
	}

	public void saveCapture(int x1, int y1, int x2, int y2) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("*.gif", "gif"));
		chooser.setFileFilter(new FileNameExtensionFilter("*.bmp", "bmp"));
		chooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
		chooser.setSelectedFile(new File(chooser.getCurrentDirectory(), Common.SCREENSHOT));
		int state = chooser.showSaveDialog(this);
		if (state == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			FileFilter filter = chooser.getFileFilter();
			String descrition = filter.getDescription();
			String endWith = descrition.substring(2);
			captureToFile(desktopImg.getSubimage(x1, y1, Math.abs(x2 - x1),
					Math.abs(y2 - y1)), endWith, new File(file
					.getAbsoluteFile()
					+ Common.FULL_SPOT + endWith));
		}
	}

	public void captureToFile(BufferedImage img, String endWith, File file) {
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			ImageIO.write(img, endWith, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, Common.SCREENSHOT_ERROR);
			e.printStackTrace();
		}
	}
}
