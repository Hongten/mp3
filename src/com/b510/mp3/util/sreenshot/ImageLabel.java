package com.b510.mp3.util.sreenshot;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Hongten
 * @created 2014-7-28
 */
public class ImageLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public ImageLabel(String imgURL) {
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(imgURL));
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);

	}

	public ImageLabel(ImageIcon icon) {
		setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
		setIcon(icon);
		setIconTextGap(0);
		setBorder(null);
		setText(null);
		setOpaque(false);
	}

}
