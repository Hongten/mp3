/**
 * 
 */
package test.com.b510.mp3.util;

import java.io.File;

import org.junit.Test;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.MusicUtil;

/**
 * @author Hongten
 * @created 2014-7-31
 */
public class MusicUtilTest {

	@Test
	public void test() {
		String path = MusicUtilTest.class.getClassLoader().getResource(Common.ONE_DAY_MUSIC).toString();
		path = path.substring(6, path.length());
		MusicUtil musicUtil = new MusicUtil();
		File file = new File(path);
		musicUtil.play(file);
	}

}
