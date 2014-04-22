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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.markdown4j.Markdown4jProcessor;

import com.github.dualsub.util.Charset;
import com.github.dualsub.util.I18N;
import com.github.dualsub.util.Log;

/**
 * AboutDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private DualSub parent;

	// Scrolls
	private JScrollPane scrollPane;
	private JScrollPane scrollLicense;
	private JScrollPane scrollChangelog;

	public AboutDialog(DualSub parent, boolean modal) {
		super(parent.getFrame(), modal);
		this.parent = parent;
		initComponents();

		Runnable doScroll = new Runnable() {
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(0);
				scrollChangelog.getVerticalScrollBar().setValue(0);
				scrollLicense.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
	}

	public void setVisible() {
		final int width = 500;
		final int height = 430;
		this.setBounds(100, 100, width, height);
		Point point = parent.getFrame().getLocationOnScreen();
		Dimension size = parent.getFrame().getSize();
		this.setLocation(
				(int) (point.getX() + ((size.getWidth() - width) / 2)),
				(int) (point.getY() + ((size.getHeight() - height) / 2)));
		setVisible(true);
	}

	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(I18N.getText("Window.name.text"));

		// Features
		this.setResizable(false);
		getContentPane().setLayout(null);
		this.getContentPane().setBackground(parent.getBackground());

		// Tabs
		JTabbedPane tabs = new JTabbedPane();
		tabs.setBounds(10, 11, 474, 351);
		this.getContentPane().add(tabs);

		// About tab
		JPanel aboutTab = new JPanel();
		aboutTab.setBackground(parent.getBackground());
		tabs.addTab(I18N.getHtmlText("About.about.text"), aboutTab);
		aboutTab.setLayout(null);

		JLabel lblDualSub = new JLabel();
		lblDualSub.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("img/dualsub-about.png")));
		lblDualSub.setBounds(66, 0, 330, 140);
		aboutTab.add(lblDualSub);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 139, 470, 187);
		aboutTab.add(scrollPane);

		JTextPane txtrDualSub = new JTextPane();
		scrollPane.setViewportView(txtrDualSub);
		txtrDualSub.setContentType("text/html");
		addTextContentToArea(txtrDualSub, "README.md", true);
		txtrDualSub.setEditable(false);

		tabs.setVisible(true);

		// Changelog tab
		JPanel changelogTab = new JPanel();
		changelogTab.setBackground(parent.getBackground());
		changelogTab.setLayout(null);
		tabs.addTab(I18N.getHtmlText("About.changelog.text"), null,
				changelogTab, null);

		scrollChangelog = new JScrollPane();
		scrollChangelog.setBounds(0, 0, 470, 327);
		changelogTab.add(scrollChangelog);

		JTextPane txtChangelog = new JTextPane();
		scrollChangelog.setViewportView(txtChangelog);
		addTextContentToArea(txtChangelog, "ChangeLog", false);
		txtChangelog.setEditable(false);

		// License tab
		JPanel licenseTab = new JPanel();
		licenseTab.setBackground(parent.getBackground());
		licenseTab.setLayout(null);
		tabs.addTab(I18N.getHtmlText("About.license.text"), null, licenseTab,
				null);

		scrollLicense = new JScrollPane();
		scrollLicense.setBounds(0, 0, 470, 327);
		licenseTab.add(scrollLicense);

		JTextPane txtLicense = new JTextPane();
		scrollLicense.setViewportView(txtLicense);
		addTextContentToArea(txtLicense, "LICENSE", false);
		txtLicense.setEditable(false);

		JButton btnOk = new JButton(I18N.getHtmlText("About.ok.text"));
		btnOk.setBounds(203, 373, 89, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		this.getContentPane().add(btnOk);
	}

	private void addTextContentToArea(JTextPane textArea, String file,
			boolean isHtml) {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(file);
			if (is == null) {
				is = new FileInputStream(file);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(is,
					Charset.ISO88591));
			String line = in.readLine();
			String allLines = "";
			while (line != null) {
				if (isHtml) {
					line = new Markdown4jProcessor().process(line);
				} else {
					line += "\r\n";
				}
				allLines += line;
				line = in.readLine();
			}
			in.close();

			textArea.setText(allLines);
		} catch (IOException e) {
			Log.error(e);
		}
	}
}