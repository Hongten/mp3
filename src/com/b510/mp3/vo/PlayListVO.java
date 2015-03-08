/**
 * 
 */
package com.b510.mp3.vo;

/**
 * playListTable.getTableHeader().setReorderingAllowed(false);
 * @author Hongten
 * @created 2014-7-28
 */
public class PlayListVO {

	private int id;
	private String name;
	private String singerName;
	private String special;
	private String path;
	private long size;
	private String imageName;
	private String imagePath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSingerName() {
		return singerName;
	}

	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
