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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.filechooser.FileFilter;

import com.github.dualsub.util.I18N;

/**
 * AddFileListener.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class AddFileListener implements ActionListener {

	private JList<File> list;
	private JFrame frame;

	public AddFileListener(JFrame frame, JList<File> list) {
		this.frame = frame;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".srt")
						|| f.isDirectory();
			}

			public String getDescription() {
				return I18N.getHtmlText("AddFileListener.fileChooser.text");
			}
		});
		fileChooser.showOpenDialog(frame);
		Validator.validateSrt(fileChooser.getSelectedFile(), list);
	}
}
