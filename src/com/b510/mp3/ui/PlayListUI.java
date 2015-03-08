package com.b510.mp3.ui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.b510.mp3.common.Common;
import com.b510.mp3.vo.PlayListVO;

/**
 * @author Hongten
 * @created 2014-8-3
 */
public class PlayListUI extends MainUI {
	private static final long serialVersionUID = 1L;

	private JButton addButton;
	private JPanel buttonPanel;
	private JLabel currentPlayLabel;
	private JLabel currentPlaySongNameLabel;
	private JLabel infoTipLable;
	private JButton playButton;
	private JTable playListTable;
	private JButton removeButton;
	private JPanel tablePanel;
	private JScrollPane tableScrollPane;
	private JPanel textPanel;
	private JLabel totalLabel;
	private JLabel totalValueLabel;
	private JScrollBar jscrollBar;

	public static int selectId = Common.ZERO;
	public static int selectId_button3 = Common.ZERO;

	private MainUI mainUI;
	private SingerImageHandlerUI singerImageHandlerUI;

	public PlayListUI(String title) {
		super(title);
		display();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				PlayListUI.this.setVisible(false);
			}
		});
	}

	public void setMainUI(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	public void display() {
		initComponents();
		initSelf();
		jscrollBarOperation();
	}

	private void jscrollBarOperation() {
		int rowCount = playListTable.getRowCount();
		if (rowCount > 0) {
			playListTable.setRowSelectionInterval(selectId, selectId);
			jscrollBar = tableScrollPane.getVerticalScrollBar();
			if (null != jscrollBar) {
				if (selectId > 21) {
					jscrollBar.setValue((selectId - 10) * Common.TABLE_ROW_HEIGHT);
				} else {
					jscrollBar.setValue((selectId) * Common.TABLE_ROW_HEIGHT);
				}
			}
		}
	}

	public void initSelf() {
		this.setVisible(true);
		setSize(353, 590);
		setResizable(false);
		this.setLocation(MainUI.pointX - 353, MainUI.pointY);
	}

	private void initComponents() {
		initElement();
		initButtonPanel();
		initTablePanel();
		initTextPanel();
		pageLayout();
		pack();
	}

	private void initTextPanel() {
		totalLabel.setText(Common.TOTAL);
		currentPlayLabel.setText(Common.CURRENT_PLAY);
		if (null != playList && playList.size() > 0) {
			totalValueLabel.setText(String.valueOf(playList.size()));
		} else {
			totalValueLabel.setText(String.valueOf(Common.ZERO));
		}

		textPanelLayout();
	}

	public void setcurrentPlaySongNameLabel(String songName) {
		if (null != playList && playList.size() > 0 && !isStop) {
			this.currentPlaySongNameLabel.setText(songName);
		} else {
			this.currentPlaySongNameLabel.setText(Common.EMPTY);
		}
	}

	@SuppressWarnings("serial")
	private void initTablePanel() {
		tableScrollPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		tableScrollPane.setVerifyInputWhenFocusTarget(false);

		Object[][] objects = new Object[][] { { null, null, null, null, null, null } };

		if (null != playList && playList.size() > 0) {
			objects = new Object[playList.size()][6];
			for (int i = 0; i < playList.size(); i++) {
				PlayListVO item = playList.get(i);
				objects[i][0] = String.valueOf(i + 1);
				objects[i][1] = item.getName();
				objects[i][2] = String.valueOf(item.getSize());
				objects[i][3] = item.getPath();
				objects[i][4] = item.getImageName();
				objects[i][5] = item.getImagePath();

			}
		}

		playListTable.setModel(new DefaultTableModel(objects, new String[] { "No", "Name", "Size", "Path", "Image Name", "Image Path" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		playListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		playListTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		playListTable.setOpaque(false);
		playListTable.setRowHeight(Common.TABLE_ROW_HEIGHT);
		playListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playListTable.setSurrendersFocusOnKeystroke(true);
		playListTable.getTableHeader().setReorderingAllowed(false);
		playListTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2) {
					selectId = playListTable.getSelectedRow();
					selectId_button3 = playListTable.getSelectedRow();
					if (null != singerImageHandlerUI) {
						singerImageHandlerUI = null;
					}
					singerImageHandlerUI = new SingerImageHandlerUI(Common.SINGER_IMAGE_HANDLER);
					singerImageHandlerUI.setMainUI(mainUI);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					setSelectId();
					doubleClickToplayOperation();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		tableScrollPane.setViewportView(playListTable);

		tablePanelLayout();
	}

	private void playListPlayOrSuspendOperation(String name) {
		if (isStop) {
			//mainUI.playOperation();
			playOperation(name);
		} else {
			mainUI.suspendOperation(true);
		}
	}

	public void playOperation(String name) {
		playButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_5)));
		playButton.setToolTipText(Common.SUSPEND);

		currentPlaySongNameLabel.setText(name);
	}

	public void suspendOperation(String name) {
		playButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_7)));
		playButton.setToolTipText(Common.PLAY);

		currentPlaySongNameLabel.setText(name);
	}

	private void initButtonPanel() {
		playButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_7)));
		playButton.addActionListener(this);
		playButton.setToolTipText(Common.PLAY);

		addButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_16)));
		addButton.addActionListener(this);
		addButton.setToolTipText(Common.ADD);

		removeButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Common.PNG_15)));
		removeButton.addActionListener(this);
		removeButton.setToolTipText(Common.REMOVE);

		infoTipLable.setHorizontalAlignment(SwingConstants.CENTER);
		infoTipLable.setText("Removed!");

		buttonPanelLayout();
	}

	private void initElement() {
		buttonPanel = new JPanel();
		playButton = new JButton();
		addButton = new JButton();
		removeButton = new JButton();
		infoTipLable = new JLabel();
		tablePanel = new JPanel();
		tableScrollPane = new JScrollPane();
		playListTable = new JTable();
		textPanel = new JPanel();
		totalLabel = new JLabel();
		currentPlayLabel = new JLabel();
		currentPlaySongNameLabel = new JLabel();
		totalValueLabel = new JLabel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			playButtonOperation();
		} else if (e.getSource() == addButton) {
			addButtonOperation();
		} else if (e.getSource() == removeButton) {
			removeButtonOperation();
		}
	}

	private void playButtonOperation() {
		mainUI.playOperation();
	}

	public void doubleClickToplayOperation() {
		String name = (String) playListTable.getValueAt(selectId, 1);
		String path = (String) playListTable.getValueAt(selectId, 3);
		String imagePath = (String) playListTable.getValueAt(selectId, 5);

		jscrollBarOperation();
		playListPlayOrSuspendOperation(name);
		mainUI.playOperation(name, path, imagePath);
	}

	private void addButtonOperation() {
		mainUI.openOperation(mainUI);
		generatePlayListXMLOperation();
	}

	private void removeButtonOperation() {
		if (!isPlayListEmpty() && mainUI.getPlayListUI().isVisible()) {
			int id = playListTable.getSelectedRow();
			if (id < 0 || id > playList.size()) {
				// Show this dialog if you do not select anyone record.
				JOptionPane.showInternalMessageDialog(PlayListUI.this, Common.SELECT_SONG_MESSAGE, Common.SELECT_SONG_TITLE, JOptionPane.WARNING_MESSAGE);
			} else {
				String name = (String) playListTable.getValueAt(id, 1);
				int option = JOptionPane.showConfirmDialog(PlayListUI.this, Common.CONFIM_REMOVE_SONG + name + Common.BLANK + Common.QUESTION_MARK, Common.CONFIM_REMOVE, JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					if(id == selectId && !isStop){
						mainUI.stopOperation();
						if(id > 0){
							selectId--;
						}else{
							selectId++;
						}
					}
					playList.remove(id);
					mainUI.refreshPlaylistUIWhenUIVisible();
					generatePlayListXMLOperation();
				}
			}
		}
	}

	private void generatePlayListXMLOperation() {
		mainUI.generatePlayListXML();
	}

	public void setSelectId() {
		selectId = playListTable.getSelectedRow();
	}

	/**
	 * If not necessary, please do not change
	 */
	private void pageLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(textPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(0, 0, 0).addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(0, 0, 0).addComponent(textPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));

	}

	/**
	 * If not necessary, please do not change
	 */
	private void textPanelLayout() {
		GroupLayout textPanelLayout = new GroupLayout(textPanel);
		textPanel.setLayout(textPanelLayout);
		textPanelLayout.setHorizontalGroup(textPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(textPanelLayout.createSequentialGroup().addContainerGap().addGroup(textPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(currentPlayLabel, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE).addComponent(totalLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(textPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(currentPlaySongNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(totalValueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		textPanelLayout.setVerticalGroup(textPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(textPanelLayout.createSequentialGroup().addContainerGap().addGroup(textPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(currentPlayLabel).addComponent(currentPlaySongNameLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(textPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(totalLabel).addComponent(totalValueLabel)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void tablePanelLayout() {
		GroupLayout tablePanelLayout = new GroupLayout(tablePanel);
		tablePanel.setLayout(tablePanelLayout);
		tablePanelLayout.setHorizontalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(tablePanelLayout.createSequentialGroup().addContainerGap().addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE).addContainerGap()));
		tablePanelLayout.setVerticalGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE));
	}

	/**
	 * If not necessary, please do not change
	 */
	private void buttonPanelLayout() {
		GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
		buttonPanel.setLayout(buttonPanelLayout);
		buttonPanelLayout.setHorizontalGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(buttonPanelLayout.createSequentialGroup().addContainerGap().addComponent(addButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(infoTipLable, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(playButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		buttonPanelLayout.setVerticalGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(buttonPanelLayout.createSequentialGroup().addGroup(buttonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addComponent(addButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE).addComponent(playButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)).addGap(20, 20, 20)).addGroup(buttonPanelLayout.createSequentialGroup().addContainerGap().addComponent(infoTipLable, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE).addGap(31, 31, 31)));
	}
}
