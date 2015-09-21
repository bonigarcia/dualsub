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

import io.github.bonigarcia.dualsub.util.I18N;
import io.github.bonigarcia.dualsub.util.I18N.Html;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WhatsNewDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.1.0
 */
public class WhatsNewDialog extends HelpParent {

	private static final Logger log = LoggerFactory
			.getLogger(WhatsNewDialog.class);

	private static final long serialVersionUID = 1L;

	private String id;

	public WhatsNewDialog(DualSub parent, boolean modal, String id) {
		super(parent, modal);
		this.id = id;
		setWidth(600);
		setHeight(518);
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
		panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		panel.setBackground(parent.getBackground());
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scroll);

		// Title
		final String title = I18N.getHtmlText("WhatsNewDialog.title.text");
		setTitle(I18N.getText("Window.name.text"));
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(marginLeft, 11, 535, 25);
		panel.add(lblTitle);

		// Content
		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("WhatsNewDialog.text.01"));
		lblContent01.setBounds(marginLeft, 36, 535, 95);
		panel.add(lblContent01);

		ImageIcon sampleIcon = new ImageIcon(
				ClassLoader.getSystemResource("img/vlc-2.1.15-sample.png"));
		Image image = sampleIcon.getImage();
		Image newimg = image.getScaledInstance(362, 265,
				java.awt.Image.SCALE_SMOOTH);
		sampleIcon = new ImageIcon(newimg);
		JLabel sampleLabel = new JLabel();
		sampleLabel.setIcon(sampleIcon);
		sampleLabel.setBounds(marginLeft + 90, 130, 362, 265);
		panel.add(sampleLabel);

		JLabel lblContent02 = new JLabel(
				I18N.getHtmlText("WhatsNewDialog.text.02"));
		lblContent02.setBounds(marginLeft, 395, 535, 50);
		panel.add(lblContent02);

		String surveyUrl = parent.getProperties().getProperty("surveyUrl")
				+ "viewform?" + parent.getProperties().getProperty("idField")
				+ "=" + id;
		JButton lblContent03 = new UrlButton(I18N.getHtmlText(
				"WhatsNewDialog.text.03", Html.LINK), surveyUrl,
				parent.getCursor(), parent.getBackground(), new Rectangle(
						marginLeft + 180, 444, 200, 15));
		panel.add(lblContent03);

		JLabel lblContent04 = new JLabel(
				I18N.getHtmlText("WhatsNewDialog.text.04"));
		lblContent04.setBounds(marginLeft, 460, 535, 15);
		panel.add(lblContent04);

		// Borders (for debug purposes)
		if (log.isTraceEnabled()) {
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