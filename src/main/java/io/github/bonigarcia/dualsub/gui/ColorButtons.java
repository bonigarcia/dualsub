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

import io.github.bonigarcia.dualsub.srt.SrtUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * ColorButtons.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.1.0
 */
public class ColorButtons {

	private Cursor hand;
	private JFrame frame;
	private JList<File> leftSubtitles;
	private JList<File> rightSubtitles;

	public ColorButtons(Cursor hand, JFrame frame, JList<File> leftSubtitles,
			JList<File> rightSubtitles) {
		this.frame = frame;
		this.hand = hand;
		this.leftSubtitles = leftSubtitles;
		this.rightSubtitles = rightSubtitles;

		initialize();
	}

	private void initialize() {
		JButton colorButtonLeft = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/color.png")));
		colorButtonLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(
						((Component) e.getSource()).getParent(), "",
						Color.black);
				if (color != null) {
					SrtUtils.setLeftColor(toHexString(color));
					leftSubtitles.setBackground(color);
				} else {
					SrtUtils.setLeftColor(null);
					leftSubtitles.setBackground(Color.white);
				}
			}
		});
		colorButtonLeft.setBounds(308, 132, 22, 22);
		colorButtonLeft.setCursor(hand);
		frame.getContentPane().add(colorButtonLeft);

		JButton colorButtonRight = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/color.png")));
		colorButtonRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(
						((Component) e.getSource()).getParent(), "",
						Color.black);
				if (color != null) {
					SrtUtils.setRightColor(toHexString(color));
					rightSubtitles.setBackground(color);
				} else {
					SrtUtils.setRightColor(null);
					rightSubtitles.setBackground(Color.white);
				}
			}
		});
		colorButtonRight.setBounds(381, 132, 22, 22);
		colorButtonRight.setCursor(hand);
		frame.getContentPane().add(colorButtonRight);

	}

	public String toHexString(Color c) {
		StringBuilder sb = new StringBuilder("#");

		if (c.getRed() < 16) {
			sb.append('0');
		}
		sb.append(Integer.toHexString(c.getRed()));

		if (c.getGreen() < 16) {
			sb.append('0');
		}
		sb.append(Integer.toHexString(c.getGreen()));

		if (c.getBlue() < 16) {
			sb.append('0');
		}
		sb.append(Integer.toHexString(c.getBlue()));

		return sb.toString();
	}

}
