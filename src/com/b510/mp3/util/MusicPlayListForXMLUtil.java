/**
 * 
 */
package com.b510.mp3.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.b510.mp3.common.Common;
import com.b510.mp3.vo.PlayListVO;

/**
 * @author Hongten
 * @created 2014-7-30
 */
public class MusicPlayListForXMLUtil {

	private Document generateDocument(List<PlayListVO> list) {
		Document document = null;
		document = DocumentHelper.createDocument();
		// root element
		Element playlist = document.addElement("playlist");
		playlist.addComment("Playlist for all songs.");
		if (null != list && list.size() > 0) {
			// son element
			for (PlayListVO item : list) {
				Element song = playlist.addElement("song");
				song.addAttribute("id", String.valueOf(item.getId()));
				Element name = song.addElement("name");
				name.setText(item.getName());
				Element singerName = song.addElement("singerName");
				singerName.setText(item.getSingerName() == null ? "" : item.getSingerName());
				Element special = song.addElement("special");
				special.setText(item.getSpecial() == null ? "" : item.getSpecial());
				Element path = song.addElement("path");
				path.setText(item.getPath());
				Element size = song.addElement("size");
				size.setText(String.valueOf(item.getSize()));
				Element imageName = song.addElement("imageName");
				imageName.setText(item.getImageName() == null ? "" : item.getImageName());
				Element imagePath = song.addElement("imagePath");
				imagePath.setText(item.getImagePath() == null ? "" : item.getImagePath());
			}
		}
		return document;
	}

	private int writeDocumentToFile(Document document, File outputXml) {
		int result = -1;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(Common.ENCODING_UTF_8);
			XMLWriter output = new XMLWriter(new FileWriter(outputXml), format);
			output.write(document);
			output.close();
			result = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean save(List<PlayListVO> list) {
		Document document = generateDocument(list);
		File file = new File(Common.PLAY_LIST_SAVE_PATH);
		int result = writeDocumentToFile(document, file);
		return (result != -1);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<PlayListVO> loadPlayListFromXML() {
		List<PlayListVO> playLists = new ArrayList<PlayListVO>();
		File file = new File(Common.PLAY_LIST_SAVE_PATH);
		if (file.exists()) {
			try {
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(Common.PLAY_LIST_SAVE_PATH);
				Element root = document.getRootElement();
				PlayListVO playList = null;
				for (Iterator song = root.elementIterator("song"); song.hasNext();) {
					Element songElement = (Element) song.next();
					Attribute idAttribute = songElement.attribute("id");
					int id = Integer.valueOf(idAttribute.getValue());
					String name = songElement.elementText("name");
					String singerName = songElement.elementText("singerName");
					String special = songElement.elementText("special");
					String path = songElement.elementText("path");
					String size = songElement.elementText("size");
					String imageName = songElement.elementText("imageName");
					String imagePath = songElement.elementText("imagePath");

					playList = new PlayListVO();
					playList.setId(id);
					playList.setName(name);
					playList.setSingerName(singerName);
					playList.setSpecial(special);
					playList.setPath(path);
					playList.setSize(Long.valueOf(size));
					playList.setImageName(imageName);
					playList.setImagePath(imagePath);

					playLists.add(playList);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return playLists;
	}
}
