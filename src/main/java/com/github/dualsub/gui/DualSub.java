/*
 * (C) Copyright 2014 Boni Garcia (http://about.me/boni.garcia)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dualsub.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.github.dualsub.util.Charset;
import com.github.dualsub.util.Font;
import com.github.dualsub.util.I18N;
import com.github.dualsub.util.Log;
import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;

/**
 * DualSub.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class DualSub {

	// Preferences and Properties
	private Preferences preferences;
	private Properties properties;

	// UI Elements
	private JFrame frame;
	private JList<File> leftSubtitles;
	private JList<File> rightSubtitles;
	private JTextField outputFolder;
	private Splash splash;
	private Cursor cursor;
	private MergeButtonListener mergeButtonListener;
	private AddFileListener leftFileListener;
	private AddFileListener rightFileListener;
	private AddFolderListener folderListener;
	private Menu menu;
	private JProgressBar progressBar;
	private Color background;

	// Panels (options)
	private PanelTiming panelTiming;
	private PanelPlayer panelPlayer;
	private PanelOutput panelOutput;
	private PanelTranslation panelTranslation;

	// Dialogs
	private ExceptionDialog exception;
	private HelpPlayerDialog helpPlayer;
	private HelpTimingDialog helpTiming;
	private HelpOutputDialog helpOutput;
	private HelpTranslationDialog helpTranslation;
	private HelpSubtitlesDialog helpSubtitles;

	/**
	 * Create the GUI.
	 * 
	 * @throws IOException
	 */
	public DualSub() throws IOException {
		// Look and feel
		try {
			// UIManager.setLookAndFeel(new NimbusLookAndFeel());

			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(false);
			JDialog.setDefaultLookAndFeelDecorated(false);

			// UIManager.setLookAndFeel(UIManager
			// .getCrossPlatformLookAndFeelClassName());
			// JFrame.setDefaultLookAndFeelDecorated(true);
			// JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			Log.warn(e.getMessage());
		}

		splash = new Splash(ClassLoader.getSystemResource("img/splash.png"));
		cursor = new Cursor(Cursor.HAND_CURSOR);

		loadProperties();

		// Default language
		String locale = preferences.get("locale",
				properties.getProperty("locale"));
		if (!locale.isEmpty()) {
			I18N.setLocale(locale);
		}

		initialize();

		menu = new Menu(this, locale);
		menu.addMenu(leftFileListener, rightFileListener, folderListener,
				mergeButtonListener);
		showSplash();
	}

	private void loadProperties() throws IOException {
		// Load properties
		properties = new Properties();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("dualsub.properties");
		Reader reader = new InputStreamReader(inputStream, Charset.ISO88591);
		properties.load(reader);
		Font.setProperties(properties);

		// Instantiate preferences
		preferences = Preferences.userNodeForPackage(DualSub.class);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		background = new Color(240, 240, 240);
		frame.getContentPane().setBackground(background);

		// Alert initialization
		Alert.setFrame(frame);

		// Left subtitles
		JScrollPane leftSubtitlesScroll = new JScrollPane();
		leftSubtitlesScroll.setBounds(46, 37, 260, 121);
		frame.getContentPane().add(leftSubtitlesScroll);
		leftSubtitles = new JList<File>(new DefaultListModel<File>());
		leftSubtitles.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_DELETE) {
					Validator.deleteSelected(leftSubtitles);
				}
			}
		});
		leftSubtitles.setCellRenderer(new FileCellRenderer());
		leftSubtitles
				.setTransferHandler(new ListTransferHandler(leftSubtitles));
		leftSubtitles.setDragEnabled(true);
		leftSubtitles.setDropMode(javax.swing.DropMode.INSERT);
		leftSubtitles.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), I18N
				.getHtmlText("Window.leftSubtitles.borderTitle")));
		leftSubtitlesScroll.setViewportView(leftSubtitles);

		// Right subtitles
		JScrollPane rightSubtitlesScroll = new JScrollPane();
		rightSubtitlesScroll.setBounds(405, 37, 260, 121);
		frame.getContentPane().add(rightSubtitlesScroll);
		rightSubtitles = new JList<File>(new DefaultListModel<File>());
		rightSubtitles.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_DELETE) {
					Validator.deleteSelected(rightSubtitles);
				}
			}
		});
		rightSubtitles.setCellRenderer(new FileCellRenderer());
		rightSubtitles.setTransferHandler(new ListTransferHandler(
				rightSubtitles));
		rightSubtitles.setDragEnabled(true);
		rightSubtitles.setDropMode(javax.swing.DropMode.INSERT);
		rightSubtitles.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), I18N
				.getHtmlText("Window.rightSubtitles.borderTitle")));
		rightSubtitlesScroll.setViewportView(rightSubtitles);

		// Progress bar
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(308, 120, 95, 39);
		Border border = BorderFactory.createTitledBorder(I18N
				.getHtmlText("Window.progress.text"));
		progressBar.setBorder(border);
		progressBar.setBackground(background);
		progressBar.setVisible(false);
		frame.getContentPane().add(progressBar);

		// Output folder
		JButton outputFolderButton = new JButton(
				I18N.getHtmlText("Window.outputFolderButton.text"));
		outputFolderButton.setBounds(46, 180, 117, 29);
		outputFolderButton.setCursor(cursor);
		frame.getContentPane().add(outputFolderButton);
		outputFolder = new JTextField();
		outputFolder.setBounds(165, 181, 500, 28);
		outputFolder.setColumns(10);
		outputFolder.setText(preferences.get("output",
				properties.getProperty("output")));
		frame.getContentPane().add(outputFolder);
		folderListener = new AddFolderListener(frame, outputFolder);
		outputFolderButton.addActionListener(folderListener);

		// Left buttons
		leftFileListener = new AddFileListener(frame, leftSubtitles);
		new LateralButtons(cursor, frame, leftSubtitles, leftFileListener, 16);

		// Right buttons
		rightFileListener = new AddFileListener(frame, rightSubtitles);
		new LateralButtons(cursor, frame, rightSubtitles, rightFileListener,
				673);

		// Timing panel
		panelTiming = new PanelTiming(this);
		frame.getContentPane().add(panelTiming);

		// Player panel
		panelPlayer = new PanelPlayer(this);
		frame.getContentPane().add(panelPlayer);

		// Output panel
		panelOutput = new PanelOutput(this);
		frame.getContentPane().add(panelOutput);

		// Translation panel
		panelTranslation = new PanelTranslation(this);
		frame.getContentPane().add(panelTranslation);

		// Merge Button
		JButton mergeButton = new JButton(
				I18N.getHtmlText("Window.mergeButton.text"));
		mergeButtonListener = new MergeButtonListener(this);
		mergeButton.addActionListener(mergeButtonListener);
		mergeButton.setBounds(308, 80, 95, 29);
		mergeButton.setCursor(cursor);
		frame.getContentPane().add(mergeButton);

		// Help
		JButton buttonHelpSub = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/help.png")));
		buttonHelpSub.setBounds(345, 50, 22, 22);
		buttonHelpSub.setCursor(cursor);
		final DualSub top = this;
		buttonHelpSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpSubtitlesDialog helpSubtitlesDialog = getHelpSubtitles();
				if (helpSubtitlesDialog == null) {
					helpSubtitlesDialog = new HelpSubtitlesDialog(top, true);
				}
				helpSubtitlesDialog.setVisible();
			}
		});
		frame.add(buttonHelpSub);

		// Frame
		frame.setBounds(100, 100, 720, 520);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dim.width - frame.getWidth()) / 2,
				(dim.height - frame.getHeight()) / 2);
		frame.setResizable(false);
		frame.setIconImage(new ImageIcon(ClassLoader
				.getSystemResource("img/dualsub.png")).getImage());
		frame.setTitle(I18N.getText("Window.name.text"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new ExitListener(this));
		frame.getContentPane().setLayout(null);

	}

	private void showSplash() {
		splash.setVisible(false);
		frame.setVisible(true);
		frame.requestFocus();
	}

	public JFrame getFrame() {
		return frame;
	}

	public PanelTiming getPanelTiming() {
		return panelTiming;
	}

	public PanelPlayer getPanelPlayer() {
		return panelPlayer;
	}

	public PanelOutput getPanelOutput() {
		return panelOutput;
	}

	public PanelTranslation getPanelTranslation() {
		return panelTranslation;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public Properties getProperties() {
		return properties;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public Color getBackground() {
		return background;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JList<File> getLeftSubtitles() {
		return leftSubtitles;
	}

	public JList<File> getRightSubtitles() {
		return rightSubtitles;
	}

	public JTextField getOutputFolder() {
		return outputFolder;
	}

	public ExceptionDialog getException() {
		return exception;
	}

	public HelpPlayerDialog getHelpPlayer() {
		return helpPlayer;
	}

	public HelpTimingDialog getHelpTiming() {
		return helpTiming;
	}

	public HelpOutputDialog getHelpOutput() {
		return helpOutput;
	}

	public HelpTranslationDialog getHelpTranslation() {
		return helpTranslation;
	}

	public HelpSubtitlesDialog getHelpSubtitles() {
		return helpSubtitles;
	}

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO: Final handler of exceptions
		new DualSub();
	}
}
