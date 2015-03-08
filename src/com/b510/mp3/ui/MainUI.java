package com.b510.mp3.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.FloatControl;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.MusicEffectThreadUtil;
import com.b510.mp3.util.MusicPlayListForXMLUtil;
import com.b510.mp3.util.MusicUtil;
import com.b510.mp3.util.OpenUtil;
import com.b510.mp3.util.ScanDirectoryUtil;
import com.b510.mp3.util.ScreenshotUtil;
import com.b510.mp3.util.TrayUtil;
import com.b510.mp3.vo.PlayListVO;

/**
 * The <code>MainUI</code> class extends <code>Mp3UI</code>. 
 * @author Hongten
 * @created 2014-8-2
 */
public class MainUI extends Mp3UI {
	private static final long serialVersionUID = 1L;

	// All operations(including Play, Stop, Suspend, Next, First, Previous, Last, Play Mode, Play List, Volume.etc.) buttons.
	private JButton firstButton;
	private JSplitPane songInfoSplitPane;
	private JButton lastButton;
	private JSeparator leftLine;
	private JPanel leftPanel;
	private JMenuBar menuBar;
	private JButton nextButton;
	private JPanel playControlPanel;
	private JButton playListButton;
	private JPanel playModeAndListControlPanel;
	private JButton playModeButton;
	private JButton playOrSuspendButton;
	private JSlider playProgress;
	private JButton previousButton;
	private JPanel rightPanel;
	private JButton singerImageButton;
	private JLabel songName;
	private JProgressBar soundEffectBarA;
	private JProgressBar soundEffectBarB;
	private JProgressBar soundEffectBarC;
	private JProgressBar soundEffectBarD;
	private JButton stopButton;
	private JSlider volume;
	private JLabel volume_0;
	private JLabel volume_100;

	// All menus (including:
	//File  (including : Play List, Clean Play List, Open, Exit)
	//Edit  (including : Play<Suspend>, Stop, Turn On, Turn Down)
	//Scan  (including : Scan All, Scan in Given Directory)
	//Tools (including : Change Theme, Change Skin, Change Watermark, Screenshot)
	//Help  (including : About)
	private JMenu file;
	private JMenu edit;
	private JMenu scan;
	private JMenu tools;
	private JMenu help;

	private JSeparator line;

	private JMenuItem playListItem;
	private JMenuItem cleanPlayListItem;
	private JMenuItem openItem;
	private JMenuItem exitItem;

	private JMenuItem playItem;
	private JMenuItem stopItem;
	private JMenuItem turnUpItem;
	private JMenuItem turnDownItem;

	// Default stop is true
	public static boolean isStop = true;
	// Default suspend is false
	public static boolean isSuspend = false;
	// Default volume value is 10
	private static int volume_value = 10;
	// Default play mode is Shuffle and the value is 0
	public static int play_mode_value = 0;

	private JMenuItem scanAllItem;
	private JMenuItem scanInGivenDerectoryItem;

	private JMenuItem changeThemeItem;
	private JMenuItem changeSkinItem;
	private JMenuItem changeWatermarkItem;
	private JMenuItem screenshotItem;

	private JMenuItem aboutItem;

	// Default position is (0, 0)
	public static int pointX = 0;
	public static int pointY = 0;

	public static List<PlayListVO> playList = new ArrayList<PlayListVO>();

	// Tray tool
	private TrayUtil tray;
	private ScanInGivenDirectoryUI scanInGivenDirectoryUI;
	private AboutUI aboutUI;
	private ThemeManagerUI themeManagerUI;
	private SkinManagerUI skinManagerUI;
	private WatermarkManagerUI watermarkManagerUI;
	private PlayListUI playListUI;
	private MusicUtil musicUtil;
	private Thread barsThread;
	private MusicPlayListForXMLUtil xmlUtil;

	public MainUI(String title) {
		super(title);
		this.setTitle(title);
	}

