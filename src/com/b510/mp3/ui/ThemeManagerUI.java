package com.b510.mp3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;

import com.b510.mp3.common.Common;

/**
 * @author Hongten
 * @created 2014-8-4
 */
@SuppressWarnings("rawtypes")
public class ThemeManagerUI extends MainUI {
	private static final long serialVersionUID = 1L;
	private JLabel currentThemeDescJLabel;
	private JLabel currentThemeJLabel;
	private JLabel descJlabel;
	private JSeparator line;
	private JComboBox themeJComboBox;
	
	private MainUI mainUI;
	
	public String[][] themes = { { "SubstanceAquaTheme", "1", "<html><a href='http://www.baidu.com'>baidu</a>current skin is good skin, and you <br> can change the color for this skin.</html>" },
			{ "SubstanceBarbyPinkTheme", "2", "This is SubstanceBarbyPinkTheme" }, 
			{ "SubstanceBottleGreenTheme", "3", "This is SubstanceBottleGreenTheme" },
			{ "SubstanceBrownTheme", "4", "This is SubstanceBrownTheme" }, 
			{ "SubstanceCharcoalTheme", "5", "This is SubstanceCharcoalTheme" }, 
			{ "SubstanceCremeTheme", "6", "This is SubstanceCremeTheme" },
			{ "SubstanceDarkVioletTheme", "7", "This is SubstanceDarkVioletTheme" }, 
			{ "SubstanceDesertSandTheme", "8", "This is SubstanceDesertSandTheme" }, 
			{ "SubstanceEbonyTheme", "9", "This is SubstanceEbonyTheme" },
			{ "SubstanceJadeForestTheme", "10", "This is SubstanceJadeForestTheme" }, 
			{ "SubstanceLightAquaTheme", "11", "This is SubstanceLightAquaTheme" }, 
			{ "SubstanceLimeGreenTheme", "12", "This is SubstanceLimeGreenTheme" },
			{ "SubstanceOliveTheme", "14", "This is SubstanceOliveTheme" }, 
			{ "SubstanceOrangeTheme", "15", "This is SubstanceOrangeTheme" },
			{ "SubstancePurpleTheme", "16", "This is SubstancePurpleTheme" }, 
			{ "SubstanceRaspberryTheme", "17", "This is SubstanceRaspberryTheme" },
			{ "SubstanceSepiaTheme", "18", "This is SubstanceSepiaTheme" }, 
			{ "SubstanceSteelBlueTheme", "19", "This is SubstanceSteelBlueTheme" },
			{ "SubstanceSunGlareTheme", "20", "This is SubstanceSunGlareTheme" }, 
			{ "SubstanceSunsetTheme", "21", "This is SubstanceSunsetTheme" },
			{ "SubstanceTerracottaTheme", "21", "This is SubstanceTerracottaTheme" }};

	private Object[] themeNames() {
		Object[] os = new Object[themes.length];
		for (int i = 0; i < themes.length; i++) {
			os[i] = themes[i][0];
		}
		return os;
	}

	private Object[] getThemeDetails(Object obj) {
		for (int i = 0; i < themes.length; i++) {
			if (themes[i][0].equals(obj)) {
				Object[] os = new Object[themes[i].length - 1];
				for (int j = 0; j < os.length; j++) {
					os[j] = themes[i][j + 1];
				}
				return os;
			}
		}
		return new Object[] {};
	}

	public ThemeManagerUI(String title) {
		super(title);
		initComponents();

		initSelf();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ThemeManagerUI.this.setVisible(false);
				mainUI.distoryThemeManagerUI();
			}
		});
	}

	public void initSelf() {
		this.setVisible(true);
		setResizable(false);
		this.setLocation(MainUI.pointX + 60, MainUI.pointY + 190);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		initElement();
		currentThemeJLabel.setText(Common.CURRENT_THEME);

		Object[] themeNames = themeNames();
		themeJComboBox.setModel(new DefaultComboBoxModel(themeNames));
		themeJComboBox.setSelectedIndex(themeNum);
		themeJComboBox.addActionListener(this);

		descJlabel.setText(Common.DESCRIPTION_WITH_COLOR);

		currentThemeDescJLabel.setText(themes[themeNum][2]);
		currentThemeDescJLabel.addMouseListener(new MouseListener(){
			 
			@Override
			public void mouseClicked(MouseEvent e) {
			try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler  http://www.baidu.com");
			} catch (IOException e1) {
			e1.printStackTrace();
			}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}});
		pageGourpLayout();
	}

	private void initElement() {
		currentThemeJLabel = new JLabel();
		themeJComboBox = new JComboBox();
		descJlabel = new JLabel();
		currentThemeDescJLabel = new JLabel();
		line = new JSeparator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == themeJComboBox) {
			updateTheme();
		}
	}

	public synchronized void updateTheme() {
		Object[] os = getThemeDetails(themeJComboBox.getSelectedItem());
		String index = (String) os[0];
		String desc = (String) os[1];
		themeNum = Integer.valueOf(index);
		currentThemeDescJLabel.setText(desc);
		setJUI();
	}
	
	public void setMainUI(MainUI mUI){
		this.mainUI = mUI;
	}

	/**
	 * If not necessary, please do not change
	 */
	private void pageGourpLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		horizontalGroupLayout(layout);
		verticalGroupLayout(layout);
		pack();
	}

	private void verticalGroupLayout(GroupLayout layout) {
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(40, 40, 40)
						.addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(currentThemeJLabel)
										.addComponent(themeJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26)
						.addComponent(line, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(descJlabel).addGap(18, 18, 18).addComponent(currentThemeDescJLabel).addContainerGap(47, Short.MAX_VALUE)));
	}

	private void horizontalGroupLayout(GroupLayout layout) {
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(21, 21, 21)
								.addGroup(
										layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(currentThemeDescJLabel)
												.addComponent(descJlabel)
												.addGroup(
														layout.createSequentialGroup().addComponent(currentThemeJLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(themeJComboBox, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addComponent(line, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));
	}
}
