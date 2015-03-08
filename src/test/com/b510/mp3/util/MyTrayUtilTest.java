/**
 * 
 */
package test.com.b510.mp3.util;

import org.junit.Test;

import com.b510.mp3.ui.MainUI;
import com.b510.mp3.util.TrayUtil;

/**
 * @author Hongten
 * @created 2014-7-29
 */
public class MyTrayUtilTest {

	@Test
	public void test() {
		MainUI mainUI = new MainUI("mp3 test");
		new TrayUtil(mainUI);
	}

}
