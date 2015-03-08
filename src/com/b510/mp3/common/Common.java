/**
 * 
 */
package com.b510.mp3.common;


/**
 * All variables will be here.
 * @author Hongten
 * @created Jul 28, 2014
 */
public class Common {

	public static final String EMPTY = "";
	public static final String BLANK = " ";
	public static final String COMMA = ",";
	public static final String FULL_SPOT = ".";
	public static final String COLOR = ":";
	public static final String STAR = "*";
	public static final String QUESTION_MARK = "?";
	public static final String UNDER_LINE = "_";
	public static final String LINE = "-";
	public static final String VERTICAL_LINE = "|";
	public static final String ELLIPSIS_SIGN = "/";
	public static final String DOUBLE_ELLIPSIS = ELLIPSIS_SIGN + ELLIPSIS_SIGN;
	public static final String BLACKSLASH = "\\";
	public static final String ENCODING_UTF_8 = "UTF-8";

	// About UI
	public static final String AUTHOR = "Author";
	public static final String AUTHOR_NAME = "Hongten";
	public static final String AUTHOR_DESC = "I'm " + AUTHOR_NAME;
	public static final String ITEM = "Item";
	public static final String DESCRIPTION = "Desctiption";
	public static final String APPLICATION = "Application";
	public static final String NAME = "Name";
	public static final String APPLICATION_NAME = APPLICATION + BLANK + NAME;
	public static final String MP3_PLAYER = "Mp3 Player";
	public static final String APPLICATION_DESCRIPTION = APPLICATION + BLANK + DESCRIPTION;
	public static final String APPLICATION_DESCRIPTION_DETAIL = "A mp3 player";
	public static final String VERSION = "Version";
	public static final String VERSION_VALUE = "1.0";
	public static final String BLOG = "Blog";
	public static final String HOME_PAGE = "http://www.cnblogs.com/hongten";
	public static final String SUBSTANCE_SKINS_PAGE =  HOME_PAGE + "/p/hongten_notepad_substance_skins.html";;
	
	public static final String RESOURCES_PATH = "com/b510/mp3/resources/";
	public static final String IMAGE_PATH = RESOURCES_PATH + "images/";
	public static final String SOUND_PATH = RESOURCES_PATH + "sound/";
	public static final String IMAGE_PNG_PATH = IMAGE_PATH + "png/";
	public static final String IMAGE_HEAD_PATH = IMAGE_PATH + "head/";

	// Image resources
	public static final String TRAY_ICON = IMAGE_PATH + "mytray.png";
	public static final String SCREENSHOT_BG = IMAGE_PATH + "screenshot_bg.png";
	public static final String ABOUT_PIC = IMAGE_PATH + "hongten.png";

	// Sound resources
	public static final String ONE_DAY_MUSIC = SOUND_PATH + "One_Day.mp3";

	// Button image
	public static final String PNG_0 = IMAGE_PNG_PATH + "0.png";//downLoad
	public static final String PNG_1 = IMAGE_PNG_PATH + "1.png";//Next
	public static final String PNG_2 = IMAGE_PNG_PATH + "2.png";//PlaylistUI
	public static final String PNG_3 = IMAGE_PNG_PATH + "3.png";//Last
	public static final String PNG_4 = IMAGE_PNG_PATH + "4.png";//Suspend
	public static final String PNG_5 = IMAGE_PNG_PATH + "5.png";//Suspend(with cycle)
	public static final String PNG_6 = IMAGE_PNG_PATH + "6.png";//Play
	public static final String PNG_7 = IMAGE_PNG_PATH + "7.png";//Play(with cycle)
	public static final String PNG_8 = IMAGE_PNG_PATH + "8.png";//First
	public static final String PNG_9 = IMAGE_PNG_PATH + "9.png";//Previous
	public static final String PNG_10 = IMAGE_PNG_PATH + "10.png";//Shuffle(Play mode)
	public static final String PNG_11 = IMAGE_PNG_PATH + "11.png";//Stop
	public static final String PNG_12 = IMAGE_PNG_PATH + "12.png";//Repeat Once(Play mode)
	public static final String PNG_13 = IMAGE_PNG_PATH + "13.png";//Order(Play mode)
	public static final String PNG_14 = IMAGE_PNG_PATH + "14.png";//Upload
	public static final String PNG_15 = IMAGE_PNG_PATH + "15.png";//Remove(PlayListUI)
	public static final String PNG_16 = IMAGE_PNG_PATH + "16.png";//Add(PlayListUI)
	public static final String JPG_17 = IMAGE_PNG_PATH + "17.jpg";//Default(Stop in MainUI)

