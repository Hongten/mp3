package com.b510.mp3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.ScanDirectoryUtil;

/**
 * @author Hongten
 * @created 2014-8-6
 */
public class ScanInGivenDirectoryUI extends MainUI {
	private static final long serialVersionUID = 1L;

	private JPanel backGroundPanel;
	private JPanel buttomPanel;
	private JSeparator buttomjSeparator;
	private JTextField dirPathJTextField;
	private JSeparator jSeparator1;
	private JPanel middlePanel;
	private JLabel numberJLabel;
	private JLabel pathDescJLabel;
	private JLabel scanDescJLabel;
	private JButton scanJButton;
	private JLabel selectDescJLabel;
	private JLabel selectDirDescJLabel;
	private JPanel topPanel;
	private JLabel totalJLabel;

	public static String dirPathJTextFieldValue;
	public static int numberJLabelValue;
	public static String selectDirectoryPath = Common.DEFAULT_DIRECTORY_PATH;
	public static String pathDescJLabelValue = Common.EMPTY;
	private static boolean freshenPageControl = true;
	private static Thread freshenPageThread;
	public static boolean scanJobCompleteFlag = false;

	private MainUI mainUI;
	private PlayListUI playListUI;

	public ScanInGivenDirectoryUI(String title) {
		super(title);
		initComponents();

		initSelf();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ScanInGivenDirectoryUI.this.setVisible(false);
			}
		});
	}

	public void initSelf() {
		this.setVisible(true);
		setSize(650,212);
		this.setLocation(MainUI.pointX + 60, MainUI.pointY + 190);
	}

	private void initElement() {
		backGroundPanel = new JPanel();
		topPanel = new JPanel();
		selectDirDescJLabel = new JLabel();
		jSeparator1 = new JSeparator();
		middlePanel = new JPanel();
		selectDescJLabel = new JLabel();
		dirPathJTextField = new JTextField();
		scanJButton = new JButton();
		buttomPanel = new JPanel();
		scanDescJLabel = new JLabel();
		pathDescJLabel = new JLabel();
		totalJLabel = new JLabel();
		numberJLabel = new JLabel();
		buttomjSeparator = new JSeparator();
	}

	private void initComponents() {
		initElement();
		initTopPanel();
		initMiddlePanel();
		initButtomPanel();
		backGroundPanelLayout();
		pack();
	}

	private void initButtomPanel() {
		scanDescJLabel.setText(Common.SCANING);

		pathDescJLabel.setText(Common.EMPTY);

		totalJLabel.setText(Common.TOTAL);

		numberJLabelValue = Common.ZERO;
		numberJLabel.setText(String.valueOf(numberJLabelValue));

		buttomPanelLayout();
	}

	private void initMiddlePanel() {
		selectDescJLabel.setText(Common.PATH);

		String path = System.getProperty(Common.USER_DIR);
		dirPathJTextFieldValue = path.trim();
		dirPathJTextField.setText(path);
		dirPathJTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					String path = ScanDirectoryUtil.getPathOfDirectory();
					dirPathJTextFieldValue = path.trim();
					dirPathJTextField.setText(path);
				}
			}
		});

		scanJButton.setText(Common.SCAN);
		scanJButton.addActionListener(this);

		middlePanelLayout();
	}

	private void initTopPanel() {
		selectDirDescJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectDirDescJLabel.setText(Common.SELECT_DIR_DESC);

		topPanelLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == scanJButton) {
			scan();
		}
	}

	private synchronized void scan() {
		selectDirectoryPath = dirPathJTextFieldValue;
		if (selectDirectoryPath != null && !Common.EMPTY.equals(selectDirectoryPath)) {
			numberJLabelValue = Common.ZERO;
			numberJLabel.setText(String.valueOf(numberJLabelValue));
			startFreshenPage();
			ScanDirectoryUtil.setScanInGivenDirectoryUI(ScanInGivenDirectoryUI.this);
			ScanDirectoryUtil.scanSongs(selectDirectoryPath.trim(), true);
			if (null != playListUI && playListUI.isVisible()) {
				mainUI.refreshPlaylistUIWhenUIVisible();
			}
			mainUI.generatePlayListXML();
		} else {
			JOptionPane.showMessageDialog(this, Common.SELECT_DIR);
		}
	}

	private void startFreshenPage() {
		freshenPageControl = true;
		// freshPathDescJLabel();
		freshenPage();
	}

	public void freshenPage() {
		freshenPageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (freshenPageControl) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
					scanJobComplete();
				}
			}
		});
		freshenPageThread.start();
	}

	private void scanJobComplete() {
		if (scanJobCompleteFlag) {
			stopFreshenPage();
		}
	}

	@SuppressWarnings("deprecation")
	private void stopFreshenPage() {
		freshenPageControl = false;
		if (null != freshenPageThread) {
			if (freshenPageThread.isAlive()) {
				freshenPageThread.stop();
				// freshenPageThread = null;
			}
		}
	}

	public void setPathDescJLabel(String path) {
		this.pathDescJLabel.setText(path);
	}

	public void setnumberJLabel(String numStr) {
		this.numberJLabel.setText(numStr);
	}

	public void setMainUI(MainUI mUI) {
		this.mainUI = mUI;
	}

	public void setPlayListUI(PlayListUI pUI) {
		this.playListUI = pUI;
	}

	/**
	 * If not necessary, please do not change
	 */
	private void backGroundPanelLayout() {
		GroupLayout backGroundPanelLayout = new GroupLayout(backGroundPanel);
		backGroundPanel.setLayout(backGroundPanelLayout);
		backGroundPanelLayout.setHorizontalGroup(backGroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(middlePanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(buttomPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		backGroundPanelLayout.setVerticalGroup(backGroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				backGroundPanelLayout.createSequentialGroup().addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0).addComponent(middlePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)
						.addComponent(buttomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(backGroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(backGroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void buttomPanelLayout() {
		GroupLayout buttomPanelLayout = new GroupLayout(buttomPanel);
		buttomPanel.setLayout(buttomPanelLayout);
		buttomPanelLayout.setHorizontalGroup(buttomPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						buttomPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(totalJLabel, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
												.addComponent(scanDescJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(pathDescJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(numberJLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap())
				.addComponent(buttomjSeparator, GroupLayout.Alignment.TRAILING));
		buttomPanelLayout.setVerticalGroup(buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				buttomPanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(buttomjSeparator, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(scanDescJLabel).addComponent(pathDescJLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(totalJLabel).addComponent(numberJLabel)).addContainerGap()));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void middlePanelLayout() {
		GroupLayout middlePanelLayout = new GroupLayout(middlePanel);
		middlePanel.setLayout(middlePanelLayout);
		middlePanelLayout.setHorizontalGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				middlePanelLayout.createSequentialGroup().addContainerGap().addComponent(selectDescJLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(dirPathJTextField).addGap(18, 18, 18).addComponent(scanJButton).addContainerGap()));
		middlePanelLayout.setVerticalGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				middlePanelLayout
						.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								middlePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(selectDescJLabel)
										.addComponent(dirPathJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(scanJButton)).addContainerGap()));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void topPanelLayout() {
		GroupLayout topPanelLayout = new GroupLayout(topPanel);
		topPanel.setLayout(topPanelLayout);
		topPanelLayout.setHorizontalGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jSeparator1, GroupLayout.Alignment.TRAILING)
				.addComponent(selectDirDescJLabel, GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE));
		topPanelLayout.setVerticalGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				topPanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(selectDirDescJLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)
						.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)));
	}

}
