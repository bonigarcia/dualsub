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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.github.dualsub.srt.Srt;

/**
 * UrlButton.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class UrlButton extends JButton {

	private static final long serialVersionUID = 1L;

	public UrlButton(String htmlText, Cursor cursor, Color background,
			Rectangle bounds) {
		this.setText(htmlText);
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setOpaque(false);
		this.addActionListener(new UrlOpener(Srt.removeTags(htmlText)));
		this.setCursor(cursor);
		this.setBackground(background);
		this.setBounds(bounds);
	}
}
