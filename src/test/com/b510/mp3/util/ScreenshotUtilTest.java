/**
 * 
 */
package test.com.b510.mp3.util;

import org.junit.Test;

import com.b510.mp3.ui.MainUI;
import com.b510.mp3.util.ScreenshotUtil;

/**
 * @author Hongten
 * @created 2014-7-29
 */
public class ScreenshotUtilTest {

	@Test
	public void test() {
		ScreenshotUtil.screenshot(new MainUI("Test mp3..."));
	}

}
