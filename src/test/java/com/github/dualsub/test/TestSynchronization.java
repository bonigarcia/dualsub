package com.github.dualsub.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.dualsub.srt.DualSrt;
import com.github.dualsub.srt.Merger;
import com.github.dualsub.srt.Srt;
import com.github.dualsub.srt.SrtUtils;
import com.github.dualsub.util.Charset;
import com.github.dualsub.util.Log;

public class TestSynchronization {

	private Properties properties;

	@Before
	public void setup() throws IOException {
		properties = new Properties();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("dualsub.properties");
		properties.load(inputStream);
	}

	@Test
	public void testDesynchronization() throws ParseException, IOException {
		// Initial configuration
		SrtUtils.init("624", "Tahoma", 17, true, true, ".", 50);

		// Input subtitles
		Srt srtLeft = new Srt("Exceptions (English).srt");
		Srt srtRight = new Srt("Exceptions (Spanish).srt");

		// Merge
		Merger merger = new Merger(".", false, 1000, false, properties,
				Charset.ISO88591, 2, false, true);

		DualSrt dualSrt1 = merger.mergeSubs(srtLeft, srtRight);
		String mergedFileName1 = merger.getMergedFileName(srtLeft, srtRight);
		// dualSrt1.writeSrt(mergedFileName1, Charset.ISO88591,
		// merger.isTranslate(), merger.isMerge());

		DualSrt dualSrt2 = merger.mergeSubs(srtRight, srtLeft);
		String mergedFileName2 = merger.getMergedFileName(srtRight, srtLeft);
		// dualSrt2.writeSrt(mergedFileName2, Charset.ISO88591,
		// merger.isTranslate(), merger.isMerge());

		Assert.assertEquals(dualSrt1.size(), dualSrt2.size());
		Assert.assertEquals(mergedFileName1, mergedFileName2);
	}

	@Test
	public void testDjango() throws ParseException, IOException {
		// Initial configuration
		SrtUtils.init("720", "Tahoma", 17, true, true, ".", 50);

		// Input subtitles
		Srt srtLeft = new Srt("Django Unchained (English).srt");
		Srt srtRight = new Srt("Django Unchained (Spanish).srt");

		// Merge
		Merger merger = new Merger(".", false, 1000, false, properties,
				Charset.ISO88591, 2, false, true);
		DualSrt dualSrt = merger.mergeSubs(srtLeft, srtRight);
		String mergedFileName = merger.getMergedFileName(srtLeft, srtRight);
		dualSrt.writeSrt(mergedFileName, merger.getCharset(),
				merger.isTranslate(), merger.isMerge());
		Log.info(mergedFileName + " " + Charset.detect(mergedFileName));
		new File(mergedFileName).delete();
	}

}
