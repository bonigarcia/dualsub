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

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * Translator.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Translator {

	private static final Logger log = LoggerFactory.getLogger(Translator.class);

	private static Translator singleton = null;

	private Preferences preferences;

	private Translate translate;

	public static Translator getInstance() {
		if (singleton == null) {
			singleton = new Translator();
		}
		return singleton;
	}

	public String translate(String text, String languageFrom,
			String languageTo) {
		Translation translation = translate.translate(text,
				TranslateOption.sourceLanguage(languageFrom),
				TranslateOption.targetLanguage(languageTo));

		String translatedText = translation.getTranslatedText();
		log.trace("Translating {} [{}] to [{}] ... result={}", text,
				languageFrom, languageTo, translatedText);
		return translatedText;
	}

	public List<String> translate(List<String> text, String languageFrom,
			String languageTo) {
		List<Translation> translation = translate.translate(text,
				TranslateOption.sourceLanguage(languageFrom),
				TranslateOption.targetLanguage(languageTo));

		List<String> out = new ArrayList<String>();
		for (Translation t : translation) {
			String translatedText = t.getTranslatedText();
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
		String key = preferences.get("googleTranslateKey", "");

		if (key != null && !key.equals("")) {
			translate = TranslateOptions.newBuilder().setApiKey(key).build()
					.getService();
		} else {
			// This should not happen (GUI forces the user to insert a key,
			// otherwise translation is not allowed)
			throw new RuntimeException(
					"Key for Google Translate service not defined");
		}
	}
}
