/**
 * 
 */
package com.b510.mp3.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

/**
 * @author Hongten
 * @created Jul 31, 2014
 */
public class MusicUtil {

	boolean isStop = true;
	boolean hasStop = true;

	AudioInputStream audioInputStream;
	AudioFormat audioFormat;
	SourceDataLine sourceDataLine;
	public static int audioLength; // Length of the sound.

	Thread playThread;
	MainUI mainUI;

	public synchronized void play(File file) {
		try {
			isStop = true;
			audioInputStream = getIudioInputStream(file);
			audioFormat = getAudioFormat(audioInputStream);

			audioFormat = transformAudioEncoding(audioFormat);
			audioInputStream = updateAudioInputStream(audioInputStream, audioFormat);

			sourceDataLine = startSourceDataLine(audioFormat);

			isStop = false;
			playThread = new Thread(new MusicPlayThreadUtil(this, mainUI));
			playThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void suspend() {

	}

	public void stop() {
		if (null != playThread) {
			if (playThread.isAlive()) {
				isStop = true;
			}
		}
	}

	public void showPlayThreadStatus() {
		if (null != playThread) {
			if (playThread.isAlive()) {
				System.out.println("playThread is alive");
			} else {
				System.out.println("playThread is dead..");
				playThread = null;
			}
		} else {
			System.out.println("playThread is null");
		}
	}

	private AudioInputStream getIudioInputStream(File file) throws IOException, UnsupportedAudioFileException {
		return AudioSystem.getAudioInputStream(file);
	}

	private AudioFormat getAudioFormat(AudioInputStream audioInputStream) {
		return audioInputStream.getFormat();
	}

	/**
	 * Transform audio(mp3) encoding.
	 */
	private AudioFormat transformAudioEncoding(AudioFormat audioFormat) {
		if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16, audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(), false);
		}
		return audioFormat;
	}

	private AudioInputStream updateAudioInputStream(AudioInputStream audioInputStream, AudioFormat audioFormat) {
		return AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
	}

	private SourceDataLine startSourceDataLine(AudioFormat audioFormat) throws LineUnavailableException {
		Info dataLineInfo = new Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
		SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sourceDataLine.open(audioFormat);
		sourceDataLine.start();
		audioLength = sourceDataLine.getBufferSize();
//		System.out.println(audioLength);
		return sourceDataLine;
	}

	public void setMainUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}
	
	public SourceDataLine getSourceDataLine(){
		return this.sourceDataLine;
	}

	// for test
	public static void main(String[] args) {
		String path = MusicUtil.class.getClassLoader().getResource(Common.ONE_DAY_MUSIC).toString();
		path = path.substring(6, path.length());
		MusicUtil musicUtil = new MusicUtil();
		File file = new File(path);
		musicUtil.play(file);

		try {
			Thread.sleep(3000);
			musicUtil.stop();

			Thread.sleep(2000);
			musicUtil.showPlayThreadStatus();

			Thread.sleep(2000);
			musicUtil.showPlayThreadStatus();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
