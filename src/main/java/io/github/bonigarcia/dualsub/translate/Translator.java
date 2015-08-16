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
package io.github.bonigarcia.dualsub.translate;

import io.github.bonigarcia.dualsub.util.Charset;
import io.github.bonigarcia.dualsub.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.Gson;

/**
 * Translator.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Translator {

	public static final String GOOGLE_TRANSLATOR_URL = "http://translate.google.com/translate_a/t?";

	private static Translator singleton = null;

	private static Gson gson;

	public static Translator getInstance() {
		if (singleton == null) {
			singleton = new Translator();
			gson = new Gson();
		}
		return singleton;
	}

	public String translate(String text, String languageFrom,
			String languageTo, String charset) throws IOException {
		StringBuilder stringBuilder = new StringBuilder(GOOGLE_TRANSLATOR_URL);
		text = URLEncoder.encode(text, charset);
		stringBuilder.append("client=p&text=" + text);
		stringBuilder.append("&sl=" + languageFrom);
		stringBuilder.append("&tl=" + languageTo);

		String result = connect(stringBuilder.toString());
		Translation translation = gson.fromJson(result, Translation.class);

		String trans = "";
		for (Sentences s : translation.getSentences()) {
			trans += s.getTrans();
		}
		Log.debug(charset + " ** " + stringBuilder.toString() + " ---- "
				+ trans);

		return trans;
	}

	public static String connect(String urlStr) throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlStr);
		URLConnection urlConnection = url.openConnection();
		urlConnection.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		Reader reader = new InputStreamReader(urlConnection.getInputStream(),
				Charset.UTF8);
		BufferedReader bufferedReader = new BufferedReader(reader);

		int read;
		while ((read = bufferedReader.read()) != -1) {
			result.append((char) read);
		}

		return result.toString();
	}

}