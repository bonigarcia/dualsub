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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JDialog;

/**
 * HelpParent.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public abstract class HelpParent extends JDialog {

	protected static final long serialVersionUID = 1L;

	private int width = 500;

	private int height = 430;

	protected DualSub parent;

	public HelpParent(DualSub parent, boolean modal) {
		super(parent.getFrame(), modal);
		this.parent = parent;
		initComponents();
	}

	public void setVisible() {
		this.getParent().setFont(new Font("SimSun", Font.BOLD, 20));
		this.setBounds(100, 100, getWidth(), getHeight());
		Point point = parent.getFrame().getLocationOnScreen();
		Dimension size = parent.getFrame().getSize();
		this.setLocation(
				(int) (point.getX() + ((size.getWidth() - getWidth()) / 2)),
				(int) (point.getY() + ((size.getHeight() - getHeight()) / 2)));
		setVisible(true);
	}

	protected abstract void initComponents();

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
