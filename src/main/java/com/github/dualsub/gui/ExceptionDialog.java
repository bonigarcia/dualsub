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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.github.dualsub.util.I18N;

/**
 * ExceptionDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class ExceptionDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private DualSub parent;
	private Throwable exception;

	public ExceptionDialog(DualSub parent, boolean modal, Throwable exception) {
		super(parent.getFrame(), modal);
		this.parent = parent;
		this.exception = exception;
		initComponents();
	}

	public void setVisible() {
		final int width = 500;
		final int height = 430;
		this.setBounds(100, 100, width, height);
		Point point = parent.getFrame().getLocationOnScreen();
		Dimension size = parent.getFrame().getSize();
		this.setLocation(
				(int) (point.getX() + ((size.getWidth() - width) / 2)),
				(int) (point.getY() + ((size.getHeight() - height) / 2)));
		setVisible(true);
	}

	private void initComponents() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(I18N.getText("Window.name.text"));

		// Features
		this.setResizable(false);
		getContentPane().setLayout(null);
		this.getContentPane().setBackground(parent.getBackground());

		JLabel lblError = new JLabel("Error");
		lblError.setFont(new Font("Lucida", Font.BOLD, 20));
		lblError.setBounds(29, 21, 75, 25);
		getContentPane().add(lblError);

		JLabel lblNewLabel = new JLabel("There were errors....");
		lblNewLabel.setBounds(29, 69, 235, 14);
		getContentPane().add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 103, 383, 132);
		getContentPane().add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.append(exception.getClass() + ":" + exception.getMessage()
				+ "\n");
		StackTraceElement[] stack = exception.getStackTrace();
		for (StackTraceElement s : stack) {
			textArea.append("    " + s.toString() + "\n");
		}
	}
}