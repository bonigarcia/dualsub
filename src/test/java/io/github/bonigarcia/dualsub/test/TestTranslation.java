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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.dualsub.gui.DualSub;
import io.github.bonigarcia.dualsub.srt.DualSrt;
import io.github.bonigarcia.dualsub.srt.Merger;
import io.github.bonigarcia.dualsub.srt.Srt;
import io.github.bonigarcia.dualsub.srt.SrtUtils;
import io.github.bonigarcia.dualsub.translate.Language;
import io.github.bonigarcia.dualsub.translate.Translator;
import io.github.bonigarcia.dualsub.util.Charset;

/**
 * TestTranslation.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class TestTranslation {

	private static final Logger log = LoggerFactory
			.getLogger(TestTranslation.class);

	@Test
	public void testTranslation() {
		Translator translate = Translator.getInstance();

		// Instantiate preferences
		Preferences preferences = Preferences.userNodeForPackage(DualSub.class);
		translate.setPreferences(preferences);

		String english = "Our story starts with the beginning of the universe.";
		String spanish = translate.translate(english, Language.ENGLISH,
				Language.SPANISH);
		log.info(english + " -- " + spanish);

		spanish = "Y es parte esencial de la naturaleza humana querer encontrar las respuestas.";
		String italian = translate.translate(spanish, Language.SPANISH,
				Language.ITALIAN);

		log.info(spanish + " -- " + italian);
	}

	@Test
	public void testSrtTranslation() throws IOException, ParseException {
		String srtLeftFile = "Game of Thrones 1x01 - Winter Is Coming (English)(short).srt";
		SrtUtils.init("720", "Tahoma", 20, true, true, ".", 50, false, null,
				null);
		Srt srtLeft = new Srt(srtLeftFile);
		Properties properties = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("dualsub.properties");
		properties.load(inputStream);

		boolean merge = false;
		Merger merger = new Merger(".", true, 1000, true, properties,
				Charset.ISO88591, 0, true, merge);
		Srt srtRight = new Srt(srtLeft, Language.ENGLISH, Language.SPANISH,
				srtLeft.getCharset(), null);

		if (!merge) {
			// Just translate
			srtLeft.resetSubtitles();
		}

		String mergedFileName = merger.getMergedFileName(srtLeft, srtRight);
		DualSrt dualSrt = merger.mergeSubs(srtLeft, srtRight);

		dualSrt.writeSrt(mergedFileName, Charset.ISO88591, merger.isTranslate(),
				merger.isMerge());
		log.info(mergedFileName + " " + Charset.detect(mergedFileName));
		new File(mergedFileName).delete();
	}

}
