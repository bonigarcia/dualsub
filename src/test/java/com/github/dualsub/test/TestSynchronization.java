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

/**
 * TestSynchronization.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
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