	public static final String DONGMA_0 = IMAGE_HEAD_PATH + "dongma_0.jpg";
	public static final String DONGMAN_1 = IMAGE_HEAD_PATH + "dongman_1.jpg";
	public static final String DONGMAN_2 = IMAGE_HEAD_PATH + "dongman_2.jpg";
	public static final String DONGMAN_3 = IMAGE_HEAD_PATH + "dongman_3.jpg";
	public static final String DONGMAN_5 = IMAGE_HEAD_PATH + "dongman_5.jpg";
	public static final String DONGMAN_6 = IMAGE_HEAD_PATH + "dongman_6.jpg";
	public static final String DONGMAN_7 = IMAGE_HEAD_PATH + "dongman_7.jpg";
	public static final String DONGMAN_8 = IMAGE_HEAD_PATH + "dongman_8.jpg";
	public static final String DONGMAN_9 = IMAGE_HEAD_PATH + "dongman_9.jpg";
	public static final String DONGMAN_10 = IMAGE_HEAD_PATH + "dongman_10.jpg";

	public static final String FENGJING_0 = IMAGE_HEAD_PATH + "fengjing_0.jpg";
	public static final String FENGJING_1 = IMAGE_HEAD_PATH + "fengjing_1.jpg";
	public static final String FENGJING_2 = IMAGE_HEAD_PATH + "fengjing_2.jpg";
	public static final String FENGJING_3 = IMAGE_HEAD_PATH + "fengjing_3.jpg";
	public static final String FENGJING_4 = IMAGE_HEAD_PATH + "fengjing_4.jpg";
	public static final String FENGJING_5 = IMAGE_HEAD_PATH + "fengjing_5.jpg";
	public static final String FENGJING_6 = IMAGE_HEAD_PATH + "fengjing_6.jpg";
	public static final String FENGJING_7 = IMAGE_HEAD_PATH + "fengjing_7.jpg";

	// Default singer image.
	public static final String[] SINGER_IMAGES = { DONGMAN_1, FENGJING_0, DONGMAN_10, FENGJING_1, DONGMAN_2, FENGJING_2, DONGMAN_3, FENGJING_3, DONGMAN_5, FENGJING_4, DONGMAN_6,
			FENGJING_5, DONGMAN_7, FENGJING_6, DONGMAN_8, FENGJING_7, DONGMAN_9, DONGMA_0 };

	// Menu in MainUI
	public static final String FILE = "File";
	public static final String EDIT = "Edit";
	public static final String SCAN = "Scan";
	public static final String TOOLS = "Tools";
	public static final String HELP = "Help";

	// Submenu
	public static final String PLAY_LIST = "Play List";
	public static final String CLEAN_PLAY_LIST = "Clean Play List";
	public static final String OPEN = "Open";
	public static final String EXIT = "Exit";
	public static final String PLAY = "Play";
	public static final String SUSPEND = "Suspend";
	public static final String TURN_ON = "Turn On";
	public static final String TURN_DOWN = "Turn Down";
	public static final String SCAN_ALL = "Scan All";
	public static final String SCAN_IN_GIVEN_DIRECTORY = "Scan in Given Directory";
	public static final String CHANGE_THEME = "Change Theme";
	public static final String CHANGE_SKIN = "Change Skin";
	public static final String CHANGE_WATER_MARK = "Change Watermark";
	public static final String SCREENSHOT = "Screenshot";
	public static final String ABOUT = "About";

	// Button description
	public static final String NEXT = "Next";
	public static final String LAST = "Last";
	public static final String PREVIOUS = "Previous";
	public static final String FIRST = "First";
	public static final String STOP = "Stop";
	public static final String ADD = "Add";
	public static final String REMOVE = "Remove";

	// Play mode
	public static final String SHUFFLE_MODE = "Shuffle";
	public static final int SHUFFLE_MODE_VALUE = 0; // Default
	public static final String REPEAT_ONCE_MODE = "Repeat Once";
	public static final int REPEAT_ONCE_MODE_VALUE = 1;
	public static final String ORDER_MODE = "Order";
	public static final int ORDER_MODE_VALUE = 2;

	// KeyStroke
	public static final char O = 'O';
	public static final char W = 'W';
	public static final char H = 'H';
	public static final char F = 'F';
	public static final char P = 'P';
	public static final char G = 'G';
	public static final char S = 'S';
	public static final char T = 'T';
	public static final char SPACE = ' ';

