package com.github.dualsub.test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import org.junit.Test;

import com.github.dualsub.srt.DualSrt;
import com.github.dualsub.srt.Merger;
import com.github.dualsub.srt.Srt;
import com.github.dualsub.srt.SrtUtils;
import com.github.dualsub.translate.Language;
import com.github.dualsub.translate.Translator;
import com.github.dualsub.util.Charset;
import com.github.dualsub.util.Log;

public class TestTranslation {

	@Test
	public void testTranslation() throws IOException {
		Translator translate = Translator.getInstance();

		String english = "Our story starts with the beginning of the universe.";
		String spanish = translate.translate(english, Language.ENGLISH,
				Language.SPANISH, Charset.ISO88591);
		Log.info(english + " -- " + spanish);

		spanish = "Y es parte esencial de la naturaleza humana querer encontrar las respuestas.";
		String italian = translate.translate(spanish, Language.SPANISH,
				Language.ITALIAN, Charset.ISO88591);

		Log.info(spanish + " -- " + italian);
	}

	@Test
	public void testSrtTranslation() throws IOException, ParseException {
		String srtLeftFile = "Game of Thrones 1x01 - Winter Is Coming (English).srt";
		SrtUtils.init("720", "Tahoma", 20, true, true, ".", 50);
		Srt srtLeft = new Srt(srtLeftFile);
		Properties properties = new Properties();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("dualsub.properties");
		properties.load(inputStream);

		boolean merge = false;
		Merger merger = new Merger(".", true, 1000, true, properties,
				Charset.ISO88591, 0, true, merge);
		Srt srtRight = new Srt(srtLeft, Language.ENGLISH, Language.SPANISH,
				srtLeft.getCharset());

		if (!merge) {
			// Just translate
			srtLeft.resetSubtitles();
		}

		String mergedFileName = merger.getMergedFileName(srtLeft, srtRight);
		DualSrt dualSrt = merger.mergeSubs(srtLeft, srtRight);

		dualSrt.writeSrt(mergedFileName, Charset.ISO88591,
				merger.isTranslate(), merger.isMerge());
		Log.info(mergedFileName + " " + Charset.detect(mergedFileName));
		// new File(mergedFileName).delete();
	}

}
