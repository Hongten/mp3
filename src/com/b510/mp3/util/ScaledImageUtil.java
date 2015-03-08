/**
 * 
 */
package com.b510.mp3.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.b510.mp3.common.Common;

/**
 * @author Hongten
 * @created 2014-7-26
 */
public class ScaledImageUtil {

	public static int snapHeightMax = 333;
	public static int snapWidthMax = 309;

	public static void scaledImage(String originalImagePath, int scaled, String scaledImagePath) {
		try {
			ImageIcon icon = getImage(ImageIO.read(new File(originalImagePath)), 5);
			BufferedImage savedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_3BYTE_BGR);
			savedImage.createGraphics().drawImage(icon.getImage(), 0, 0, null);
			ImageIO.write(savedImage, getPostfix(originalImagePath), new File(scaledImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPostfix(String inputFilePath) {
		return inputFilePath.substring(inputFilePath.lastIndexOf(Common.FULL_SPOT) + 1);
	}

	public static ImageIcon getImage(Image img, int scaled) {
		ImageIcon icon = new ImageIcon(getImages(img, scaled));
		return icon;
	}

	public static Image getImages(Image img, int scaled) {
		int heigth = 0;
		int width = 0;

		if (scaled < 25) {
			scaled = 25;
		}

		String sScaled = String.valueOf(Math.ceil((double) scaled / 25));
		int indexX = sScaled.indexOf(".");
		scaled = Integer.parseInt(sScaled.substring(0, indexX));

		double scaleds = getScaling(img.getWidth(null), img.getHeight(null), scaled);

		try {
			heigth = (int) (img.getHeight(null) * scaleds);
			width = (int) (img.getWidth(null) * scaleds);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getScaledImage(img, width, heigth);
	}

	public static Image getScaledImage(Image srcImg, int width, int heigth) {
		BufferedImage resizedImg = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, width, heigth, null);
		g2.dispose();

		srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_DEFAULT);

		return resizedImg;
	}

	/**
	 * Get the image zoom ratio
	 * 
	 * @param sourceWidth
	 * @param sourceHeight
	 * @return scaled
	 */
	public static double getScaling(int sourceWidth, int sourceHeight, int scaled) {
		double widthScaling = ((double) snapWidthMax * (double) scaled) / (double) sourceWidth;
		double heightScaling = ((double) snapHeightMax * (double) scaled) / (double) sourceHeight;

		double scaling = (widthScaling < heightScaling) ? widthScaling : heightScaling;

		return scaling;
	}
}
