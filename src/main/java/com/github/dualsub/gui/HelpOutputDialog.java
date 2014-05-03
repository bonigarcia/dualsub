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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.github.dualsub.util.I18N;
import com.github.dualsub.util.I18N.Html;
import com.github.dualsub.util.Log;

/**
 * HelpOutputDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class HelpOutputDialog extends HelpParent {

	private static final long serialVersionUID = 1L;

	public HelpOutputDialog(DualSub parent, boolean modal) {
		super(parent, modal);
		setWidth(600);
	}

	@Override
	protected void initComponents() {
		// Features
		final int marginLeft = 23;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(getWidth(), getHeight() + 100));
		panel.setBackground(parent.getBackground());
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scroll);

		// Title
		final String title = I18N.getHtmlText("PanelOutput.border.text");
		setTitle(I18N.getText("Window.name.text"));
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(marginLeft, 21, 535, 25);
		panel.add(lblTitle);

		// Content
		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("HelpOutputDialog.help.01"));
		lblContent01.setBounds(marginLeft, 50, 535, 230);
		panel.add(lblContent01);

		JLabel lblContent02 = new JLabel(I18N.getHtmlText(
				"HelpOutputDialog.help.02", Html.MONOSPACE));
		lblContent02.setBounds(marginLeft, 280, 535, 120);
		panel.add(lblContent02);

		JLabel lblContent03 = new JLabel(
				I18N.getHtmlText("HelpOutputDialog.help.03"));
		lblContent03.setBounds(marginLeft, 400, 535, 40);
		panel.add(lblContent03);

		JLabel lblContent04 = new JLabel(I18N.getHtmlText(
				"HelpOutputDialog.help.04", Html.MONOSPACE));
		lblContent04.setBounds(marginLeft, 440, 535, 70);
		panel.add(lblContent04);

		// Borders (for debug purposes)
		if (Log.getLevel().equals(Level.FINE)) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorder(border);
			lblContent03.setBorder(border);
			lblContent04.setBorder(border);
			panel.setBorder(border);
		}
	}
}