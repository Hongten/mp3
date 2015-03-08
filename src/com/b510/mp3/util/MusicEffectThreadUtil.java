/**
 * 
 */
package com.b510.mp3.util;

import java.util.Random;

import javax.swing.JProgressBar;

import com.b510.mp3.ui.MainUI;

/**
 * @author Hongten
 * @created Jul 31, 2014
 */
public class MusicEffectThreadUtil extends Thread {

	private JProgressBar soundEffectBarA;
	private JProgressBar soundEffectBarB;
	private JProgressBar soundEffectBarC;
	private JProgressBar soundEffectBarD;

	public MusicEffectThreadUtil(JProgressBar barA, JProgressBar barB, JProgressBar barC, JProgressBar barD) {
		this.soundEffectBarA = barA;
		this.soundEffectBarB = barB;
		this.soundEffectBarC = barC;
		this.soundEffectBarD = barD;
	}

	@Override
	public void run() {
		try {
			Random random = new Random();
			while (!MainUI.isStop) {
				Thread.sleep(10);
				soundEffectBarD.setValue(random.nextInt(100));
				soundEffectBarA.setValue(random.nextInt(100));
				soundEffectBarC.setValue(random.nextInt(100));
				soundEffectBarB.setValue(random.nextInt(100));
			}
			soundEffectBarA.setValue(0);
			soundEffectBarB.setValue(0);
			soundEffectBarC.setValue(0);
			soundEffectBarD.setValue(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
