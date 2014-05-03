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
package com.github.dualsub.translate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Properties;

import com.github.dualsub.util.Charset;
import com.github.dualsub.util.Log;

/**
 * TranslateMessages.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class TranslateMessages {

	private final static String MASTER_PROPERTIES = "./src/main/resources/lang/messages.properties";
	private final static String MASTER_CHARSET = Charset.ISO88591;
	public final static String EXCEPTIONS[] = { "HelpOutputDialog.help.02",
			"HelpOutputDialog.help.04", "HelpTimingDialog.help.06" };

	public final static String TRANSLATE_LANG[] = { Language.ARABIC,
			Language.CHINESE, Language.DANISH, Language.GERMAN,
			Language.SPANISH, Language.FRENCH, Language.FINNISH,
			Language.GREEK, Language.HINDI, Language.ICELANDIC,
			Language.ITALIAN, Language.JAPANESE, Language.NORWEGIAN,
			Language.PORTUGUESE, Language.RUSSIAN, Language.SWEDISH };
	public final static String TRANSLATE_CHARSET[] = { "ISO-8859-6", "GB2312",
			"ISO-8859-1", "ISO-8859-1", "ISO-8859-1", "ISO-8859-1",
			"ISO-8859-1", "ISO-8859-7", "UTF-8", "ISO-8859-1", "ISO-8859-1",
			"ISO-2022-jp", "ISO-8859-1", "ISO-8859-1", "ISO-8859-5",
			"ISO-8859-1" };

	public static void main(String[] args) throws IOException {
		Translator translate = Translator.getInstance();
		int j;
		String newProperties, lang, charset;
		FileOutputStream newPropertiesFile = null;

		for (int i = 0; i < TRANSLATE_LANG.length; i++) {
			lang = TRANSLATE_LANG[i];
			charset = TRANSLATE_CHARSET[i];
			Properties properties = new Properties();
			FileInputStream inputStream = new FileInputStream(MASTER_PROPERTIES);
			Reader reader = new InputStreamReader(inputStream, MASTER_CHARSET);
			properties.load(reader);

			j = MASTER_PROPERTIES.lastIndexOf(".");
			newProperties = MASTER_PROPERTIES.substring(0, j) + "_" + lang
					+ MASTER_PROPERTIES.substring(j);
			newPropertiesFile = new FileOutputStream(newProperties);

			Log.info(newProperties);
			for (Object propKey : properties.keySet()) {
				if (!Arrays.asList(EXCEPTIONS).contains((String) propKey)) {
					properties.setProperty((String) propKey, translate
							.translate(
									properties.getProperty((String) propKey),
									Language.ENGLISH, lang, charset));
				}
			}
			properties.store(newPropertiesFile,
					"Translated automatically from messages.properties");
			newPropertiesFile.close();
		}
	}
}