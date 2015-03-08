/**
 * 
 */
package test.com.b510.mp3.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.b510.mp3.util.ScanDirectoryUtil;

/**
 * @author Hongten
 * @created 2014-7-29
 */
public class ScanDirectoryUtilTest {

	@Test
	public void testScanSongs() {
		String dir = ScanDirectoryUtil.getPathOfDirectory();
		assertEquals("C:/Users/Administrator/Documents", dir.replace('\\', '/'));
		ScanDirectoryUtil.scanSongs(dir, true);
	}
	
	@Test
	public void testScanAllDirs(){
		ScanDirectoryUtil.scanAllDirs();
	}

}
