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
package com.github.dualsub.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * I18N.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class I18N {

	public static final String MESSAGES = "lang/messages";

	public static final String HTML_INIT_TAG = "<html><font face='Lucid'>";

	public static final String HTML_END_TAG = "</font></html>";

	public enum Html {
		LINK, BOLD, MONOSPACE
	}

	private static I18N singleton = null;

	private Locale locale;

	public I18N() {
		this.locale = Locale.getDefault();
	}

	public static I18N getSingleton() {
		if (singleton == null) {
			singleton = new I18N();
		}
		return singleton;
	}

	public static String getHtmlText(String key, Html html) {
		String out = HTML_INIT_TAG;
		switch (html) {
		case LINK:
			out += "<a href='#'>" + getText(key) + "</a>";
			break;
		case BOLD:
			out += "<b><u>" + getText(key) + "</u></b>";
			break;
		case MONOSPACE:
			out += "<pre>" + getText(key) + "</pre>";
			break;
		default:
			out += getText(key);
			break;
		}
		out += HTML_END_TAG;
		return out;
	}

	public static String getHtmlText(String key) {
		return HTML_INIT_TAG + getText(key) + HTML_END_TAG;
	}

	public static String getText(String key) {
		return ResourceBundle.getBundle(MESSAGES, getLocale()).getString(key);
	}

	public static Locale getLocale() {
		return I18N.getSingleton().locale;
	}

	public static void setLocale(String locale) {
		I18N.getSingleton().locale = new Locale(locale);
	}

	public static void setLocale(Locale locale) {
		I18N.getSingleton().locale = locale;
	}

}
