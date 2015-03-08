/**
 * 
 */
package test.com.b510.mp3.util;

import org.junit.Test;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.OpenUtil;

/**
 * @author Hongten
 * @created 2014-7-29
 */
public class OpenUtilTest {

	@Test
	public void testOpen() {
		OpenUtil.open(Common.MP3);
	}
}
