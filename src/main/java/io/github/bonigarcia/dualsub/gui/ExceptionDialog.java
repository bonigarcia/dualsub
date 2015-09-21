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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExceptionDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class ExceptionDialog extends JDialog {

	private static final Logger log = LoggerFactory
			.getLogger(ExceptionDialog.class);

	private static final long serialVersionUID = 1L;

	private DualSub parent;
	private Throwable exception;

	public ExceptionDialog(DualSub parent, boolean modal, Throwable exception) {
		super(parent.getFrame(), modal);
		this.parent = parent;
		this.exception = exception;
		initComponents();
		parent.setException(this);
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

		JLabel lblTitle = new JLabel(I18N.getText("ExceptionDialog.text.01"));
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(29, 21, 435, 25);
		getContentPane().add(lblTitle);

		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("ExceptionDialog.text.02"));
		lblContent01.setBounds(29, 69, 435, 50);
		getContentPane().add(lblContent01);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 130, 435, 150);
		getContentPane().add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.append(exception.getClass() + ":" + exception.getMessage()
				+ "\n");
		StackTraceElement[] stack = exception.getStackTrace();
		for (StackTraceElement s : stack) {
			textArea.append("    " + s.toString() + "\n");
		}

		JLabel lblContent02 = new JLabel(
				I18N.getHtmlText("ExceptionDialog.text.03"));
		lblContent02.setBounds(29, 290, 435, 90);
		getContentPane().add(lblContent02);

		// Borders (for debug purposes)
		if (log.isTraceEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorder(border);
		}
	}
}
