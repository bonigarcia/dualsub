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
package com.github.dualsub.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * Charset.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Charset {

	public static String ISO88591 = "ISO-8859-1";
	public static String UTF8 = "UTF-8";

	private static Charset singleton = null;

	private UniversalDetector detector;

	public Charset() {
		detector = new UniversalDetector(null);
	}

	public static Charset getSingleton() {
		if (singleton == null) {
			singleton = new Charset();
		}
		return singleton;
	}

	public static String detect(String file) throws IOException {
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(file);
		if (inputStream == null) {
			inputStream = new BufferedInputStream(new FileInputStream(file));
		}
		return Charset.detect(inputStream);
	}

	public static String detect(InputStream inputStream) throws IOException {
		UniversalDetector detector = Charset.getSingleton()
				.getCharsetDetector();
		byte[] buf = new byte[4096];
		int nread;
		while ((nread = inputStream.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		inputStream.close();
		if (encoding == null) {
			// If none encoding is detected, we assume UTF-8
			encoding = UTF8;
		}
		return encoding;
	}

	public UniversalDetector getCharsetDetector() {
		return detector;
	}

}
