package com.b510.mp3.util.sreenshot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JTextArea;
import javax.swing.JWindow;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

public class CaptureView extends JWindow implements MouseListener, KeyListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private BufferedImage desktopImg;
	private boolean captured = false, draging = false, toolPanelAtRight = true;
	private int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 1, y2 = 1;
	private int point_x, point_y;
	private Color point_color;
	private DesktopCapture window;
	private ImagePanel toolPanel;
	private final int TOOLPANEL_WIDTH = 200, TOOLPANEL_HEIGHT = 300, HALF_PICK_IMG = 40;
	private JTextArea infoArea;
	private ToolImagePanel pickImgPanel;

	private MainUI mainUI;

	CaptureView(DesktopCapture window, BufferedImage img, MainUI mainUI) {
		super(window);
		this.mainUI = mainUI;

		this.window = window;
		this.desktopImg = img;
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		init();
		setVisible(true);
		setAlwaysOnTop(true);
		this.requestFocus();

	}

	void init() {
		this.setContentPane(new BackGroundPanel(desktopImg));
		setLayout(null);
		toolPanel = new ImagePanel(Common.SCREENSHOT_BG);
		toolPanel.setLayout(new BorderLayout());
		pickImgPanel = new ToolImagePanel();
		infoArea = new JTextArea();
		infoArea.setOpaque(false);
		infoArea.setEditable(false);
		infoArea.setForeground(Color.WHITE);
		infoArea.setFont(new Font(Common.FONT_NAME, Font.PLAIN, 11));
		infoArea.setText("");
		toolPanel.add(pickImgPanel, BorderLayout.CENTER);
		toolPanel.add(infoArea, BorderLayout.SOUTH);
		toolPanel.setLocation(getWidth() - TOOLPANEL_WIDTH, 0);
		toolPanel.setSize(TOOLPANEL_WIDTH, TOOLPANEL_HEIGHT);
		this.getLayeredPane().add(toolPanel, 300);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void refreshBackGround(BufferedImage img) {
		this.desktopImg = img;
		this.setContentPane(new BackGroundPanel(desktopImg));
		setVisible(true);
		setAlwaysOnTop(true);
		this.requestFocus();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		if (captured == true) {
			if (draging) {
				g.drawLine(point_x, 0, point_x, getHeight());
				g.drawLine(0, point_y, getWidth(), point_y);
			}
			confirmArea();
			if (x1 < x2 && y1 < y2)
				g.drawImage(desktopImg.getSubimage(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1)), x1, y1, null);
			g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
		} else {
			g.drawLine(point_x, 0, point_x, getHeight());
			g.drawLine(0, point_y, getWidth(), point_y);
		}
		repaintToolPanel();
	}

	public void repaintToolPanel() {
		if (toolPanelAtRight == true) {
			if (point_x > (getWidth() - TOOLPANEL_WIDTH - 100) && point_y < (TOOLPANEL_HEIGHT + 100)) {
				toolPanel.setLocation(0, 0);
				toolPanelAtRight = false;
			}
		} else {
			if (point_x < (TOOLPANEL_WIDTH + 100) && point_y < (TOOLPANEL_HEIGHT + 100)) {
				toolPanel.setLocation(getWidth() - TOOLPANEL_WIDTH, 0);
				toolPanelAtRight = true;
			}
		}
		point_color = new Color(desktopImg.getRGB(point_x, point_y));
		refreshInfoText();
	}

	public void refreshInfoText() {
		String text = new String(Common.OPERATION_GUIDE);
		String infoString;
		int captureWidth, captureHeight;
		if (captured == true) {
			captureWidth = x2 - x;
			captureHeight = y2 - y;
		} else {
			captureWidth = 0;
			captureHeight = 0;
		}
		infoString = Common.X_Y + point_x + Common.COMMA + point_y + Common.W_H + captureWidth + Common.STAR + captureHeight + Common.RGB + point_color.getRed() + Common.COMMA + point_color.getGreen() + Common.COMMA + point_color.getBlue() + Common.CLOSE_ROUND + text;
		infoArea.setText(infoString);

		int pick_x1, pick_y1, pick_x2, pick_y2, pickImg_x, pickImg_y;

		if (point_x - HALF_PICK_IMG < 0) {
			pick_x1 = 0;
			pick_x2 = point_x + HALF_PICK_IMG;
			pickImg_x = HALF_PICK_IMG - point_x;
		} else if (point_x + HALF_PICK_IMG > this.getWidth()) {
			pick_x1 = point_x - HALF_PICK_IMG;
			pick_x2 = this.getWidth();
			pickImg_x = 0;
		} else {
			pick_x1 = point_x - HALF_PICK_IMG;
			pick_x2 = point_x + HALF_PICK_IMG;
			pickImg_x = 0;
		}

		if (point_y - HALF_PICK_IMG < 0) {
			pick_y1 = 0;
			pick_y2 = point_y + HALF_PICK_IMG;
			pickImg_y = HALF_PICK_IMG - point_y;
		} else if (point_y + HALF_PICK_IMG > this.getHeight()) {
			pick_y1 = point_y - HALF_PICK_IMG;
			pick_y2 = this.getHeight();
			pickImg_y = 0;
		} else {
			pick_y1 = point_y - HALF_PICK_IMG;
			pick_y2 = point_y + HALF_PICK_IMG;
			pickImg_y = 0;
		}

		BufferedImage pickImg = new BufferedImage(HALF_PICK_IMG * 2, HALF_PICK_IMG * 2, BufferedImage.TYPE_INT_RGB);
		Graphics pickGraphics = pickImg.getGraphics();
		pickGraphics.drawImage(desktopImg.getSubimage(pick_x1, pick_y1, pick_x2 - pick_x1, pick_y2 - pick_y1), pickImg_x, pickImg_y, Color.black, null);
		pickImgPanel.refreshImg(pickImg.getScaledInstance(TOOLPANEL_WIDTH, TOOLPANEL_WIDTH, Image.SCALE_AREA_AVERAGING));
		toolPanel.validate();
	}

	public void confirmArea() {
		int temp;
		x1 = x;
		y1 = y;
		if (x2 < x1) {
			if (y2 < y1) {
				temp = x1;
				x1 = x2;
				x2 = temp;
				temp = y1;
				y1 = y2;
				y2 = temp;
			} else {
				temp = x1;
				x1 = x2;
				x2 = temp;
			}
		} else {
			if (y2 < y1) {
				temp = y1;
				y1 = y2;
				y2 = temp;
			}
		}
	}

	public void exit() {
		x = 0;
		y = 0;
		x1 = 0;
		y1 = 0;
		x2 = 1;
		y2 = 1;
		point_x = 0;
		point_y = 0;
		captured = false;
		draging = false;
		toolPanel.setLocation(getWidth() - TOOLPANEL_WIDTH, 0);
		toolPanelAtRight = true;
		this.setVisible(false);

		this.mainUI.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getClickCount() == 2) {
				this.setVisible(false);
				window.toFront();
				window.saveCapture(x1, y1, x2, y2);
				if (!window.iconed)
					window.setVisible(false);
				exit();
			}
		} else if (e.getClickCount() == 2) {
			if (!window.iconed) {
				window.setVisible(false);
				window.toFront();
			}
			exit();
		}
		if (window.isActive()) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (captured == false) {
				point_x = e.getX();
				point_y = e.getY();
				x = point_x;
				y = point_y;
				draging = true;
				captured = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (draging == true) {
				point_x = e.getX();
				point_y = e.getY();
				x2 = point_x;
				y2 = point_y;
				repaint();
				draging = false;
			}
		} else {
			draging = false;
			captured = false;
			point_x = e.getX();
			point_y = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (draging == true) {
			point_x = e.getX();
			point_y = e.getY();
			x2 = point_x;
			y2 = point_y;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!captured) {
			point_x = e.getX();
			point_y = e.getY();
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CANCEL) {
			this.setVisible(false);
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			this.setVisible(false);
			this.dispose();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
			this.dispose();
		}
	}

}
