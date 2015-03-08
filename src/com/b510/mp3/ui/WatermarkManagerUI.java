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
public class WatermarkManagerUI extends MainUI {
	private static final long serialVersionUID = 1L;
	
	private JLabel currentWatermarkDescJLabel;
	private JLabel currentWatermarkJLabel;
	private JLabel descJlabel;
	private JSeparator line;
	private JComboBox watermarkJComboBox;
	
	private MainUI mainUI;
	
	public String[][] watermarks = { { "SubstanceBubblesWatermark", "1", "<html><a href='http://www.baidu.com'>baidu</a>current skin is good skin, and you <br> can change the color for this skin.</html>" },
			{ "SubstanceBinaryWatermark", "2", "This is SubstanceBinaryWatermark" }, 
			{ "SubstanceCopperplateEngravingWatermark", "3", "This is SubstanceCopperplateEngravingWatermark" },
			{ "SubstanceCrosshatchWatermark", "4", "This is SubstanceCrosshatchWatermark" }, 
			{ "SubstanceFabricWatermark", "5", "This is SubstanceFabricWatermark" }, 
			{ "SubstanceGenericNoiseWatermark", "6", "This is SubstanceGenericNoiseWatermark" },
			{ "SubstanceKatakanaWatermark", "7", "This is SubstanceKatakanaWatermark" }, 
			{ "SubstanceLatchWatermark", "8", "This is SubstanceLatchWatermark" }, 
			{ "SubstanceMagneticFieldWatermark", "9", "This is SubstanceMagneticFieldWatermark" },
			{ "SubstanceMarbleVeinWatermark", "10", "This is SubstanceMarbleVeinWatermark" }, 
			{ "SubstanceMazeWatermark", "11", "This is SubstanceMazeWatermark" }, 
			{ "SubstanceMetalWallWatermark", "12", "This is SubstanceMetalWallWatermark" },
			{ "SubstanceMosaicWatermark", "13", "This is SubstanceMosaicWatermark" }, 
			{ "SubstanceNoneWatermark", "14", "This is SubstanceNoneWatermark" }, 
			{ "SubstanceNullWatermark", "15", "This is SubstanceNullWatermark" },
			{ "SubstancePlanktonWatermark", "16", "This is SubstancePlanktonWatermark" }, 
			{ "SubstanceStripeWatermark", "17", "This is SubstanceStripeWatermark" },
			{ "SubstanceWoodWatermark", "18", "This is SubstanceWoodWatermark" }};

	private Object[] watermarkNames() {
		Object[] os = new Object[watermarks.length];
		for (int i = 0; i < watermarks.length; i++) {
			os[i] = watermarks[i][0];
		}
		return os;
	}

	private Object[] getWatermarkDetails(Object obj) {
		for (int i = 0; i < watermarks.length; i++) {
			if (watermarks[i][0].equals(obj)) {
				Object[] os = new Object[watermarks[i].length - 1];
				for (int j = 0; j < os.length; j++) {
					os[j] = watermarks[i][j + 1];
				}
				return os;
			}
		}
		return new Object[] {};
	}

	public WatermarkManagerUI(String title) {
		super(title);
		initComponents();

		initSelf();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				WatermarkManagerUI.this.setVisible(false);
				mainUI.distoryWatermarkManagerUI();
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
		currentWatermarkJLabel.setText(Common.CURRENT_WATER_MARK);

		Object[] watermarkNames = watermarkNames();
		watermarkJComboBox.setModel(new DefaultComboBoxModel(watermarkNames));
		watermarkJComboBox.setSelectedIndex(watermarkNum);
		watermarkJComboBox.addActionListener(this);

		descJlabel.setText(Common.DESCRIPTION_WITH_COLOR);

		currentWatermarkDescJLabel.setText(watermarks[watermarkNum][2]);
		currentWatermarkDescJLabel.addMouseListener(new MouseListener(){
			 
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
		currentWatermarkJLabel = new JLabel();
		watermarkJComboBox = new JComboBox();
		descJlabel = new JLabel();
		currentWatermarkDescJLabel = new JLabel();
		line = new JSeparator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == watermarkJComboBox) {
			updateWatermark();
		}
	}

	public synchronized void updateWatermark() {
		Object[] os = getWatermarkDetails(watermarkJComboBox.getSelectedItem());
		String index = (String) os[0];
		String desc = (String) os[1];
		watermarkNum = Integer.valueOf(index);
		currentWatermarkDescJLabel.setText(desc);
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
								layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(currentWatermarkJLabel)
										.addComponent(watermarkJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26)
						.addComponent(line, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(descJlabel).addGap(18, 18, 18).addComponent(currentWatermarkDescJLabel).addContainerGap(47, Short.MAX_VALUE)));
	}

	private void horizontalGroupLayout(GroupLayout layout) {
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(21, 21, 21)
								.addGroup(
										layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(currentWatermarkDescJLabel)
												.addComponent(descJlabel)
												.addGroup(
														layout.createSequentialGroup().addComponent(currentWatermarkJLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(watermarkJComboBox, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addComponent(line, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));
	}
}
