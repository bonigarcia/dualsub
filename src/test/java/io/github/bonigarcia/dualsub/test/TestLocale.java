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
package io.github.bonigarcia.dualsub.test;

import io.github.bonigarcia.dualsub.util.I18N;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestLocale.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class TestLocale {

	private static final Logger log = LoggerFactory.getLogger(TestLocale.class);

	@Test
	public void testLocale() throws IOException {
		Enumeration<URL> classpath = Thread.currentThread()
				.getContextClassLoader().getResources("");
		File folder;
		String[] ls;
		String localeStr;
		while (classpath.hasMoreElements()) {
			folder = new File(classpath.nextElement().getFile());
			ls = folder.list();
			for (String s : ls) {
				if (s.startsWith(I18N.MESSAGES) && s.endsWith(".properties")
						&& s.contains("_")) {
					localeStr = s.substring(s.indexOf("_") + 1, s.indexOf("."));
					Locale locale = new Locale(localeStr);
					log.info(s + " " + locale);
				}
			}
		}
	}

}
