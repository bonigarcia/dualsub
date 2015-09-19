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
import io.github.bonigarcia.dualsub.util.I18N.Html;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelpPlayerDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class HelpPlayerDialog extends HelpParent {

	private static final Logger log = LoggerFactory
			.getLogger(HelpPlayerDialog.class);

	private static final long serialVersionUID = 1L;

	public HelpPlayerDialog(DualSub parent, boolean modal) {
		super(parent, modal);
		parent.setHelpPlayer(this);
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
		panel.setPreferredSize(new Dimension(getWidth(), getHeight() + 140));
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
		lblTitle.setBounds(marginLeft, 21, 435, 25);
		panel.add(lblTitle);

		// Content
		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("HelpPlayerDialog.help.01"));
		lblContent01.setBounds(marginLeft, 50, 435, 120);
		panel.add(lblContent01);

		JButton lblContent02 = new UrlButton(I18N.getHtmlText(
				"HelpPlayerDialog.help.02", Html.LINK), parent.getCursor(),
				parent.getBackground(), new Rectangle(marginLeft + 20, 170,
						240, 20));
		panel.add(lblContent02);

		UrlButton lblContent03 = new UrlButton(I18N.getHtmlText(
				"HelpPlayerDialog.help.03", Html.LINK), parent.getCursor(),
				parent.getBackground(), new Rectangle(marginLeft + 20, 190,
						240, 20));
		panel.add(lblContent03);

		UrlButton lblContent04 = new UrlButton(I18N.getHtmlText(
				"HelpPlayerDialog.help.04", Html.LINK), parent.getCursor(),
				parent.getBackground(), new Rectangle(marginLeft + 20, 210,
						240, 20));
		panel.add(lblContent04);

		JLabel lblContent05 = new JLabel(
				I18N.getHtmlText("HelpPlayerDialog.help.05"));
		lblContent05.setBounds(marginLeft, 230, 435, 180);
		panel.add(lblContent05);

		JLabel lblContent06 = new JLabel(
				I18N.getHtmlText("HelpPlayerDialog.help.06"));
		lblContent06.setBounds(marginLeft, 410, 435, 100);
		panel.add(lblContent06);

		JLabel lblContent07 = new JLabel(
				I18N.getHtmlText("HelpPlayerDialog.help.07"));
		lblContent07.setBounds(marginLeft + 20, 510, 415, 40);
		panel.add(lblContent07);

		// Borders (for debug purposes)
		if (log.isDebugEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorderPainted(true);
			lblContent02.setBorder(border);
			lblContent03.setBorder(border);
			lblContent03.setBorderPainted(true);
			lblContent04.setBorder(border);
			lblContent04.setBorderPainted(true);
			lblContent05.setBorder(border);
			lblContent06.setBorder(border);
			lblContent07.setBorder(border);
			panel.setBorder(border);
		}
	}

}