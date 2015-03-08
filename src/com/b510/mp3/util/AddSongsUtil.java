/**
 * 
 */
package com.b510.mp3.util;

import java.util.Random;

import com.b510.mp3.common.Common;
import com.b510.mp3.ui.MainUI;
import com.b510.mp3.vo.PlayListVO;

/**
 * @author Hongten
 * @created Jul 30, 2014
 */
public class AddSongsUtil extends MainUI{
	private static final long serialVersionUID = 1L;

	public AddSongsUtil(String title) {
		super(title);
		this.setVisible(false);
	}
	
	public synchronized static int add2PlayList(String name, String filePath) {
		if (null != playList && playList.size() > 0) {
			int size = playList.size();
			int id = -1;
			for (PlayListVO item : playList) {
				if (filePath.replace(Common.BLACKSLASH, Common.ELLIPSIS_SIGN).equals(item.getPath())) {
					id = item.getId();
					break;
				} else {
					size--;
				}
			}
			if (size == 0 && id == -1) {
				PlayListVO song = addNewSong(name, filePath, false);
				return song.getId();
			} else {
				return id;
			}
		} else {
			PlayListVO song = addNewSong(name, filePath, true);
			return song.getId();
		}
	}

	private synchronized static PlayListVO addNewSong(String name, String filePath, boolean listIsEmpty) {
		PlayListVO songs = new PlayListVO();
		if (listIsEmpty) {
			songs.setId(1);
		} else {
			songs.setId(playList.size() + 1);
		}
		songs.setName(name.substring(0, name.lastIndexOf(Common.FULL_SPOT)));
		songs.setPath(filePath.replace(Common.BLACKSLASH, Common.ELLIPSIS_SIGN));
		songs.setSize(CommonUtil.getFileSize(filePath));
		String imagetPath = Common.SINGER_IMAGES[new Random().nextInt(Common.SINGER_IMAGES.length)];
		String imageName = CommonUtil.getLastName(imagetPath);
		songs.setImageName(imageName);
		songs.setImagePath(imagetPath);
		playList.add(songs);
		return songs;
	}

}
