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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Alert.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Alert {

	private static Alert singleton = null;

	private JFrame frame;

	public static Alert getSingleton() {
		if (singleton == null) {
			singleton = new Alert();
		}
		return singleton;
	}

	public static void error(String message) {
		JOptionPane.showMessageDialog(Alert.getSingleton().getFrame(), message,
				I18N.getText("Window.name.text"), JOptionPane.ERROR_MESSAGE);
	}

	public static void info(String message) {
		JOptionPane.showMessageDialog(Alert.getSingleton().getFrame(), message,
				I18N.getText("Window.name.text"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	public JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		Alert.getSingleton().frame = frame;
	}
}
