/**
 * 
 */
package com.b510.mp3.util;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

/**
 * @author Hongten
 * @created Jul 31, 2014
 */
public class MusicPlayThreadUtil extends Thread {

	byte[] tempBuffer = new byte[320];

	private MusicUtil musicUtil;
	private MainUI mainUI;

	public MusicPlayThreadUtil(MusicUtil musicUtil, MainUI mUI) {
		this.musicUtil = musicUtil;
		this.mainUI = mUI;
	}
	
	public byte[] getByte320(){
		return new byte[320];
	}
	
	public byte[] getByte0(){
		return new byte[0];
	}

	@Override
	public void run() {
		try {
			int cnt;
			int length = 0;
			musicUtil.hasStop = false;
			while ((cnt = musicUtil.audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (musicUtil.isStop) {
					break;
				}
				if (cnt > 0) {
//					System.out.println(tempBuffer.length);
					length += cnt;
					musicUtil.sourceDataLine.write(tempBuffer, 0, cnt);
//					System.out.println(musicUtil.getSourceDataLine().getLongFramePosition());
					mainUI.getPlayProgress().setValue(length);
					mainUI.getPlayProgress().setToolTipText(length + Common.EMPTY);
				}
				if(MainUI.isStop){
					tempBuffer = getByte0();
				}else{
					tempBuffer = getByte320();
				}
			}
			if (cnt == -1) {
				mainUI.stopOperation();
				if (MainUI.play_mode_value == Common.REPEAT_ONCE_MODE_VALUE) {
					mainUI.playOperation();
				} else {
					mainUI.nextOperation();
				}
			}
			musicUtil.sourceDataLine.drain();
			musicUtil.sourceDataLine.close();
			musicUtil.hasStop = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