	public void init() {
		initComponents();

		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setJMenuBar(menuBar);

		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				if (null != playListUI) {
					if (playListUI.isVisible()) {
						setMainUIXY();
						distoryPlayListUI();
						showPlayListUI();
					}
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Exit System.....");
				System.exit(0);
			}
		});
		
		// Loading the tray when the application start.
		if (null == tray) {
			tray = new TrayUtil(MainUI.this);
		} else {
			tray.initTray();
		}

		// Loading the play list when the application start.
		loadPlayList();
	}

	private void initComponents() {
		initMenu();
		initMainControlPanel();
		mainLayout();
	}

	private void initMenu() {
		menuBar();
		menuFile();
		menuEdit();
		menuScan();
		menuTools();
		menuHelp();
		setJMenuBar(menuBar);
	}

	private void menuBar() {
		menuBar = new JMenuBar();
	}

	private void menuFile() {
		file = new JMenu(Common.FILE);

		playListItem = new JMenuItem(Common.PLAY_LIST);
		// See the method <code>actionPerformed()</code> in this class
		playListItem.addActionListener(this);
		// Ctrl + Shift + P
		playListItem.setAccelerator(KeyStroke.getKeyStroke(Common.P, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
		file.add(playListItem);

		cleanPlayListItem = new JMenuItem(Common.CLEAN_PLAY_LIST);
		cleanPlayListItem.addActionListener(this);
		file.add(cleanPlayListItem);

		openItem = new JMenuItem(Common.OPEN);
		openItem.setAccelerator(KeyStroke.getKeyStroke(Common.O, InputEvent.CTRL_MASK));
		openItem.addActionListener(this);
		file.add(openItem);

		line = new JSeparator();
		file.add(line);

		exitItem = new JMenuItem(Common.EXIT);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(Common.W, InputEvent.CTRL_MASK));
		exitItem.addActionListener(this);
		file.add(exitItem);

		menuBar.add(file);
	}

	private void menuEdit() {
		edit = new JMenu(Common.EDIT);

		playItem = new JMenuItem(Common.PLAY);
		playItem.addActionListener(this);
		playItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		edit.add(playItem);

		stopItem = new JMenuItem(Common.STOP);
		stopItem.addActionListener(this);
		stopItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK + InputEvent.ALT_MASK));
		edit.add(stopItem);

		line = new JSeparator();
		edit.add(line);

		turnUpItem = new JMenuItem(Common.TURN_ON);
		turnUpItem.addActionListener(this);
		turnUpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_MASK));
		edit.add(turnUpItem);

		turnDownItem = new JMenuItem(Common.TURN_DOWN);
		turnDownItem.addActionListener(this);
		turnDownItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.CTRL_MASK));
		edit.add(turnDownItem);

		menuBar.add(edit);
	}

	private void menuScan() {
		scan = new JMenu(Common.SCAN);

		scanAllItem = new JMenuItem(Common.SCAN_ALL);
		scanAllItem.setAccelerator(KeyStroke.getKeyStroke(Common.F, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
		scanAllItem.addActionListener(this);
		scan.add(scanAllItem);

		scanInGivenDerectoryItem = new JMenuItem(Common.SCAN_IN_GIVEN_DIRECTORY);
		scanInGivenDerectoryItem.setAccelerator(KeyStroke.getKeyStroke(Common.F, InputEvent.CTRL_MASK));
		scanInGivenDerectoryItem.addActionListener(this);
		scan.add(scanInGivenDerectoryItem);

		menuBar.add(scan);
	}

	private void menuTools() {
		tools = new JMenu(Common.TOOLS);

		changeThemeItem = new JMenuItem(Common.CHANGE_THEME);
		changeThemeItem.addActionListener(this);
		changeThemeItem.setAccelerator(KeyStroke.getKeyStroke(Common.T, InputEvent.CTRL_MASK + InputEvent.ALT_DOWN_MASK));
		tools.add(changeThemeItem);

		changeSkinItem = new JMenuItem(Common.CHANGE_SKIN);
		changeSkinItem.addActionListener(this);
		changeSkinItem.setAccelerator(KeyStroke.getKeyStroke(Common.S, InputEvent.CTRL_MASK + InputEvent.ALT_DOWN_MASK));
		tools.add(changeSkinItem);

		changeWatermarkItem = new JMenuItem(Common.CHANGE_WATER_MARK);
		changeWatermarkItem.addActionListener(this);
		changeWatermarkItem.setAccelerator(KeyStroke.getKeyStroke(Common.G, InputEvent.CTRL_MASK + InputEvent.ALT_DOWN_MASK));
		tools.add(changeWatermarkItem);

		screenshotItem = new JMenuItem(Common.SCREENSHOT);
		screenshotItem.setAccelerator(KeyStroke.getKeyStroke(Common.H, InputEvent.CTRL_MASK + InputEvent.ALT_DOWN_MASK));
		screenshotItem.addActionListener(this);
		tools.add(screenshotItem);

		menuBar.add(tools);
	}

	private void menuHelp() {
		help = new JMenu(Common.HELP);

		aboutItem = new JMenuItem(Common.ABOUT);
		aboutItem.addActionListener(this);
		help.add(aboutItem);

		menuBar.add(help);
	}

	private void initMainControlPanel() {
		initPlayControlPanel();
		initPlayModeAndListControlPanel();
		initPlayProgress();
		initSongInfoSplitPane();
	}

	private void initPlayProgress() {
		playProgress = new JSlider();
		playProgress.setValue(0);
		playProgress.setToolTipText(Common.EMPTY + Common.ZERO);
	}

	private void initSongInfoSplitPane() {
		songInfoSplitPane = new JSplitPane();
		leftPanel = new JPanel();
		soundEffectBarA = new JProgressBar();
		soundEffectBarB = new JProgressBar();
		soundEffectBarC = new JProgressBar();
		soundEffectBarD = new JProgressBar();
		leftLine = new JSeparator();
		rightPanel = new JPanel();
		singerImageButton = new JButton();
		songName = new JLabel();

		soundEffectBarA.setValue(0);
		soundEffectBarB.setValue(0);
		soundEffectBarC.setValue(0);
		soundEffectBarD.setValue(0);

		soundEffectBarA.setOrientation(1);
		soundEffectBarB.setOrientation(1);
		soundEffectBarC.setOrientation(1);
		soundEffectBarD.setOrientation(1);

		leftPanelLayout();
		songInfoSplitPane.setLeftComponent(leftPanel);

		singerImageButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.JPG_17)));

		songName.setFont(new Font(Common.FONT_SONG_TI, 1, 12));
		songName.setHorizontalAlignment(SwingConstants.CENTER);
		songName.setText(Common.HTML_STOPPED);

		rightPanelLayout();
		songInfoSplitPane.setRightComponent(rightPanel);
	}

	private void initPlayModeAndListControlPanel() {
		playModeAndListControlPanel = new JPanel();
		playListButton = new JButton();
		playModeButton = new JButton();
		volume = new JSlider();
		volume_0 = new JLabel();
		volume_100 = new JLabel();

		playListButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_2)));
		playListButton.addActionListener(this);
		playListButton.setToolTipText(Common.PLAY_LIST);

		playModeButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_10)));
		playModeButton.addActionListener(this);
		playModeButton.setToolTipText(Common.SHUFFLE_MODE);

		volume.setOrientation(JSlider.VERTICAL);
		volume.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				volume_value = volume.getValue();
				adjustVolumeOperation();
			}
		});
		volume.setToolTipText(String.valueOf(volume_value));
		volume.setValue(volume_value);

		volume_0.setText(Common.LINE + Common.LINE + Common.ZERO + Common.LINE + Common.LINE);
		volume_100.setText(Common.LINE + Common.LINE + "1" + Common.ZERO + Common.ZERO + Common.LINE + Common.LINE);

		playModeAndListControlPanelLayout();
	}

	private void initPlayControlPanel() {
		playControlPanel = new JPanel();
		lastButton = new JButton();
		nextButton = new JButton();
		playOrSuspendButton = new JButton();
		previousButton = new JButton();
		firstButton = new JButton();
		stopButton = new JButton();

		lastButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_3)));
		lastButton.addActionListener(this);
		lastButton.setToolTipText(Common.LAST);

		nextButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_1)));
		nextButton.addActionListener(this);
		nextButton.setToolTipText(Common.NEXT);

		playOrSuspendButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_7)));
		playOrSuspendButton.addActionListener(this);
		playOrSuspendButton.setToolTipText(Common.PLAY);

		previousButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_9)));
		previousButton.addActionListener(this);
		previousButton.setToolTipText(Common.PREVIOUS);

		firstButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_8)));
		firstButton.addActionListener(this);
		firstButton.setToolTipText(Common.FIRST);

		stopButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_11)));
		stopButton.addActionListener(this);
		stopButton.setToolTipText(Common.STOP);

		playControlPanelLayout();
	}

	@SuppressWarnings("static-access")
	private void threadYield() {
		if (null != barsThread) {
			barsThread.yield();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		threadYield();
		actionForFileMenuItem(e);
		actionForEditMenuItem(e);
		actionForScanMenuItem(e);
		actionForToolsMenuItem(e);
		actionForHelpMenuItem(e);
		actionForPlayControlPanel(e);
		actionForPlayModeAndListControlPanel(e);
	}

	private void actionForFileMenuItem(ActionEvent e) {
		if (e.getSource() == playListItem) {
			playListUIOperation();
		} else if (e.getSource() == cleanPlayListItem) {
			cleanPlayListOperation();
		} else if (e.getSource() == openItem) {
			openOperation(MainUI.this);
		} else if (e.getSource() == exitItem) {
			int option = JOptionPane.showConfirmDialog(MainUI.this, Common.EXIT_SYSTEM, Common.CONFIM_EXIT, JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	public void openOperation(MainUI mainUI) {
		int beforeSize = 0;
		int afterSize = 0;
		if (!isPlayListEmpty()) {
			beforeSize = playList.size();
		}

		int id = OpenUtil.open(mainUI);
		if (id == -1) {
			OpenUtil.InvalidFileFormat(mainUI);
		}

		if (!isPlayListEmpty()) {
			afterSize = playList.size();
			if (afterSize != beforeSize) {
				refreshPlaylistUIWhenUIVisible();
				generatePlayListXML();
			}
		}
	}

	public void generatePlayListXML() {
		if (null == xmlUtil) {
			xmlUtil = new MusicPlayListForXMLUtil();
			xmlUtil.save(playList);
		} else {
			xmlUtil.save(playList);
		}
	}

	public void refreshPlaylistUIWhenUIVisible() {
		if (null != playList && null != playListUI && playListUI.isVisible()) {
			distoryPlayListUI();
			showPlayListUI();
		}
	}

	public void playListUIOperation() {
		setMainUIXY();
		if (null == playListUI) {
			showPlayListUI();
		} else {
			if (playListUI.isVisible()) {
				distoryPlayListUI();
			} else {
				showPlayListUI();
			}
		}
	}

	public void cleanPlayListOperation() {
		int option = JOptionPane.showConfirmDialog(MainUI.this, Common.CONFIM_CLEAN_PLAY_LIST, Common.CONFIM_CLEAN, JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			File file = new File(Common.HEAD_PATH);
			if (file.exists()) {
				File[] files = file.listFiles();
				for (File f : files) {
					if (f.exists() && f.isFile()) {
						f.delete();
					}
				}
			}
			file = new File(Common.PLAY_LIST_SAVE_PATH);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			playList = new ArrayList<PlayListVO>();
			refreshPlaylistUIWhenUIVisible();
			generatePlayListXML();
			
			stopOperation();
		}
	}

	private void distoryPlayListUI() {
		playListUI.setVisible(false);
		playListUI = null;
	}

	private void showPlayListUI() {
		playListUI = new PlayListUI(Common.PLAY_LIST);
		playListUI.setMainUI(MainUI.this);
		playListUI.setcurrentPlaySongNameLabel(songName.getText());
	}

	private void actionForEditMenuItem(ActionEvent e) {
		if (e.getSource() == playItem) {
			playOrSuspendOperation();
		} else if (e.getSource() == stopItem) {
			stopOperation();
		} else if (e.getSource() == turnUpItem) {
			if (volume_value < 100) {
				volume_value += 5;
			}
			adjustVolumeOperation();
		} else if (e.getSource() == turnDownItem) {
			if (volume_value > 0) {
				volume_value -= 5;
			}
			adjustVolumeOperation();
		}
	}

	private void actionForScanMenuItem(ActionEvent e) {
		if (e.getSource() == scanAllItem) {
			ScanDirectoryUtil.scanAllDirs();
		} else if (e.getSource() == scanInGivenDerectoryItem) {
			showScanInGivenDirectory();
		}
	}

	private void actionForToolsMenuItem(ActionEvent e) {
		if (e.getSource() == changeThemeItem) {
			changeThemeOperation();
		} else if (e.getSource() == changeSkinItem) {
			changeSkinOperation();
		} else if (e.getSource() == changeWatermarkItem) {
			changeWatermarkOperation();
		} else if (e.getSource() == screenshotItem) {
			this.setVisible(false);
			ScreenshotUtil.screenshot(MainUI.this);
			ScreenshotUtil.deskTopCapture = null;
		}
	}

	private void actionForHelpMenuItem(ActionEvent e) {
		if (e.getSource() == aboutItem) {
			showAbout();
		}
	}

	private void actionForPlayModeAndListControlPanel(ActionEvent e) {
		if (e.getSource() == playListButton) {
			playListOperation();
		} else if (e.getSource() == playModeButton) {
			playModeOperation();
		}
	}

	private void actionForPlayControlPanel(ActionEvent e) {
		if (e.getSource() == playOrSuspendButton) {
			playOrSuspendOperation();
		} else if (e.getSource() == nextButton) {
			nextOperation();
		} else if (e.getSource() == firstButton) {
			firstOperation();
		} else if (e.getSource() == lastButton) {
			lastOperation();
		} else if (e.getSource() == previousButton) {
			previousOperation();
		} else if (e.getSource() == stopButton) {
			stopOperation();
		}
	}

	private void adjustVolumeOperation() {
		volume.setToolTipText(String.valueOf(volume_value));
		volume.setValue(volume_value);
	}

	private void playModeOperation() {
		if (play_mode_value == Common.SHUFFLE_MODE_VALUE) {
			setPlayMode(Common.PNG_12, Common.REPEAT_ONCE_MODE, Common.REPEAT_ONCE_MODE_VALUE);
		} else if (play_mode_value == Common.REPEAT_ONCE_MODE_VALUE) {
			setPlayMode(Common.PNG_13, Common.ORDER_MODE, Common.ORDER_MODE_VALUE);
		} else if (play_mode_value == Common.ORDER_MODE_VALUE) {
			setPlayMode(Common.PNG_10, Common.SHUFFLE_MODE, Common.SHUFFLE_MODE_VALUE);
		}
	}

	private void setPlayMode(String icon, String desc, int value) {
		playModeButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(icon)));
		playModeButton.setToolTipText(desc);
		play_mode_value = value;
	}

	private void playListOperation() {
		playListUIOperation();
	}

	public void stopOperation() {
		if (null != musicUtil) {
			musicUtil.stop();
		}
		musicUtil = null;
		isStop = false;
		suspendOperation(false);
		playProgress.setValue(0);
		if (isPlayListNotEmpty()) {
			songName.setText(Common.HTML_STOPPED);
		} else {
			this.songName.setText(Common.HTML_PLAY_LIST_IS_EMPTY);
		}
		singerImageButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.JPG_17)));

		if (null != playListUI) {
			playListUI.suspendOperation(Common.HTML_STOPPED);
		}
	}

	private void previousOperation() {
		if (isPlayListNotEmpty()) {
			if (play_mode_value == Common.SHUFFLE_MODE_VALUE) {
				randomId();
				playSongOperation();
			} else if (play_mode_value == Common.REPEAT_ONCE_MODE_VALUE || play_mode_value == Common.ORDER_MODE_VALUE) {
				if (PlayListUI.selectId > 0) {
					PlayListUI.selectId--;
					playSongOperation();
				} else {
					this.previousButton.setToolTipText(Common.HTML_FIRST_ONE);
				}
			}
		} else {
			playListIsEmpty();
		}
	}

	private void lastOperation() {
		if (isPlayListNotEmpty()) {
			PlayListUI.selectId = playList.size() - 1;
			playSongOperation();
		} else {
			playListIsEmpty();
		}
	}

	private void firstOperation() {
		if (isPlayListNotEmpty()) {
			PlayListUI.selectId = Common.ZERO;
			playSongOperation();
		} else {
			playListIsEmpty();
		}
	}

	public void nextOperation() {
		if (isPlayListNotEmpty()) {
			if (play_mode_value == Common.SHUFFLE_MODE_VALUE) {
				randomId();
				playSongOperation();
			} else if (play_mode_value == Common.REPEAT_ONCE_MODE_VALUE || play_mode_value == Common.ORDER_MODE_VALUE) {
				if (PlayListUI.selectId < playList.size() - 1) {
					PlayListUI.selectId++;
					playSongOperation();
				} else {
					this.nextButton.setToolTipText(Common.HTML_LAST_ONE);
				}
			}
		} else {
			playListIsEmpty();
		}
	}

	public boolean isPlayListNotEmpty() {
		return !isPlayListEmpty();
	}

	private void playSongOperation() {
		if (null == playListUI) {
			playListUI = new PlayListUI(Common.PLAY_LIST);
			playListUI.setMainUI(MainUI.this);
			playListUI.setVisible(false);
		}
		playListUI.doubleClickToplayOperation();
	}

	private void playOrSuspendOperation() {
		playOperation();
	}

	public void suspendOperation(boolean exeFlab) {
		playItem.setText(Common.PLAY);
		playOrSuspendButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_7)));
		playOrSuspendButton.setToolTipText(Common.PLAY);
		isStop = true;

		if (null != playListUI) {
			playListUI.suspendOperation(songName.getText());
		}

		if (exeFlab && !isPlayListNotEmpty()) {
			playListIsEmpty();
		}
	}

	public void playOperation() {
		playStatus();

		if (isPlayListNotEmpty()) {
			int id = PlayListUI.selectId;
			if (isStop) { // stop
				if (isSuspend) { // suspend to stop
					// to play from suspend
					isSuspend = false;
					if (null != musicUtil) {
						if (null != musicUtil.getSourceDataLine() && !musicUtil.getSourceDataLine().isRunning()) {
							musicUtil.getSourceDataLine().start();
							isStop = false;
						}
					}
					playStatus();
					startSoundEffectBar();
				} else { // stop
					playSong();
				}
			} else { // running
				if (null != playListUI && playListUI.isVisible()) {
					playListUI.setSelectId();
				}
				if (id == PlayListUI.selectId) { // to suspend
					isSuspend = true;
					if (null != musicUtil) {
						if (null != musicUtil.getSourceDataLine() && musicUtil.getSourceDataLine().isRunning()) {
							isStop = true;
							musicUtil.getSourceDataLine().stop();
						}
					}
					suspendOperation(true);
				} else { // play other song
					playSong();
				}
			}

		} else {
			// Change back the button status(to play)
			suspendOperation(true);
		}
	}

	private void playStatus() {
		playItem.setText(Common.SUSPEND);
		playOrSuspendButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_5)));
		playOrSuspendButton.setToolTipText(Common.SUSPEND);

		if (null != playListUI) {
			playListUI.playOperation(songName.getText());
		}
	}

	private void playSong() {
		isStop = false;
		PlayListVO song = playList.get(PlayListUI.selectId);
		String name = song.getName();
		String path = song.getPath();
		String imagePath = song.getImagePath();
		playOperation(name, path, imagePath);
	}

	private void playListIsEmpty() {
		this.songName.setText(Common.HTML_PLAY_LIST_IS_EMPTY);
		stopOperation();
	}

	/**
	 * Play Operation
	 * @param name song name
	 * @param path song path
	 * @param imagePath singer image path
	 */
	public void playOperation(String name, String path, String imagePath) {
		isStop = false;

		playItem.setText(Common.SUSPEND);
		playOrSuspendButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_5)));
		playOrSuspendButton.setToolTipText(Common.SUSPEND);

		if (imagePath.startsWith(Common.COM)) {
			singerImageButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(imagePath)));
		} else {
			try {
				URL url = new URL(Common.FILE_WITH_COLOR + imagePath);
				singerImageButton.setIcon(new ImageIcon(url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		songName.setText(name);

		previousButton.setToolTipText(Common.PREVIOUS);
		nextButton.setToolTipText(Common.NEXT);

		if (null != playListUI) {
			playListUI.playOperation(songName.getText());
		}

		if (null == musicUtil) {
			musicUtil = new MusicUtil();
			musicUtil.setMainUI(MainUI.this);
			File file = new File(path);
			startSoundEffectBar();

			musicUtil.play(file);
			playProgress.setMaximum(MusicUtil.audioLength);
			addSampledControls();
		} else {
			stopOperation();
			playOperation(name, path, imagePath);
		}

	}

	private synchronized void startSoundEffectBar() {
		barsThread = new MusicEffectThreadUtil(soundEffectBarA, soundEffectBarB, soundEffectBarC, soundEffectBarD);
		barsThread.start();
	}

	private synchronized void showScanInGivenDirectory() {
		int beforeSize = 0;
		int afterSize = 0;
		if (!isPlayListEmpty()) {
			beforeSize = playList.size();
		}

		setMainUIXY();
		if (null == scanInGivenDirectoryUI) {
			scanInGivenDirectoryUI = new ScanInGivenDirectoryUI(Common.SCAN_IN_GIVEN_DIRECTORY);
			scanInGivenDirectoryUI.setMainUI(MainUI.this);
			if (null != playListUI) {
				scanInGivenDirectoryUI.setPlayListUI(playListUI);
			}
		} else {
			scanInGivenDirectoryUI.initSelf();
		}

		if (!isPlayListEmpty()) {
			afterSize = playList.size();
			if (afterSize != beforeSize) {
				refreshPlaylistUIWhenUIVisible();
				generatePlayListXML();
			}
		}
	}

	public void changeThemeOperation() {
		setMainUIXY();
		themeManagerUI = new ThemeManagerUI(Common.CHANGE_THEME);
		themeManagerUI.setMainUI(MainUI.this);
	}

	public void distoryThemeManagerUI() {
		if (null != themeManagerUI) {
			themeManagerUI = null;
		}
	}

	public void changeSkinOperation() {
		setMainUIXY();
		skinManagerUI = new SkinManagerUI(Common.CHANGE_SKIN);
		skinManagerUI.setMainUI(MainUI.this);
	}

	public void distorySkinManagerUI() {
		if (null != skinManagerUI) {
			skinManagerUI = null;
		}
	}

	public void changeWatermarkOperation() {
		setMainUIXY();
		watermarkManagerUI = new WatermarkManagerUI(Common.CHANGE_SKIN);
		watermarkManagerUI.setMainUI(MainUI.this);
	}

	public void distoryWatermarkManagerUI() {
		if (null != watermarkManagerUI) {
			watermarkManagerUI = null;
		}
	}

	private synchronized void showAbout() {
		setMainUIXY();
		aboutUI = new AboutUI(Common.ABOUT);
		aboutUI.setMainUI(MainUI.this);
	}

	public void distoryAboutUI() {
		if (null != aboutUI) {
			aboutUI = null;
		}
	}

	private void loadPlayList() {
		xmlUtil = new MusicPlayListForXMLUtil();
		playList = xmlUtil.loadPlayListFromXML();
	}

	private void setMainUIXY() {
		pointX = getMainUIX();
		pointY = getMainUIY();
	}

	private int getMainUIY() {
		return (int) getLocation().getY();
	}

	private int getMainUIX() {
		return (int) getLocation().getX();
	}

	public static boolean isPlayListEmpty() {
		return playList.isEmpty();
	}

	public PlayListUI getPlayListUI() {
		return this.playListUI;
	}

	private void randomId() {
		Random random = new Random();
		PlayListUI.selectId = random.nextInt(playList.size());
	}

	public JSlider getPlayProgress() {
		return this.playProgress;
	}

	private void addSampledControls() {
		try {
			FloatControl gainControl = (FloatControl) musicUtil.getSourceDataLine().getControl(FloatControl.Type.MASTER_GAIN);
			if (gainControl != null) {
				volume = createSlider(gainControl);
			}
		} catch (IllegalArgumentException e) {
			// If MASTER_GAIN volume control is unsupported, just skip it
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSlider createSlider(final FloatControl c) {
		if (c == null)
			return null;
		final JSlider s = new JSlider(0, 1000);
		final float min = c.getMinimum();
		final float max = c.getMaximum();
		final float width = max - min;
		float fval = c.getValue();
		s.setValue((int) ((fval - min) / width * 1000));

		Hashtable labels = new Hashtable(3);
		labels.put(new Integer(0), new JLabel(c.getMinLabel()));
		labels.put(new Integer(500), new JLabel(c.getMidLabel()));
		labels.put(new Integer(1000), new JLabel(c.getMaxLabel()));
		s.setLabelTable(labels);
		s.setPaintLabels(true);

		s.setBorder(new TitledBorder(c.getType().toString() + " " + c.getUnits()));

		s.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int i = s.getValue();
				float f = min + (i * width / 1000.0f);
				c.setValue(f);
			}
		});
		return s;
	}

	/**
	 * If not necessary, please do not change
	 */
	private void mainLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(songInfoSplitPane).addGap(0, 0, 0).addComponent(playModeAndListControlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(playControlPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(playProgress, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(songInfoSplitPane, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE).addComponent(playModeAndListControlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(1, 1, 1).addComponent(playProgress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(1, 1, 1).addComponent(playControlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		pack();
	}

	/**
	 * If not necessary, please do not change
	 */
	private void rightPanelLayout() {
		GroupLayout rightPanelLayout = new GroupLayout(rightPanel);
		rightPanel.setLayout(rightPanelLayout);
		rightPanelLayout.setHorizontalGroup(rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rightPanelLayout.createSequentialGroup().addGap(49, 49, 49).addComponent(singerImageButton).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup().addContainerGap().addComponent(songName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		rightPanelLayout.setVerticalGroup(rightPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(rightPanelLayout.createSequentialGroup().addGap(49, 49, 49).addComponent(singerImageButton).addGap(18, 18, 18).addComponent(songName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void leftPanelLayout() {
		GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
		leftPanel.setLayout(leftPanelLayout);
		leftPanelLayout.setHorizontalGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(leftPanelLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(soundEffectBarA, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(soundEffectBarB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(soundEffectBarC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(soundEffectBarD, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(15, 15, 15)).addComponent(leftLine));
		leftPanelLayout.setVerticalGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(leftPanelLayout.createSequentialGroup().addContainerGap(46, Short.MAX_VALUE).addGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(soundEffectBarB, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE).addComponent(soundEffectBarA, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE).addComponent(soundEffectBarC, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE).addComponent(soundEffectBarD, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(leftLine, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE).addGap(40, 40, 40)));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void playModeAndListControlPanelLayout() {
		GroupLayout playModeAndListControlPanelLayout = new GroupLayout(playModeAndListControlPanel);
		playModeAndListControlPanel.setLayout(playModeAndListControlPanelLayout);
		playModeAndListControlPanelLayout.setHorizontalGroup(playModeAndListControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(playModeAndListControlPanelLayout.createSequentialGroup().addGroup(playModeAndListControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(playListButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addComponent(playModeButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)).addGap(0, 0, Short.MAX_VALUE)).addGroup(playModeAndListControlPanelLayout.createSequentialGroup().addGroup(playModeAndListControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(playModeAndListControlPanelLayout.createSequentialGroup().addGap(20, 20, 20).addComponent(volume_0, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(playModeAndListControlPanelLayout.createSequentialGroup().addContainerGap().addGroup(playModeAndListControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(playModeAndListControlPanelLayout.createSequentialGroup().addGap(10, 10, 10).addComponent(volume, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)).addComponent(volume_100, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))).addContainerGap()));
		playModeAndListControlPanelLayout.setVerticalGroup(playModeAndListControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, playModeAndListControlPanelLayout.createSequentialGroup().addComponent(playListButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(playModeButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(volume_100).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(volume, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(volume_0).addContainerGap()));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void playControlPanelLayout() {
		GroupLayout playControlPanelLayout = new GroupLayout(playControlPanel);
		playControlPanel.setLayout(playControlPanelLayout);
		playControlPanelLayout.setHorizontalGroup(playControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, playControlPanelLayout.createSequentialGroup().addContainerGap(16, Short.MAX_VALUE).addComponent(stopButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(firstButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(previousButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(playOrSuspendButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE).addGap(10, 10, 10).addComponent(nextButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(lastButton, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE).addGap(10, 10, 10)));
		playControlPanelLayout.setVerticalGroup(playControlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, playControlPanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(playControlPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(stopButton).addComponent(firstButton).addComponent(previousButton).addComponent(playOrSuspendButton).addComponent(nextButton).addComponent(lastButton)).addContainerGap()));
	}
}
