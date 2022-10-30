/*
 * (C) Copyright 2022 Frank Facundo https://github.com/FrankFacundo
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.prefs.Preferences;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TranslatorFree.
 * 
 * @author Frank Facundo
 * @since 1.0.0
 */
public class TranslatorFree {

	private static final Logger log = LoggerFactory.getLogger(TranslatorFree.class);

	private static TranslatorFree singleton = null;

	private Preferences preferences;

	public static TranslatorFree getInstance() {
		if (singleton == null) {
			singleton = new TranslatorFree();
		}
		return singleton;
	}

	// Translate
	public String translate(String text, String languageFrom, String languageTo) 
	{
		String translatedText = "";
		if (text.substring(text.length() - 1) == ":") {
			text.substring(0, text.length() - 1);
		}

		// Brief mode.
		String brief_param = "-brief";
		// Treat all arguments as one single sentence.
		String join_param = "-join-sentence";
		ProcessBuilder pb = new ProcessBuilder("trans", brief_param, join_param, String.format("%s:%s", languageFrom, languageTo), text);
		try{
			String line = "";
			Process p = pb.start();
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			boolean b = p.waitFor(5, TimeUnit.SECONDS);
			if (!b)
			{
				System.out.println("TIMEOUT");
				p.destroy();
			}
			else {
				while ((line = input.readLine()) != null) {
					translatedText += line /* + "\n" */;
				}
				System.out.println(translatedText);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("IO Exception -> Error in Process Builder (Verify command)");
		} catch (InterruptedException e) {
			//e.printStackTrace();
			System.out.println("Interrupted Exception -> Error in Buffered Reader");
		}

		log.trace("Translating {} [{}] to [{}] ... result={}", text,
				languageFrom, languageTo, translatedText);
		return translatedText;
	}

	public List<String> translate(List<String> text, String languageFrom,
			String languageTo) {

		List<String> out = new ArrayList<String>();
		for (String text_to_translate : text) {
			String translatedText = translate(text_to_translate, languageFrom, languageTo);
			out.add(translatedText);
			log.trace("Translating {} [{}] to [{}] ... result={}", text,
					languageFrom, languageTo, translatedText);
		}
		return out;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
		updateInstance();
	}
	
	private void updateInstance() {
	}
}
