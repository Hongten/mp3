/**
 * 
 */
package test.com.b510.mp3.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.CommonUtil;

/**
 * @author Hongten
 * @created 2014-7-29
 */
public class CommonUtilTest {

	@Test
	public void testGetPostfix() {
		String path = CommonUtilTest.class.getClassLoader().getResource(Common.SCREENSHOT_BG).toString();
		String postfix = CommonUtil.getPostfix(path);
		assertEquals("png", postfix);
	}
	
	@Test
	public void testGetFileSize() {
		String path = CommonUtilTest.class.getClassLoader().getResource(Common.SCREENSHOT_BG).toString();
		path = path.substring(6, path.length());
		long size = CommonUtil.getFileSize(path);
		assertEquals(59121, size);
	}
	
	@Test
	public void testGetEllipsisString(){
		String str = CommonUtil.getEllipsisString("E:/ELS/ELS-861/The Same Caveator or Common Title/Angeli Test/Wu Jun Test 24-Jul-2014/Wu Jun Jul 24, 2014");
		assertEquals("E:/ELS/ELS-861/The Same Caveator or Common Ti.../Wu Jun Jul 24, 2014  ", str);
	}
	
	@Test
	public void testGetLastName(){
		String string = "com/b510/mp3/resources/images/head/wenlan_1.jpg";
		string = CommonUtil.getLastName(string);
		assertEquals("wenlan_1", string);
	}

	@Test
	public void testAccessURL(){
		CommonUtil.accessURL(Common.HOME_PAGE);
	}
	
	@Test
	public void testgetCalandarString(){
		String string = CommonUtil.getCalandarString();
		System.out.println(string);
	}
}
