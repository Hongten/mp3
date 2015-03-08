/**
 * 
 */
package com.b510.mp3.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.AutumnSkin;
import org.jvnet.substance.skin.BusinessBlackSteelSkin;
import org.jvnet.substance.skin.ChallengerDeepSkin;
import org.jvnet.substance.skin.CremeCoffeeSkin;
import org.jvnet.substance.skin.CremeSkin;
import org.jvnet.substance.skin.EbonyHighContrastSkin;
import org.jvnet.substance.skin.EmeraldDuskSkin;
import org.jvnet.substance.skin.FieldOfWheatSkin;
import org.jvnet.substance.skin.FindingNemoSkin;
import org.jvnet.substance.skin.GreenMagicSkin;
import org.jvnet.substance.skin.MagmaSkin;
import org.jvnet.substance.skin.MangoSkin;
import org.jvnet.substance.skin.MistSilverSkin;
import org.jvnet.substance.skin.ModerateSkin;
import org.jvnet.substance.skin.NebulaBrickWallSkin;
import org.jvnet.substance.skin.NebulaSkin;
import org.jvnet.substance.skin.OfficeBlue2007Skin;
import org.jvnet.substance.skin.RavenGraphiteGlassSkin;
import org.jvnet.substance.skin.RavenGraphiteSkin;
import org.jvnet.substance.skin.RavenSkin;
import org.jvnet.substance.skin.SaharaSkin;
import org.jvnet.substance.skin.SubstanceAbstractSkin;
import org.jvnet.substance.theme.SubstanceAquaTheme;
import org.jvnet.substance.theme.SubstanceBarbyPinkTheme;
import org.jvnet.substance.theme.SubstanceBottleGreenTheme;
import org.jvnet.substance.theme.SubstanceBrownTheme;
import org.jvnet.substance.theme.SubstanceCharcoalTheme;
import org.jvnet.substance.theme.SubstanceCremeTheme;
import org.jvnet.substance.theme.SubstanceDarkVioletTheme;
import org.jvnet.substance.theme.SubstanceDesertSandTheme;
import org.jvnet.substance.theme.SubstanceEbonyTheme;
import org.jvnet.substance.theme.SubstanceJadeForestTheme;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;
import org.jvnet.substance.theme.SubstanceLimeGreenTheme;
import org.jvnet.substance.theme.SubstanceOliveTheme;
import org.jvnet.substance.theme.SubstanceOrangeTheme;
import org.jvnet.substance.theme.SubstancePurpleTheme;
import org.jvnet.substance.theme.SubstanceRaspberryTheme;
import org.jvnet.substance.theme.SubstanceSepiaTheme;
import org.jvnet.substance.theme.SubstanceSteelBlueTheme;
import org.jvnet.substance.theme.SubstanceSunGlareTheme;
import org.jvnet.substance.theme.SubstanceSunsetTheme;
import org.jvnet.substance.theme.SubstanceTerracottaTheme;
import org.jvnet.substance.theme.SubstanceTheme;
import org.jvnet.substance.watermark.SubstanceBinaryWatermark;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;
import org.jvnet.substance.watermark.SubstanceCopperplateEngravingWatermark;
import org.jvnet.substance.watermark.SubstanceCrosshatchWatermark;
import org.jvnet.substance.watermark.SubstanceFabricWatermark;
import org.jvnet.substance.watermark.SubstanceGenericNoiseWatermark;
import org.jvnet.substance.watermark.SubstanceKatakanaWatermark;
import org.jvnet.substance.watermark.SubstanceLatchWatermark;
import org.jvnet.substance.watermark.SubstanceMagneticFieldWatermark;
import org.jvnet.substance.watermark.SubstanceMarbleVeinWatermark;
import org.jvnet.substance.watermark.SubstanceMazeWatermark;
import org.jvnet.substance.watermark.SubstanceMetalWallWatermark;
import org.jvnet.substance.watermark.SubstanceMosaicWatermark;
import org.jvnet.substance.watermark.SubstanceNoneWatermark;
import org.jvnet.substance.watermark.SubstanceNullWatermark;
import org.jvnet.substance.watermark.SubstancePlanktonWatermark;
import org.jvnet.substance.watermark.SubstanceStripeWatermark;
import org.jvnet.substance.watermark.SubstanceWatermark;
import org.jvnet.substance.watermark.SubstanceWoodWatermark;

/**
 * The basic class extends <code>java.awt.JFrame</code>, there are three methods provided:<br>
 * <code>getTheme()</code>,<code>getSkin()</code>,<code>getWatermark()</code> to change the frame theme, skin and watermark.<br>
 * and there are 21 themes , 21 skins and 18 watermarks to provided. And the default theme is <code>SubstanceAquaTheme</code>, 
 * default skin is <code>OfficeBlue2007Skin</code> and default watermark is <code>SubstanceBubblesWatermark</code>.You can change value to change theme, <br>
 * skin and watermark if possible. and you should call the method <code>setJUI()</code> to refresh the page when you change the value.
 * @author Hongten
 * @created Aug 1, 2014
 */
public class JUI extends JFrame {

	private static final long serialVersionUID = 1L;

	static SubstanceTheme theme;
	static SubstanceAbstractSkin skin;
	static SubstanceWatermark watermark;
	static int themeNum = 1;
	static int skinNum = 17;
	static int watermarkNum = 1;
	String title;

