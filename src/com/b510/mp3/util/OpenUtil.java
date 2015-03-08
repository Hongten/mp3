/**
 * 
 */
package com.b510.mp3.util;

import java.awt.Frame;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;

/**
 * @author Hongten
 * @created 2014-7-28
 */
public class OpenUtil extends MainUI {
	private static final long serialVersionUID = 1L;

	public OpenUtil(String title) {
		super(title);
		this.setVisible(false);
	}

	/**
	 * @param parent
	 * @return -1 the file is not '*.mp3' or '*.wav'
	 */
	public synchronized static int open(Frame parent) {
		String filePath = open(Common.MP3);
		String name = Common.EMPTY;
		if (null != filePath && !Common.EMPTY.equals(filePath)) {
			filePath = filePath.replace(Common.DOUBLE_ELLIPSIS, Common.ELLIPSIS_SIGN).replace(Common.BLACKSLASH, Common.ELLIPSIS_SIGN);
			if (filePath.contains(Common.ELLIPSIS_SIGN)) {
				String[] strings = filePath.split(Common.ELLIPSIS_SIGN);
				name = strings[strings.length - 1];
			}
			return AddSongsUtil.add2PlayList(name, filePath);
		}
		return -1;
	}

	public static String open(String type) {
		String path = Common.EMPTY;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter;
		if(type.equals(Common.MP3)){
			filter = new FileNameExtensionFilter(Common.FILE_TYPE, Common.MP3, Common.WAV);
			chooser.setFileFilter(filter);
		}else if(type.equals(Common.JPG)){
			filter = new FileNameExtensionFilter(Common.IMAGE_FILE_TYPE, Common.JPG, Common.PNG);
			chooser.setFileFilter(filter);
		}
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(Common.OPEN);
		int ret = chooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			path = chooser.getSelectedFile().getAbsolutePath();
		}
		return path;
	}

	public static void InvalidFileFormat(Frame parent) {
		JOptionPane.showMessageDialog(parent, Common.INVALID_FILE_FORMAT_MP3);
	}
}
