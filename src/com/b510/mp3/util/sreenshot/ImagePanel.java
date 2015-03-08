package com.b510.mp3.util.sreenshot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * @author Hongten
 * @created 2014-7-28
 */
public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected Image img;

	public ImagePanel() {
		super();
	}

	public ImagePanel(String imgUrl) {
		super();
		img = new ImageIcon(this.getClass().getClassLoader().getResource(imgUrl)).getImage();
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setOpaque(false);
		setLayout(null);

	}

	public ImagePanel(Image img) {
		super();
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setOpaque(false);
		setLayout(null);
	}

	public void refreshImg(Image img) {
		this.img = img;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

}