	/**
	 * Total themes : 21. Get the skin according to the <code>themeNum</code> value, and the default skin is <code>SubstanceAquaTheme</code>
	 * @param num <code>themeNum</code> value
	 * @return
	 */
	public SubstanceTheme getTheme(int num) {
		switch (num) {
		case 1:
			theme = new SubstanceAquaTheme();
			break;
		case 2:
			theme = new SubstanceBarbyPinkTheme();
			break;
		case 3:
			theme = new SubstanceBottleGreenTheme();
			break;
		case 4:
			theme = new SubstanceBrownTheme();
			break;
		case 5:
			theme = new SubstanceCharcoalTheme();
			break;
		case 6:
			theme = new SubstanceCremeTheme();
			break;
		case 7:
			theme = new SubstanceDarkVioletTheme();
			break;
		case 8:
			theme = new SubstanceDesertSandTheme();
			break;
		case 9:
			theme = new SubstanceEbonyTheme();
			break;
		case 10:
			theme = new SubstanceJadeForestTheme();
			break;
		case 11:
			theme = new SubstanceLightAquaTheme();
			break;
		case 12:
			theme = new SubstanceLimeGreenTheme();
			break;
		case 14:
			theme = new SubstanceOliveTheme();
			break;
		case 15:
			theme = new SubstanceOrangeTheme();
			break;
		case 16:
			theme = new SubstancePurpleTheme();
			break;
		case 17:
			theme = new SubstanceRaspberryTheme();
			break;
		case 18:
			theme = new SubstanceSepiaTheme();
			break;
		case 19:
			theme = new SubstanceSteelBlueTheme();
			break;
		case 20:
			theme = new SubstanceSunGlareTheme();
			break;
		case 21:
			theme = new SubstanceSunsetTheme();
			break;
		case 22:
			theme = new SubstanceTerracottaTheme();
			break;
		default:
			theme = new SubstanceAquaTheme();
			break;
		}
		return theme;
	}
	
	/**
	 * Total skins : 21. Get the skin according to the <code>skinNum</code> value, and the default skin is <code>OfficeBlue2007Skin</code>
	 * @param num <code>skinNum</code> value
	 * @return
	 */
	public SubstanceAbstractSkin getSkin(int num) {
		switch (num) {
		case 1:
			skin = new AutumnSkin();
			break;
		case 2:
			skin = new BusinessBlackSteelSkin();
			break;
		case 3:
			skin = new ChallengerDeepSkin();
			break;
		case 4:
			skin = new CremeCoffeeSkin();
			break;
		case 5:
			skin = new CremeSkin();
			break;
		case 6:
			skin = new EbonyHighContrastSkin();
			break;
		case 7:
			skin = new EmeraldDuskSkin();
			break;
		case 8:
			skin = new FieldOfWheatSkin();
			break;
		case 9:
			skin = new FindingNemoSkin();
			break;
		case 10:
			skin = new GreenMagicSkin();
			break;
		case 11:
			skin = new MagmaSkin();
			break;
		case 12:
			skin = new MangoSkin();
			break;
		case 13:
			skin = new MistSilverSkin();
			break;
		case 14:
			skin = new ModerateSkin();
			break;
		case 15:
			skin = new NebulaBrickWallSkin();
			break;
		case 16:
			skin = new NebulaSkin();
			break;
		case 17:
			skin = new OfficeBlue2007Skin();
			break;
		case 18:
			skin = new RavenGraphiteGlassSkin();
			break;
		case 19:
			skin = new RavenGraphiteSkin();
			break;
		case 20:
			skin = new RavenSkin();
			break;
		case 21:
			skin = new SaharaSkin();
			break;
		default:
			skin = new FieldOfWheatSkin();
			break;
		}
		return skin;
	}

	/**
	 * Total watermarks : 18. Get the watermark according to the <code>watermarkNum</code> value, and the default wartermark is <code>SubstanceBubblesWatermark</code>
	 * @param num <code>watermarkNum</code> value
	 * @return
	 */
	public SubstanceWatermark getWatermark(int num) {
		switch (num) {
		case 1:
			watermark = new SubstanceBubblesWatermark();
			break;
		case 2:
			watermark = new SubstanceBinaryWatermark();
			break;
		case 3:
			watermark = new SubstanceCopperplateEngravingWatermark();
			break;
		case 4:
			watermark = new SubstanceCrosshatchWatermark();
			break;
		case 5:
			watermark = new SubstanceFabricWatermark();
			break;
		case 6:
			watermark = new SubstanceGenericNoiseWatermark();
			break;
		case 7:
			watermark = new SubstanceKatakanaWatermark();
			break;
		case 8:
			watermark = new SubstanceLatchWatermark();
			break;
		case 9:
			watermark = new SubstanceMagneticFieldWatermark();
			break;
		case 10:
			watermark = new SubstanceMarbleVeinWatermark();
			break;
		case 11:
			watermark = new SubstanceMazeWatermark();
			break;
		case 12:
			watermark = new SubstanceMetalWallWatermark();
			break;
		case 13:
			watermark = new SubstanceMosaicWatermark();
			break;
		case 14:
			watermark = new SubstanceNoneWatermark();
			break;
		case 15:
			watermark = new SubstanceNullWatermark();
			break;
		case 16:
			watermark = new SubstancePlanktonWatermark();
			break;
		case 17:
			watermark = new SubstanceStripeWatermark();
			break;
		case 18:
			watermark = new SubstanceWoodWatermark();
			break;
		default:
			watermark = new SubstanceBubblesWatermark();
			break;
		}
		return watermark;
	}
	
	/**
	 * Set the page UI. including the theme, skin, watermark.etc.
	 */
	public void setJUI() {
		try {
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			SubstanceLookAndFeel.setCurrentTheme(getTheme(themeNum));
			SubstanceLookAndFeel.setSkin(getSkin(skinNum));
			SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
			SubstanceLookAndFeel.setCurrentWatermark(getWatermark(watermarkNum));
			SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
			SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

	public JUI(String title) {
		this.title = title;
		setJUI();
	}

	public void init() {

	}
}
