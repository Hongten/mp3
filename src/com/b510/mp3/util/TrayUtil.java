/**
 * 
 */
package com.b510.mp3.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

/**
 * @author Hongten
 * @created Jul 28, 2014
 */
public class TrayUtil extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private Image icon;
	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu popupMenu = new PopupMenu();

	private MenuItem exitItem;
	private MenuItem restoreItem;
	private MenuItem changeThemeItem;
	private MenuItem changeSkinItem;
	private MenuItem changeWatermarkItem;
	private MenuItem screenshotItem;

	private MainUI mainUI;

	public TrayUtil(MainUI mainUI) {
		this.mainUI = mainUI;
		initTray();
	}

	public void initTray() {
		if (SystemTray.isSupported()) {
			generateTrayIcon();
			generatePopuppMenu();
			systemTray = SystemTray.getSystemTray();
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	private void generateTrayIcon() {
		icon = new ImageIcon(this.getClass().getClassLoader().getResource(Common.TRAY_ICON)).getImage();
		trayIcon = new TrayIcon(icon, Common.OPEN_MAIN_PANEL, popupMenu);
		trayIcon.addMouseListener(this);
	}

	private void generatePopuppMenu() {
		changeThemeItem = new MenuItem(Common.CHANGE_THEME);
		changeThemeItem.addActionListener(this);
		popupMenu.add(changeThemeItem);

		changeSkinItem = new MenuItem(Common.CHANGE_SKIN);
		changeSkinItem.addActionListener(this);
		popupMenu.add(changeSkinItem);

		changeWatermarkItem = new MenuItem(Common.CHANGE_WATER_MARK);
		changeWatermarkItem.addActionListener(this);
		popupMenu.add(changeWatermarkItem);

		screenshotItem = new MenuItem(Common.SCREENSHOT);
		screenshotItem.addActionListener(this);
		popupMenu.add(screenshotItem);

		restoreItem = new MenuItem(Common.RESTORE);
		restoreItem.addActionListener(this);
		popupMenu.add(restoreItem);

		popupMenu.addSeparator();

		exitItem = new MenuItem(Common.EXIT);
		exitItem.addActionListener(this);
		popupMenu.add(exitItem);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitItem) {
			int option = JOptionPane.showConfirmDialog(TrayUtil.this, Common.EXIT_SYSTEM, Common.EXIT_SYSTEM, JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else if (e.getSource() == restoreItem) {
			mainUI.setVisible(true);
			// mainUI.setExtendedState(JFrame.NORMAL);
		} else if (e.getSource() == changeThemeItem) {
			mainUI.changeThemeOperation();
		} else if (e.getSource() == changeSkinItem) {
			mainUI.changeSkinOperation();
		} else if (e.getSource() == changeWatermarkItem) {
			mainUI.changeWatermarkOperation();
		} else if (e.getSource() == screenshotItem) {
			ScreenshotUtil.screenshot(this.mainUI);
			ScreenshotUtil.deskTopCapture = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ((e.getClickCount() == 1 || e.getClickCount() == 2) && e.getButton() != MouseEvent.BUTTON3) {
			if (mainUI.isVisible()) {
				mainUI.setVisible(false);
			} else {
				mainUI.setVisible(true);
			}
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

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
