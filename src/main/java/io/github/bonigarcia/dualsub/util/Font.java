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
package io.github.bonigarcia.dualsub.util;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Font.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Font {

	private List<String> fontList;
	private String[] widthList;
	private static Properties properties;
	private static Font singleton = null;

	public Font() {
		fontList = new ArrayList<String>();
		widthList = properties.getProperty("widths").split(",");

		GraphicsEnvironment e = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		for (String font : e.getAvailableFontFamilyNames()) {
			fontList.add(font);
		}
	}

	public static Font getSingleton() {
		if (singleton == null) {
			singleton = new Font();
		}
		return singleton;
	}

	public static void setProperties(Properties properties) {
		Font.properties = properties;
	}

	public static String[] getFontList() {
		return Font.getSingleton().fontList.toArray(new String[Font
				.getSingleton().fontList.size()]);
	}

	public static String[] getWidthList() {
		return Font.getSingleton().widthList;
	}

}
