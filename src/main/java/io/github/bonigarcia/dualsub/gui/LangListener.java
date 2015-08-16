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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

/**
 * LangListener.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class LangListener implements ActionListener {

	private String locale;

	private Preferences preferences;

	public LangListener(String locale, Preferences preferences) {
		this.locale = locale;
		this.preferences = preferences;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		preferences.put("locale", locale);
		Alert.info(I18N.getHtmlText("LangListener.changes.alert"));
	}

}
