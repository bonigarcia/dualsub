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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import com.github.dualsub.util.I18N;
import com.github.dualsub.util.I18N.Html;
import com.github.dualsub.util.Log;

/**
 * HelpTimingDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class HelpTimingDialog extends HelpParent {

	private static final long serialVersionUID = 1L;

	public HelpTimingDialog(DualSub parent, boolean modal) {
		super(parent, modal);
		setHeight(800);
		parent.setHelpTiming(this);
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
		panel.setPreferredSize(new Dimension(getWidth(), getHeight() + 1000));
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
				I18N.getHtmlText("HelpTimingDialog.help.01"));
		lblContent01.setBounds(marginLeft, 50, 435, 220);
		panel.add(lblContent01);

		JLabel lblContent02 = new JLabel(I18N.getHtmlText(
				"HelpTimingDialog.help.02", Html.BOLD));
		lblContent02.setBounds(marginLeft, 270, 435, 20);
		panel.add(lblContent02);

		ImageIcon sampleIcon1 = new ImageIcon(
				ClassLoader.getSystemResource("img/cosmos1.gif"));
		JLabel sampleLabel1 = new JLabel();
		sampleLabel1.setIcon(sampleIcon1);
		sampleLabel1.setBounds(marginLeft, 290, 240, 176);
		panel.add(sampleLabel1);

		JLabel lblContent03 = new JLabel(I18N.getHtmlText(
				"HelpTimingDialog.help.03", Html.BOLD));
		lblContent03.setBounds(marginLeft, 466, 435, 20);
		panel.add(lblContent03);

		ImageIcon sampleIcon2 = new ImageIcon(
				ClassLoader.getSystemResource("img/cosmos2.gif"));
		JLabel sampleLabel2 = new JLabel();
		sampleLabel2.setIcon(sampleIcon2);
		sampleLabel2.setBounds(marginLeft, 486, 240, 176);
		panel.add(sampleLabel2);

		JLabel lblContent04 = new JLabel(I18N.getHtmlText(
				"HelpTimingDialog.help.04", Html.BOLD));
		lblContent04.setBounds(marginLeft, 662, 435, 20);
		panel.add(lblContent04);

		ImageIcon sampleIcon3 = new ImageIcon(
				ClassLoader.getSystemResource("img/cosmos3.gif"));
		JLabel sampleLabel3 = new JLabel();
		sampleLabel3.setIcon(sampleIcon3);
		sampleLabel3.setBounds(marginLeft, 682, 240, 176);
		panel.add(sampleLabel3);

		JLabel lblContent05 = new JLabel(
				I18N.getHtmlText("HelpTimingDialog.help.05"));
		lblContent05.setBounds(marginLeft, 858, 435, 60);
		panel.add(lblContent05);

		JLabel lblContent06 = new JLabel(I18N.getHtmlText(
				"HelpTimingDialog.help.06", Html.MONOSPACE));
		lblContent06.setBounds(marginLeft, 918, 435, 130);
		panel.add(lblContent06);

		JLabel lblContent07 = new JLabel(
				I18N.getHtmlText("HelpTimingDialog.help.07"));
		lblContent07.setBounds(marginLeft, 1048, 435, 60);
		panel.add(lblContent07);

		JLabel lblContent08 = new JLabel(
				I18N.getHtmlText("HelpTimingDialog.help.08"));
		lblContent08.setBounds(marginLeft, 1108, 435, 160);
		panel.add(lblContent08);

		JLabel lblContent09 = new JLabel(
				I18N.getHtmlText("HelpTimingDialog.help.09"));
		lblContent09.setBounds(marginLeft, 1268, 435, 150);
		panel.add(lblContent09);

		// Borders (for debug purposes)
		if (Log.getLevel().equals(Level.FINE)) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorder(border);
			lblContent03.setBorder(border);
			lblContent04.setBorder(border);
			lblContent05.setBorder(border);
			lblContent06.setBorder(border);
			lblContent07.setBorder(border);
			lblContent08.setBorder(border);
			lblContent09.setBorder(border);
			panel.setBorder(border);
		}
	}
}