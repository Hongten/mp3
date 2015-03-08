/**
 * 
 */
package com.b510.mp3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The <code>Mp3UI</code> class extends <code>JUI</code> and implements <code>ActionListener</code>.
 * @author Hongten
 * @created Aug 1, 2014
 */
public class Mp3UI extends JUI implements ActionListener {
	private static final long serialVersionUID = 1L;

	private MainUI mainUI;

	public Mp3UI(String title) {
		super(title);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void init() {
		if (null == mainUI) {
			mainUI = new MainUI(title);
		}
		mainUI.init();
	}

}