	// Dialog messages and titles
	public static final String CONFIM_EXIT = "Confim Exit";
	public static final String EXIT_SYSTEM = "Exit MP3 Player?";
	public static final String ACCESS_URL_REQUEST = "Access URL Request";
	public static final String ACCESS_URL = "Access URL : ";
	public static final String CONFIM_REMOVE = "Confim Remove";
	public static final String CONFIM_REMOVE_SONG = "Confim Remove Song : ";
	public static final String SELECT_SONG_MESSAGE = "Please select one record!";
	public static final String SELECT_SONG_TITLE = "Please select one record!";
	public static final String CONFIM_CLEAN = "Confim Clean";
	public static final String CONFIM_CLEAN_PLAY_LIST = "Confim Clean Play List?";

	// Tray
	public static final String OPEN_MAIN_PANEL = "Open main panel";
	public static final String RESTORE = "Restore";

	// Screenshot
	public static final String OPERATION_GUIDE = "Operation Guide: \n1.Select the Screenshot menu.\n2.Double right clicks to exist screenshot.\n3.Double left clicks to save picture.\n4.Right-click to select the capture area.";
	public static final String X_Y = "X,Y" + BLANK + COLOR + BLANK;
	public static final String W_H = BLANK + BLANK + BLANK + BLANK + "W*H" + BLANK + COLOR + BLANK;
	public static final String RGB = "\nRBG:(";
	public static final String CLOSE_ROUND = ")\n";
	public static final String SCREENSHOT_ERROR = "Screenshot opeartion encounters error!";
	public static final String FONT_NAME = "Tahoma";
	public static final String FONT_SONG_TI = "宋体";

	// mp3(wav) format
	public static final String MP3 = "mp3";
	public static final String WAV = "wav";
	public static final String FILE_TYPE = "*.mp3/*.wav";
	public static final String INVALID_FILE_FORMAT_MP3 = "Invalid file format!\nThe format of the permit : \n'*.mp3', '*.wav'.";
	
	// jpg(png) format
	public static final String JPG = "jpg";
	public static final String PNG = "png";
	public static final String IMAGE_FILE_TYPE = "*.jpg/*.png";
	public static final String INVALID_FILE_FORMAT_JPG = "Invalid file format!\nThe format of the permit : \n'*.jpg', '*.png'.";
	
	public static final String SCAN_IN_GIVEN_DIRECTORY_DESC = "Input the path of directory, e.g.'C:/songs'";

	public static final String DEFAULT_DIRECTORY_PATH = "c:/";
	public static final String PLAY_LIST_SAVE_PATH = DEFAULT_DIRECTORY_PATH + "mp3Player_playlist.xml";
	public static final String SELECT_DIR = "Select Directory";

	
	public static final String SINGER_IMAGE_HANDLER = "Singer Head Image Heandler";
	
	public static final String USER_DIR = "user.dir";
	public static final String PATH = "Path" + BLANK + COLOR + BLANK;
	public static final String TOTAL = "Total" + BLANK + BLANK + COLOR + BLANK;
	public static final String CURRENT_PLAY = "Current Play" + BLANK + COLOR + BLANK;
	public static final String SCANING = "Scaning" + BLANK + COLOR + BLANK;
	public static final int ZERO = 0;
	public static final int TABLE_ROW_HEIGHT = 20;
	public static final String SELECT_DIR_DESC = "Input the path of a directory, and click the 'Scan' button to scan file(s).";

	public static final String SELECT_IMAGE = "Select Image" + BLANK + COLOR + BLANK;
	public static final String SET = "Set";
	public static final String CANCEL = "Cancel";
	public static final String COM = "com";
	public static final String FILE_WITH_COLOR = "file:/";
	public static final String HEAD_PATH = "c:/mp3/head/";
	public static final String TEMP_HEAD = "temp_head_";
	
	public static final String CURRENT_THEME = "Current Theme" + BLANK + COLOR + BLANK;
	public static final String CURRENT_SINK = "Current Skin" + BLANK + COLOR + BLANK;
	public static final String CURRENT_WATER_MARK = "Current Watermark" + BLANK + COLOR + BLANK;
	public static final String DESCRIPTION_WITH_COLOR = DESCRIPTION + BLANK + COLOR + BLANK;

	public static final String HTML_STOPPED = "<html><font color='red'>Stopped...</font></html>";
	public static final String HTML_PLAY_LIST_IS_EMPTY = "<html><font color='red'>Play list is Empty!Please enter 'Ctrl+F' to scan songs,<br>Or press 'Ctrl+Shift+F' to scan all,Or press 'Ctrl+O' to add song.</font></html>";
	public static final String HTML_SELECT_IMAGE_DESC = "<html>Input the path of a directory, and click the 'Set' button to set singer image.<br>Or click the 'Cancel' button to cancel.</html>";
	public static final String HTML_LAST_ONE = "<html><font color='red'>The Last One!</font></html>";
	public static final String HTML_FIRST_ONE = "<html><font color='red'>The First One!</font></html>";
}
