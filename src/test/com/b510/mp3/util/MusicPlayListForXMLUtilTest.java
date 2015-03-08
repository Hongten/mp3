/**
 * 
 */
package test.com.b510.mp3.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.MusicPlayListForXMLUtil;
import com.b510.mp3.vo.PlayListVO;

/**
 * @author Hongten
 * @created Aug 6, 2014
 */
public class MusicPlayListForXMLUtilTest {

	@Test
	public void testSave() {
		MusicPlayListForXMLUtil xmlUtil = new MusicPlayListForXMLUtil();
		PlayListVO playList = new PlayListVO();
		playList.setId(1);
		playList.setName("One Day");
		playList.setSingerName("Charice");
		playList.setSpecial("");
		playList.setPath("D:/ncs_workspace/mp3_v1.0.9/bin/com/b510/mp3/resources/sound/One_Day.mp3");
		playList.setSize(Long.valueOf("3131000"));
		playList.setImageName("charice.jpg");
		playList.setImagePath(Common.DONGMA_0);
		List<PlayListVO> list = new ArrayList<PlayListVO>();
		list.add(playList);
		boolean result = xmlUtil.save(list);
		Assert.assertEquals(true, result);
	}

	@Test
	public void testLoadPlayListFromXML() {
		MusicPlayListForXMLUtil xmlUtil = new MusicPlayListForXMLUtil();
		List<PlayListVO> list = xmlUtil.loadPlayListFromXML();
		Assert.assertEquals(true, null != list);
	}
}
