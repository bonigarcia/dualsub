/*
 * (C) Copyright 2015 Boni Garcia (http://bonigarcia.github.io/)
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

import io.github.bonigarcia.dualsub.translate.Translator;
import io.github.bonigarcia.dualsub.util.I18N;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

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

/**
 * CaptchaDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.1
 */
public class CaptchaDialog extends HelpParent {

	private static final Logger log = LoggerFactory
			.getLogger(CaptchaDialog.class);

	private static final long serialVersionUID = 1L;

	private BufferedImage captchaImg;

	public CaptchaDialog(DualSub parent, boolean modal, BufferedImage captchaImg) {
		super(parent, modal);
		this.captchaImg = captchaImg;
		setHeight(250);
		initComponents();
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
		final String title = I18N.getHtmlText("CaptchaDialog.title.text");
		setTitle(I18N.getText("Window.name.text"));
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(marginLeft, 21, 435, 25);
		panel.add(lblTitle);

		// Content
		CaptchaPanel captchaPanel = new CaptchaPanel(captchaImg);
		captchaPanel.setBounds(marginLeft, 55, 202, 72);
		panel.add(captchaPanel);

		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("CaptchaDialog.help.01"));
		lblContent01.setBounds(marginLeft, 140, 435, 20);
		panel.add(lblContent01);

		final JTextField captchaSolution = new JTextField();
		final CaptchaDialog parent = this;
		captchaSolution.setBounds(marginLeft, 160, 202, 28);

		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Translator.getInstance().setCaptchaSolution(
						captchaSolution.getText());
				parent.dispose();
			}
		};
		captchaSolution.addActionListener(buttonListener);
		panel.add(captchaSolution);

		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		JButton resolveButton = new JButton(
				I18N.getHtmlText("CaptchaDialog.button.resolve"));
		resolveButton.setCursor(cursor);
		resolveButton.setBounds(marginLeft + 220, 160, 130, 28);
		resolveButton.addActionListener(buttonListener);
		panel.add(resolveButton);

		JButton cancelButton = new JButton(
				I18N.getHtmlText("CaptchaDialog.button.cancel"));
		cancelButton.setCursor(cursor);
		cancelButton.setBounds(marginLeft + 360, 160, 70, 28);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
				Translator.getInstance().setIndexGoogleAbuse(0);
			}
		});
		panel.add(cancelButton);

		// Focus on text field
		super.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				captchaSolution.requestFocus();
			}
		});

		// Borders (for debug purposes)
		if (log.isDebugEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			captchaPanel.setBorder(border);
			lblContent01.setBorder(border);
			captchaSolution.setBorder(border);
			resolveButton.setBorder(border);
			cancelButton.setBorder(border);
			panel.setBorder(border);
		}
	}
}