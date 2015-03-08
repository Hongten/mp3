/**
 * 
 */
package com.b510.mp3.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;
import com.b510.mp3.ui.ScanInGivenDirectoryUI;

/**
 * @author Hongten
 * @created Jul 29, 2014
 */
public class ScanDirectoryUtil extends MainUI implements Runnable {
	private static final long serialVersionUID = 1L;

	private String path;
	public static boolean scanCompleteStatus = false;
	private static List<Thread> list;
	public static boolean loopControl = true;

	private static ScanInGivenDirectoryUI scanInGivenDirectoryUI;

	public ScanDirectoryUtil(String title) {
		super(title);
	}

	public static String getPathOfDirectory() {
		String dir = Common.DEFAULT_DIRECTORY_PATH;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle(Common.SELECT_DIR);
		chooser.setCurrentDirectory(new File(ScanInGivenDirectoryUI.dirPathJTextFieldValue));
		int ret = chooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			dir = chooser.getSelectedFile().getAbsolutePath();
		} else if (ret == JFileChooser.CANCEL_OPTION) {
			dir = ScanInGivenDirectoryUI.dirPathJTextFieldValue;
		}
		return dir;
	}

	public static synchronized void scanSongs(String pathname, boolean flag) {
		File file = new File(pathname);
		File[] listFiles = file.listFiles();
		if (null != listFiles && listFiles.length > 0) {
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {
					String path = listFiles[i].getAbsolutePath();
					scanSongs(path, flag);
				} else if (listFiles[i].isFile()) {
					if (flag) {
						String path = listFiles[i].getAbsolutePath();
						if (null != scanInGivenDirectoryUI) {
							scanInGivenDirectoryUI.setPathDescJLabel(path);
						}
						ScanInGivenDirectoryUI.pathDescJLabelValue = path;
					}
					addNewSong(listFiles[i], flag);
				}
			}
			ScanInGivenDirectoryUI.scanJobCompleteFlag = true;
			scanCompleteStatus = true;
		}
	}

	private static synchronized void addNewSong(File file, boolean flag) {
		String name = file.getName();
		String postfix = CommonUtil.getPostfix(file.getAbsolutePath());
		if (null != postfix && (Common.MP3.equalsIgnoreCase(postfix) || Common.WAV.equalsIgnoreCase(postfix))) {
			if (flag) {
				ScanInGivenDirectoryUI.numberJLabelValue += 1;
				if (null != scanInGivenDirectoryUI) {
					scanInGivenDirectoryUI.setnumberJLabel(String.valueOf(ScanInGivenDirectoryUI.numberJLabelValue));
				}
			}
			AddSongsUtil.add2PlayList(name, file.getAbsolutePath());
		}
	}

	public static void scanAllDirs() {
		File[] roots = File.listRoots();
		if (null != roots && roots.length > 0) {
			list = new ArrayList<Thread>();
			for (int i = 0; i < roots.length; i++) {
				File file = roots[i];
				ScanDirectoryUtil scanDirectoryUtil = new ScanDirectoryUtil(Common.EMPTY);
				scanDirectoryUtil.setVisible(false);
				scanDirectoryUtil.setPath(file.getAbsolutePath());
				Thread threadTemp = new Thread(scanDirectoryUtil);
				list.add(threadTemp);
				System.out.println(Common.SCANING + Common.BLANK + Common.COLOR + Common.BLANK + roots[i]);
				threadTemp.start();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		scanSongs(this.path, false);
		while (loopControl) {
			try {
				Thread.sleep(600000); // rest 600 seconds
				if (null != list && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						System.out.println("stop scan thread .. " + i);
						Thread thread = list.get(i);
						thread.stop();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		MusicPlayListForXMLUtil xmlUtil = new MusicPlayListForXMLUtil();
		xmlUtil.save(playList);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static void setScanInGivenDirectoryUI(ScanInGivenDirectoryUI ui) {
		scanInGivenDirectoryUI = ui;
	}
}
