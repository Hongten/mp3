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
 * @created 2014-8-1
 */
@SuppressWarnings("rawtypes")
public class SkinManagerUI extends MainUI {
	private static final long serialVersionUID = -37011351219515242L;

	private JLabel currentSkinDescJLabel;
	private JLabel currentSkinJLabel;
	private JLabel descJlabel;
	private JSeparator line;
	private JComboBox sinkJComboBox;
	
	private MainUI mainUI;
	
	public String[][] skins = { { "AutumnSkin", "1", "<html><a href=''>What is the AutumnSkin skin?</a></html>" },
			{ "BusinessBlackSteelSkin", "2", "<html><a href=''>What is the BusinessBlackSteelSkin skin?</a></html>" }, 
			{ "ChallengerDeepSkin", "3", "<html><a href=''>What is the ChallengerDeepSkin skin?</a></html>" },
			{ "CremeCoffeeSkin", "4", "<html><a href=''>What is the CremeCoffeeSkin skin?</a></html>" }, 
			{ "CremeSkin", "5", "<html><a href=''>What is the CremeSkin skin?</a></html>" }, 
			{ "EbonyHighContrastSkin", "6", "<html><a href=''>What is the EbonyHighContrastSkin skin?</a></html>" }, 
			{ "EmeraldDuskSkin", "7", "<html><a href=''>What is the EmeraldDuskSkin skin?</a></html>" }, 
			{ "FieldOfWheatSkin", "8", "<html><a href=''>What is the FieldOfWheatSkin skin?</a></html>" }, 
			{ "FindingNemoSkin", "9", "<html><a href=''>What is the FindingNemoSkin skin?</a></html>" }, 
			{ "GreenMagicSkin", "10", "<html><a href=''>What is the GreenMagicSkin skin?</a></html>" }, 
			{ "MagmaSkin", "11", "<html><a href=''>What is the MagmaSkin skin?</a></html>" }, 
			{ "MangoSkin", "12", "<html><a href=''>What is the MangoSkin skin?</a></html>" }, 
			{ "MistSilverSkin", "13", "<html><a href=''>What is the MistSilverSkin skin?</a></html>" },
			{ "ModerateSkin", "14", "<html><a href=''>What is the ModerateSkin skin?</a></html>" }, 
			{ "NebulaBrickWallSkin", "15", "<html><a href=''>What is the NebulaBrickWallSkin skin?</a></html>" }, 
			{ "NebulaSkin", "16", "<html><a href=''>What is the NebulaSkin skin?</a></html>" }, 
			{ "OfficeBlue2007Skin", "17", "<html><a href=''>What is the OfficeBlue2007Skin skin?</a></html>" }, 
			{ "RavenGraphiteGlassSkin", "18", "<html><a href=''>What is the RavenGraphiteGlassSkin skin?</a></html>" }, 
			{ "RavenGraphiteSkin", "19", "<html><a href=''>What is the RavenGraphiteSkin skin?</a></html>" }, 
			{ "RavenSkin", "20", "<html><a href=''>What is the RavenSkin skin?</a></html>" }, 
			{ "SaharaSkin", "21", "<html><a href=''>What is the SaharaSkin skin?</a></html>" } };

	private Object[] skinNames() {
		Object[] os = new Object[skins.length];
		for (int i = 0; i < skins.length; i++) {
			os[i] = skins[i][0];
		}
		return os;
	}

	private Object[] getSkinDetails(Object obj) {
		for (int i = 0; i < skins.length; i++) {
			if (skins[i][0].equals(obj)) {
				Object[] os = new Object[skins[i].length - 1];
				for (int j = 0; j < os.length; j++) {
					os[j] = skins[i][j + 1];
				}
				return os;
			}
		}
		return new Object[] {};
	}

	public SkinManagerUI(String title) {
		super(title);
		initComponents();

		initSelf();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SkinManagerUI.this.setVisible(false);
				mainUI.distorySkinManagerUI();
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
		currentSkinJLabel.setText(Common.CURRENT_SINK);

		Object[] skinNames = skinNames();
		sinkJComboBox.setModel(new DefaultComboBoxModel(skinNames));
		sinkJComboBox.setSelectedIndex(skinNum);
		sinkJComboBox.addActionListener(this);

		descJlabel.setText(Common.DESCRIPTION_WITH_COLOR);

		currentSkinDescJLabel.setText(skins[skinNum][2]);
		currentSkinDescJLabel.addMouseListener(new MouseListener(){
			 
			@Override
			public void mouseClicked(MouseEvent e) {
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + Common.SUBSTANCE_SKINS_PAGE + sinkJComboBox.getSelectedItem());
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
		currentSkinJLabel = new JLabel();
		sinkJComboBox = new JComboBox();
		descJlabel = new JLabel();
		currentSkinDescJLabel = new JLabel();
		line = new JSeparator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sinkJComboBox) {
			updateSkin();
		}
	}

	public synchronized void updateSkin() {
		Object[] os = getSkinDetails(sinkJComboBox.getSelectedItem());
		String index = (String) os[0];
		String desc = (String) os[1];
		skinNum = Integer.valueOf(index);
		currentSkinDescJLabel.setText(desc);
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
								layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(currentSkinJLabel)
										.addComponent(sinkJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26)
						.addComponent(line, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(descJlabel).addGap(18, 18, 18).addComponent(currentSkinDescJLabel).addContainerGap(47, Short.MAX_VALUE)));
	}

	private void horizontalGroupLayout(GroupLayout layout) {
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(21, 21, 21)
								.addGroup(
										layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(currentSkinDescJLabel)
												.addComponent(descJlabel)
												.addGroup(
														layout.createSequentialGroup().addComponent(currentSkinJLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(sinkJComboBox, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addComponent(line, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));
	}
}
