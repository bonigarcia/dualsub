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

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * LateralButtons.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class LateralButtons {

	private Cursor hand;
	private JFrame frame;
	private JList<File> subtitles;
	private AddFileListener fileListener;

	public LateralButtons(Cursor hand, JFrame frame, JList<File> subtitles,
			AddFileListener fileListener, int x) {
		this.frame = frame;
		this.subtitles = subtitles;
		this.fileListener = fileListener;

		initialize(x);
	}

	private void initialize(int x) {
		JButton rightUp = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/up.png")));
		rightUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Validator.goUp(subtitles);
			}
		});
		rightUp.setBounds(x, 55, 22, 22);
		rightUp.setCursor(hand);
		frame.getContentPane().add(rightUp);

		JButton rightAdd = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/add.png")));
		rightAdd.addActionListener(fileListener);
		rightAdd.setBounds(x, 79, 22, 22);
		rightAdd.setCursor(hand);
		frame.getContentPane().add(rightAdd);

		JButton rightDel = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/remove.png")));
		rightDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Validator.deleteSelected(subtitles);
			}
		});
		rightDel.setBounds(x, 103, 22, 22);
		rightDel.setCursor(hand);
		frame.getContentPane().add(rightDel);

		JButton rightDown = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/down.png")));
		rightDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Validator.goDown(subtitles);
			}
		});
		rightDown.setBounds(x, 127, 22, 22);
		rightDown.setCursor(hand);
		frame.getContentPane().add(rightDown);
	}

}
