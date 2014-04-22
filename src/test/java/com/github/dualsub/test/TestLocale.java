package com.github.dualsub.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;

import org.junit.Test;

import com.github.dualsub.util.I18N;
import com.github.dualsub.util.Log;

public class TestLocale {

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
					Log.info(s + " " + locale);
				}
			}
		}
	}

}
