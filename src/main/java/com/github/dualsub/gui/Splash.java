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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * Splash.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Splash extends JWindow {

	private static final long serialVersionUID = 1L;

	public Splash(URL pic) {
		JPanel content = (JPanel) getContentPane();
		content.setBackground(Color.white);
		JLabel image = new JLabel(new ImageIcon(pic));
		setSize(image.getPreferredSize());
		JLabel label = new JLabel();
		content.add(image, BorderLayout.CENTER);
		content.add(label, BorderLayout.SOUTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width - getWidth()) / 2,
				(dim.height - getHeight()) / 2);
		setVisible(true);
	}

}