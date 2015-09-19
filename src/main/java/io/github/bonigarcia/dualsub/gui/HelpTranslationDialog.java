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
package io.github.bonigarcia.dualsub.gui;

import io.github.bonigarcia.dualsub.util.I18N;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelpTranslationDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class HelpTranslationDialog extends HelpParent {

	private static final Logger log = LoggerFactory
			.getLogger(HelpTranslationDialog.class);

	private static final long serialVersionUID = 1L;

	public HelpTranslationDialog(DualSub parent, boolean modal) {
		super(parent, modal);
		setHeight(330);
		parent.setHelpTranslation(this);
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
		final String title = I18N.getHtmlText("PanelTranslation.border.text");
		setTitle(I18N.getText("Window.name.text"));
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(marginLeft, 21, 435, 25);
		panel.add(lblTitle);

		// Content
		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("HelpTranslationDialog.help.01"));
		lblContent01.setBounds(marginLeft, 50, 435, 80);
		panel.add(lblContent01);

		JLabel lblContent02 = new JLabel(
				I18N.getHtmlText("HelpTranslationDialog.help.02"));
		lblContent02.setBounds(marginLeft, 130, 435, 140);
		panel.add(lblContent02);

		// Borders (for debug purposes)
		if (log.isDebugEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorder(border);
			panel.setBorder(border);
		}
	}
}