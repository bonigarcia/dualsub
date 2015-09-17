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

import io.github.bonigarcia.dualsub.srt.DualSrt;
import io.github.bonigarcia.dualsub.srt.Merger;
import io.github.bonigarcia.dualsub.srt.Srt;
import io.github.bonigarcia.dualsub.srt.SrtUtils;
import io.github.bonigarcia.dualsub.util.Charset;
import io.github.bonigarcia.dualsub.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * TestSrt.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class TestSrt {

	private Srt srtEn;
	private Srt srtEs;
	private Merger merger;

	@Before
	public void setup() throws IOException {
		String srtEnFile = "Game of Thrones 1x01 - Winter Is Coming (English).srt";
		String srtEsFile = "Game of Thrones 1x01 - Winter Is Coming (Spanish).srt";

		SrtUtils.init("624", "Tahoma", 17, false, false, ".", 50, false, null,
				null);
		srtEn = new Srt(srtEnFile);
		srtEs = new Srt(srtEsFile);
		Log.info(srtEn.getFileName() + " " + Charset.detect(srtEnFile));
		Log.info(srtEs.getFileName() + " " + Charset.detect(srtEsFile));
		Properties properties = new Properties();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("dualsub.properties");
		properties.load(inputStream);
		merger = new Merger(".", true, 1000, true, properties,
				Charset.ISO88591, 0, false, true);
	}

	@Ignore
	@Test
	public void testReadSrt() throws IOException {
		Log.info("srtEnFile size=" + srtEn.getSubtitles().size());
		Log.info("srtEsFile size=" + srtEs.getSubtitles().size());

		Assert.assertEquals(srtEn.getSubtitles().size(), srtEs.getSubtitles()
				.size());
	}

	@Ignore
	@Test
	public void testTime() throws ParseException {
		String line1 = "01:27:40,480 --> 01:26:56,260";
		String line2 = "01:27:40 --> 01:27:49,500";

		Date init1 = SrtUtils.getInitTime(line1);
		Date init2 = SrtUtils.getInitTime(line2);

		Date end1 = SrtUtils.getEndTime(line1);
		Date end2 = SrtUtils.getEndTime(line2);

		Log.info(init1 + " " + init1.getTime());
		Log.info(init2 + " " + init2.getTime());
		Log.info("---");
		Log.info(end1 + " " + end1.getTime());
		Log.info(end2 + " " + end2.getTime());

		Assert.assertTrue(init1.getTime() > init2.getTime());
	}

	@Ignore
	@Test
	public void testMergedFileName() {
		String mergedFileName = merger.getMergedFileName(srtEs, srtEn);
		Log.info(mergedFileName);
		Assert.assertEquals("." + File.separator
				+ "Game of Thrones 1x01 - Winter Is Coming.srt", mergedFileName);
	}

	@Ignore
	@Test
	public void testCompleteSrtISO88591() throws ParseException, IOException {
		DualSrt dualSrt = merger.mergeSubs(srtEs, srtEn);
		String mergedFileName = merger.getMergedFileName(srtEs, srtEn);
		dualSrt.writeSrt(mergedFileName, Charset.ISO88591, false, true);
		Log.info(mergedFileName + " " + Charset.detect(mergedFileName));
		new File(mergedFileName).delete();
	}

	@Ignore
	@Test
	public void testCompleteSrtUTF8() throws ParseException, IOException {
		DualSrt dualSrt = merger.mergeSubs(srtEs, srtEn);
		String mergedFileName = merger.getMergedFileName(srtEs, srtEn);
		dualSrt.writeSrt(mergedFileName, Charset.UTF8, false, true);
		Log.info(mergedFileName + " " + Charset.detect(mergedFileName));
		new File(mergedFileName).delete();
	}

	@Test
	public void testCompleteSrtUTF8Horizontal() throws ParseException,
			IOException {
		SrtUtils.init("624", "Tahoma", 17, false, false, ".", 50, true,
				"green", "yellow");

		DualSrt dualSrt = merger.mergeSubs(srtEs, srtEn);
		String mergedFileName = merger.getMergedFileName(srtEs, srtEn);
		dualSrt.writeSrt(mergedFileName, Charset.UTF8, false, true);
		Log.info(mergedFileName + " " + Charset.detect(mergedFileName));
		// new File(mergedFileName).delete();
	}

}
