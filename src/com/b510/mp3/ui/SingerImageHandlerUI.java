package com.b510.mp3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.b510.mp3.common.Common;
import com.b510.mp3.util.CommonUtil;
import com.b510.mp3.util.OpenUtil;
import com.b510.mp3.util.ScaledImageUtil;

public class SingerImageHandlerUI extends MainUI {
	private static final long serialVersionUID = 1L;

	private JPanel backGroundPanel;
	private JPanel buttomPanel;
	private JSeparator buttomjSeparator;
	private JButton cancelButton;
	private JTextField imagePathTextField;
	private JPanel middlePanel;
	private JButton previewButton;
	private JLabel selectImageDescLabel;
	private JLabel selectLabel;
	private JButton setButton;
	private JPanel topPanel;
	private JSeparator topjSeparator;

	private String imagePathTextFieldValue = Common.DEFAULT_DIRECTORY_PATH;
	private String tempHeadImagePath = Common.EMPTY;

	private MainUI mainUI;

	public SingerImageHandlerUI(String title) {
		super(title);
		initComponents();

		initSelf();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SingerImageHandlerUI.this.setVisible(false);
				deleteTempFile();
			}
		});
	}

	public void initSelf() {
		this.setVisible(true);
		this.setLocation(MainUI.pointX + 80, MainUI.pointY + 55);
	}

	private void initElement() {
		backGroundPanel = new JPanel();
		topPanel = new JPanel();
		selectImageDescLabel = new JLabel();
		topjSeparator = new JSeparator();
		middlePanel = new JPanel();
		selectLabel = new JLabel();
		imagePathTextField = new JTextField();
		setButton = new JButton();
		cancelButton = new JButton();
		buttomPanel = new JPanel();
		previewButton = new JButton();
		buttomjSeparator = new JSeparator();
	}

	private void initComponents() {
		initElement();
		initTopPanelLayout();
		initMiddlePanelLayout();
		initButtomPanelLayout();
		backGroundPanelLayout();
		pack();
	}

	private void initTopPanelLayout() {
		selectImageDescLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectImageDescLabel.setText(Common.HTML_SELECT_IMAGE_DESC);

		topPanelLayout();
	}

	private void initMiddlePanelLayout() {
		selectLabel.setText(Common.SELECT_IMAGE);

		String path = playList.get(PlayListUI.selectId_button3).getImagePath();
		imagePathTextFieldValue = path.trim();
		imagePathTextField.setText(path);
		imagePathTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1 || e.getClickCount() == 2) {
					openOperation();
				}
			}
		});
		setButton.setText(Common.SET);
		setButton.addActionListener(this);

		cancelButton.setText(Common.CANCEL);
		cancelButton.addActionListener(this);

		middlePanelLayout();
	}

	private void initButtomPanelLayout() {
		if (imagePathTextFieldValue.startsWith(Common.COM)) {
			previewButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(imagePathTextFieldValue)));
		} else {
			try {
				URL url = new URL(Common.FILE_WITH_COLOR + imagePathTextFieldValue);
				previewButton.setIcon(new ImageIcon(url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		buttomPanelLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == setButton) {
			setOperation();
		} else if (e.getSource() == cancelButton) {
			cancelOperation();
		}
	}

	private void openOperation() {
		String imagePath = OpenUtil.open(Common.JPG);
		if (Common.EMPTY.equals(imagePath)) {
			imagePathTextField.setText(imagePathTextFieldValue);
			File file = new File(imagePathTextField.getText().trim());
			if (file.isDirectory()) {
				JOptionPane.showMessageDialog(SingerImageHandlerUI.this, Common.INVALID_FILE_FORMAT_JPG);
			}
		} else {
			imagePathTextField.setText(imagePath);
			imagePathTextFieldValue = imagePath;
			File file = new File(imagePathTextFieldValue);
			if (file.exists() && file.isFile()) {
				previewImage(imagePath);
				try {
					Thread.sleep(2000); // waiting for 2s
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Preview the image in the <code>SingerImageHandlerUI</code>
	 * 
	 * @param imagePath
	 *            image path
	 */
	private void previewImage(String imagePath) {
		String singerImageTempHeadPath = scaledImage(imagePath);
		try {
			URL url = new URL(Common.FILE_WITH_COLOR + singerImageTempHeadPath);
			previewButton.setIcon(new ImageIcon(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	// Scaled image
	private String scaledImage(String imagePath) {
		String singerImageTempHeadPath = getSingerImageTempHeadPath(imagePath);
		ScaledImageUtil.scaledImage(imagePathTextFieldValue, 2, singerImageTempHeadPath);
		try {
			Thread.sleep(2000); // waiting for 2s
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return singerImageTempHeadPath;
	}

	private String getSingerImageTempHeadPath(String imagePath) {
		isHeadPathExist();
		String postFix = ScaledImageUtil.getPostfix(imagePath);
		tempHeadImagePath = Common.HEAD_PATH + Common.TEMP_HEAD + CommonUtil.getCalandarString() + Common.FULL_SPOT + postFix;
		return tempHeadImagePath;
	}

	private File isHeadPathExist() {
		File file = new File(Common.HEAD_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	private void setOperation() {
		String pathInTextField = imagePathTextField.getText();
		String oldPath = playList.get(PlayListUI.selectId_button3).getImagePath();
		if (!pathInTextField.isEmpty() && (!pathInTextField.startsWith(Common.COM) && !pathInTextField.equals(oldPath))) {
			String name = CommonUtil.getLastName(tempHeadImagePath);
			String postFix = ScaledImageUtil.getPostfix(pathInTextField);
			String currentPath = Common.HEAD_PATH + name.substring(5, name.length()) + CommonUtil.getCalandarString() + Common.FULL_SPOT + postFix;
			File tempFile = new File(tempHeadImagePath);
			if (tempFile.exists() && tempFile.isFile()) {
				tempFile.renameTo(new File(currentPath));
				playList.get(PlayListUI.selectId_button3).setImagePath(currentPath.replace(Common.BLACKSLASH, Common.ELLIPSIS_SIGN));
				mainUI.refreshPlaylistUIWhenUIVisible();
				mainUI.generatePlayListXML();
				deleteOldFile(oldPath);
			}
		}
		this.setVisible(false);
		deleteTempFile();
	}

	private void deleteOldFile(String oldPath) {
		File oldFile = new File(oldPath);
		if (oldFile.exists() && oldFile.isFile()) {
			oldFile.delete();
		}
	}

	private void deleteTempFile() {
		File file = isHeadPathExist();
		File[] files = file.listFiles();
		for (File f : files) {
			String name = f.getName();
			if (name.startsWith(Common.TEMP_HEAD)) {
				f.delete();
			}
		}
	}

	private void cancelOperation() {
		this.setVisible(false);
		deleteTempFile();
	}

	public void setMainUI(MainUI mUI) {
		this.mainUI = mUI;
	}

	/**
	 * If not necessary, please do not change
	 */
	private void topPanelLayout() {
		GroupLayout topPanelLayout = new GroupLayout(topPanel);
		topPanel.setLayout(topPanelLayout);
		topPanelLayout.setHorizontalGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(topPanelLayout.createSequentialGroup().addContainerGap().addGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(selectImageDescLabel).addComponent(topjSeparator)).addContainerGap()));
		topPanelLayout.setVerticalGroup(topPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(topPanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(selectImageDescLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(topjSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void middlePanelLayout() {
		GroupLayout middlePanelLayout = new GroupLayout(middlePanel);
		middlePanel.setLayout(middlePanelLayout);
		middlePanelLayout.setHorizontalGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(middlePanelLayout.createSequentialGroup().addContainerGap().addComponent(selectLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(middlePanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(setButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)).addComponent(imagePathTextField)).addContainerGap()));
		middlePanelLayout.setVerticalGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(middlePanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(selectLabel).addComponent(imagePathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(middlePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(setButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void buttomPanelLayout() {
		GroupLayout buttomPanelLayout = new GroupLayout(buttomPanel);
		buttomPanel.setLayout(buttomPanelLayout);
		buttomPanelLayout.setHorizontalGroup(buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(buttomPanelLayout.createSequentialGroup().addComponent(buttomjSeparator).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, buttomPanelLayout.createSequentialGroup().addGap(0, 88, Short.MAX_VALUE).addComponent(previewButton).addGap(88, 88, 88)));
		buttomPanelLayout.setVerticalGroup(buttomPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, buttomPanelLayout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(buttomjSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(previewButton).addContainerGap()));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void backGroundPanelLayout() {
		GroupLayout backGroundPanelLayout = new GroupLayout(backGroundPanel);
		backGroundPanel.setLayout(backGroundPanelLayout);
		backGroundPanelLayout.setHorizontalGroup(backGroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(topPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(middlePanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(buttomPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		backGroundPanelLayout.setVerticalGroup(backGroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(backGroundPanelLayout.createSequentialGroup().addContainerGap().addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(0, 0, 0).addComponent(middlePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE).addComponent(buttomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(backGroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(backGroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
	}
}
