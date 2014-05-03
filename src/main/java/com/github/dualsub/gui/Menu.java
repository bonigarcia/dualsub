/*
 * (C) Copyright 2014 Boni Garcia (http://bonigarcia.github.io/)
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.github.dualsub.util.I18N;

/**
 * Menu.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Menu {

	private final DualSub parent;
	private String locale;

	// Dialogs
	private AboutDialog about;

	public Menu(DualSub parent, String locale) {
		this.parent = parent;
		this.locale = locale;
	}

	public void addMenu(ActionListener... listeners) throws IOException {
		JMenuBar menuBar = new JMenuBar();
		parent.getFrame().setJMenuBar(menuBar);
		JMenu subtitleMenu = new JMenu(I18N.getHtmlText("Menu.actions.text"));
		menuBar.add(subtitleMenu);

		JMenuItem addLeftSubItem = new JMenuItem(
				I18N.getHtmlText("Menu.leftSub.text"));
		addLeftSubItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		addLeftSubItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/left.png")));
		addLeftSubItem.addActionListener(listeners[0]);
		subtitleMenu.add(addLeftSubItem);

		JMenuItem addRightSubItem = new JMenuItem(
				I18N.getHtmlText("Menu.rightSub.text"));
		addRightSubItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		addRightSubItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/right.png")));
		addRightSubItem.addActionListener(listeners[1]);
		subtitleMenu.add(addRightSubItem);

		JMenuItem selectOutputItem = new JMenuItem(
				I18N.getHtmlText("Menu.output.text"));
		selectOutputItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.CTRL_MASK));
		selectOutputItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/folder.png")));
		selectOutputItem.addActionListener(listeners[2]);
		subtitleMenu.add(selectOutputItem);

		JMenuItem mergeSubsItem = new JMenuItem(
				I18N.getHtmlText("Menu.merge.text"));
		mergeSubsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		mergeSubsItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/join.png")));
		mergeSubsItem.addActionListener(listeners[3]);
		subtitleMenu.add(mergeSubsItem);

		subtitleMenu.addSeparator();

		JMenuItem exitItem = new JMenuItem(I18N.getHtmlText("Menu.exit.text"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		exitItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/exit.png")));
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.getFrame()
						.getToolkit()
						.getSystemEventQueue()
						.postEvent(
								new WindowEvent(parent.getFrame(),
										WindowEvent.WINDOW_CLOSING));
			}
		});
		subtitleMenu.add(exitItem);

		// Language
		JMenu langMenu = new JMenu(I18N.getHtmlText("Menu.lang.text"));
		menuBar.add(langMenu);
		addLanguages(langMenu);

		// Help
		JMenu helpMenu = new JMenu(I18N.getHtmlText("Menu.help.text"));
		menuBar.add(helpMenu);

		JMenuItem helpSubItem = new JMenuItem(
				I18N.getHtmlText("Menu.helpSub.text"));
		helpSubItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		helpSubItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/movie.png")));
		helpSubItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpSubtitlesDialog helpSubtitlesDialog = parent
						.getHelpSubtitles();
				if (helpSubtitlesDialog == null) {
					helpSubtitlesDialog = new HelpSubtitlesDialog(parent, true);
				}
				helpSubtitlesDialog.setVisible();
			}
		});
		helpMenu.add(helpSubItem);

		JMenuItem helpPlayerItem = new JMenuItem(
				I18N.getHtmlText("PanelPlayer.border.text"));
		helpPlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		helpPlayerItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/player.png")));
		helpPlayerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpPlayerDialog helpPlayerDialog = parent.getHelpPlayer();
				if (helpPlayerDialog == null) {
					helpPlayerDialog = new HelpPlayerDialog(parent, true);
				}
				helpPlayerDialog.setVisible();
			}
		});
		helpMenu.add(helpPlayerItem);

		JMenuItem helpTimingItem = new JMenuItem(
				I18N.getHtmlText("PanelTiming.border.text"));
		helpTimingItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.CTRL_MASK));
		helpTimingItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/delay.png")));
		helpTimingItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpTimingDialog helpTimingDialog = parent.getHelpTiming();
				if (helpTimingDialog == null) {
					helpTimingDialog = new HelpTimingDialog(parent, true);
				}
				helpTimingDialog.setVisible();
			}
		});
		helpMenu.add(helpTimingItem);

		JMenuItem helpOutputItem = new JMenuItem(
				I18N.getHtmlText("PanelOutput.border.text"));
		helpOutputItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		helpOutputItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/font.png")));
		helpOutputItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpOutputDialog helpOutputDialog = parent.getHelpOutput();
				if (helpOutputDialog == null) {
					helpOutputDialog = new HelpOutputDialog(parent, true);
				}
				helpOutputDialog.setVisible();
			}
		});
		helpMenu.add(helpOutputItem);

		JMenuItem helpTranslationItem = new JMenuItem(
				I18N.getHtmlText("PanelTranslation.border.text"));
		helpTranslationItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		helpTranslationItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/international.png")));
		helpTranslationItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpTranslationDialog helpTranslationDialog = parent
						.getHelpTranslation();
				if (helpTranslationDialog == null) {
					helpTranslationDialog = new HelpTranslationDialog(parent,
							true);
				}
				helpTranslationDialog.setVisible();
			}
		});
		helpMenu.add(helpTranslationItem);

		JMenuItem aboutItem = new JMenuItem(I18N.getHtmlText("Menu.about.text"));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		aboutItem.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/about.png")));
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (about == null) {
					about = new AboutDialog(parent, true);
				}
				about.setVisible();
			}
		});
		helpMenu.add(aboutItem);
	}

	private void addLanguages(JMenu menuLang) throws IOException {
		String localeStr = I18N.getHtmlText("Menu.lang.def");
		JRadioButtonMenuItem defaultLang = new JRadioButtonMenuItem(localeStr);
		int i = KeyEvent.VK_0;
		defaultLang.setAccelerator(KeyStroke.getKeyStroke(i,
				ActionEvent.ALT_MASK));
		defaultLang.addActionListener(new LangListener("", parent
				.getPreferences()));
		if (locale.isEmpty()) {
			defaultLang.setSelected(true);
		}
		menuLang.add(defaultLang);

		ButtonGroup groupLanguge = new ButtonGroup();
		groupLanguge.add(defaultLang);

		Enumeration<URL> classpath = ClassLoader.getSystemResources("./lang");
		File folder;
		String[] ls;

		if (classpath.hasMoreElements()) {
			// Run from source code
			while (classpath.hasMoreElements()) {
				folder = new File(classpath.nextElement().getFile());
				ls = folder.list();
				for (String s : ls) {
					if (checkLanguageFie("lang/" + s)) {
						i++;
						if (i == 58) {
							i = 65; // From the 10th language, Alt-A and so on
						}
						JRadioButtonMenuItem otherLang = createNewLang(s, i);
						menuLang.add(otherLang);
						groupLanguge.add(otherLang);
					}
				}
			}
		} else {
			// Run from Jar
			CodeSource src = this.getClass().getProtectionDomain()
					.getCodeSource();
			if (src != null) {
				URL jar = src.getLocation();
				ZipInputStream zip = new ZipInputStream(jar.openStream());
				while (true) {
					ZipEntry e = zip.getNextEntry();
					if (e == null) {
						break;
					}
					String name = e.getName();
					if (checkLanguageFie(name)) {
						i++;
						if (i == 58) {
							i = 65; // From the 10th language, Alt-A and so on
						}
						JRadioButtonMenuItem otherLang = createNewLang(name, i);
						menuLang.add(otherLang);
						groupLanguge.add(otherLang);
					}
				}
			}
		}
	}

	private boolean checkLanguageFie(String s) {
		boolean out = s.startsWith(I18N.MESSAGES) && s.endsWith(".properties")
				&& s.contains("_");
		return out;
	}

	private JRadioButtonMenuItem createNewLang(String s, int i) {
		JRadioButtonMenuItem otherLang;
		String localeStr = s.substring(s.indexOf("_") + 1, s.indexOf("."));
		otherLang = new JRadioButtonMenuItem(I18N.getHtmlText("Menu.lang."
				+ localeStr));
		otherLang.setAccelerator(KeyStroke
				.getKeyStroke(i, ActionEvent.ALT_MASK));
		otherLang.setIcon((Icon) new ImageIcon(ClassLoader
				.getSystemResource("img/" + localeStr + ".png")));
		otherLang.addActionListener(new LangListener(localeStr, parent
				.getPreferences()));
		if (localeStr.equals(locale)) {
			otherLang.setSelected(true);
		}
		return otherLang;
	}

}
