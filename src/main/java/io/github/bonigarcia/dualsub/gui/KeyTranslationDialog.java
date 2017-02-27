/*
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)
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
package io.github.bonigarcia.dualsub.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.dualsub.util.I18N;
import io.github.bonigarcia.dualsub.util.I18N.Html;

/**
 * HelpTranslationDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.2.0
 */
public class KeyTranslationDialog extends HelpParent {

	private static final Logger log = LoggerFactory
			.getLogger(KeyTranslationDialog.class);

	private static final long serialVersionUID = 1L;

	private String key;
	private JTextField keyValue;

	public KeyTranslationDialog(DualSub parent, boolean modal, String key) {
		super(parent, modal);
		this.key = key;
		setHeight(280);
		parent.setKeyTranslationDialog(this);
	}

	@Override
	protected void initComponents() {
		// Features
		final int marginLeft = 30;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		panel.setBackground(parent.getBackground());
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scroll);

		// Title
		final String title = I18N.getHtmlText("KeyTranslation.border.text");
		setTitle(I18N.getText("Window.name.text"));
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(marginLeft, 21, 435, 25);
		panel.add(lblTitle);

		// Content
		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("KeyTranslation.help.01"));
		lblContent01.setBounds(marginLeft, 50, 435, 80);
		panel.add(lblContent01);

		JButton lblContent02 = new UrlButton(
				I18N.getHtmlText("KeyTranslation.help.02", Html.LINK),
				I18N.getHtmlText("KeyTranslation.help.03", Html.LINK),
				parent.getCursor(), parent.getBackground(),
				new Rectangle(marginLeft + 20, 140, 240, 20));
		panel.add(lblContent02);

		JLabel lblContent03 = new JLabel(
				I18N.getHtmlText("KeyTranslation.help.04"));
		lblContent03.setBounds(marginLeft, 170, 435, 30);
		panel.add(lblContent03);

		keyValue = new JTextField();
		keyValue.setBounds(marginLeft, 200, 435, 20);
		keyValue.setColumns(5);
		keyValue.setText(key);
		panel.add(keyValue);

		// Borders (for debug purposes)
		if (log.isTraceEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorder(border);
			lblContent03.setBorder(border);
			keyValue.setBorder(border);
			panel.setBorder(border);
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				String key = keyValue.getText();
				parent.getPreferences().put("googleTranslateKey", key);
				parent.getPanelTranslation().updateLang();
			}
		});
	}

	public void setKey(String key) {
		this.key = key;
		this.keyValue.setText(key);
	}

}