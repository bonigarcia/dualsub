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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * CaptchaPanel.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.1
 */
public class CaptchaPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	public CaptchaPanel(BufferedImage image) {
		this.image = image;
	}

	@Override
	public Dimension getPreferredSize() {
		return image == null ? super.getPreferredSize() : new Dimension(
				image.getWidth(), image.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			int x = (getWidth() - image.getWidth()) / 2;
			int y = (getHeight() - image.getHeight()) / 2;
			g.drawImage(image, x, y, this);
		}
	}
}
