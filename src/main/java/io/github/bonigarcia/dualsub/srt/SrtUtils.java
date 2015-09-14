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
package io.github.bonigarcia.dualsub.srt;

import io.github.bonigarcia.dualsub.util.Log;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SrtUtils.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class SrtUtils {

	private static final String SPACE = " ";
	private static final String HARD_SPACE = "\\h";

	public static final String SEP_SRT = " --> ";
	public static final String SRT_EXT = ".srt";
	public static final String TAG_INIT = "<";
	public static final String TAG_END = ">";
	public static final String EOL = "\r\n";

	private Font font;
	private FontMetrics fontMetrics;
	private float maxWidth;
	private float separatorWidth;
	private float spaceWidth;
	private float halfWidth;
	private String blankLine;
	private SimpleDateFormat simpleDateFormat;
	private String padding;
	private String separator;
	private boolean usingSpace;
	private boolean usingSeparator;

	private static SrtUtils singleton = null;

	public static SrtUtils getSingleton() {
		if (singleton == null) {
			singleton = new SrtUtils();
		}
		return singleton;
	}

	// Default constructor
	public SrtUtils() {
	}

	@SuppressWarnings("deprecation")
	public static void init(String maxWidth, String fontFamily, int fontSize,
			boolean space, boolean separator, String separatorChar, int guard) {
		Log.debug("maxWidth " + maxWidth + " fontFamily " + fontFamily
				+ " fontSize " + fontSize + " space " + space + " separator "
				+ separator + " separatorChar " + separatorChar + " guard "
				+ guard);
		SrtUtils srtUtils = getSingleton();
		srtUtils.font = new Font(fontFamily, Font.PLAIN, fontSize);
		srtUtils.maxWidth = Float.parseFloat(maxWidth) - guard;
		srtUtils.fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(
				srtUtils.font);
		srtUtils.simpleDateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
		srtUtils.separator = separator ? separatorChar : "";
		srtUtils.padding = space ? SrtUtils.SPACE : SrtUtils.HARD_SPACE;
		srtUtils.usingSpace = space;
		srtUtils.usingSeparator = separator;
		srtUtils.separatorWidth = separator ? getWidth(srtUtils.separator) : 0;

		// Even if hard space is used, the width of the padding is the same
		// as the normal space
		srtUtils.spaceWidth = getWidth(SPACE);

		// Gap of two characters (space + separator)
		srtUtils.halfWidth = (srtUtils.maxWidth / 2) - 2
				* (srtUtils.spaceWidth + srtUtils.separatorWidth);

		// Blank line
		int numSpaces = (int) Math.round(srtUtils.halfWidth / getSpaceWidth());

		if (separator) {
			srtUtils.blankLine = SrtUtils.getSeparator()
					+ repeat(SrtUtils.getPadding(), numSpaces)
					+ SrtUtils.getSeparator();
		} else {
			srtUtils.blankLine = repeat(SrtUtils.getPadding(), numSpaces);
		}
	}

	public static int getWidth(String message) {
		final int width = SrtUtils.getSingleton().fontMetrics
				.stringWidth(message);
		Log.debug("getWidth " + message + " " + width);
		return width;
	}

	/**
	 * 
	 * @param str
	 * @param times
	 * @return
	 */
	public static String repeat(String str, int times) {
		return new String(new char[times]).replace("\0", str);
	}

	/**
	 * It reads the initial time of a subtitle entry
	 * 
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	public static Date getInitTime(String line) throws ParseException {
		Date out = null;
		int i = line.indexOf(SrtUtils.SEP_SRT);
		if (i != -1) {
			String time = line.substring(0, i).trim();
			if (time.length() == 8) {
				// Time without milliseconds (e.g. 01:27:40)
				time += ",000";
			}
			out = SrtUtils.getSingleton().parse(time);
		}
		return out;
	}

	/**
	 * It reads the ending time of a subtitle entry
	 * 
	 * @param line
	 * @return
	 * @throws ParseException
	 */
	public static Date getEndTime(String line) throws ParseException {
		Date out = null;
		int i = line.indexOf(SrtUtils.SEP_SRT);
		if (i != -1) {
			String time = line.substring(i + SrtUtils.SEP_SRT.length());
			if (time.length() == 8) {
				// Time without milliseconds (e.g. 01:27:40)
				time += ",000";
			}
			out = SrtUtils.getSingleton().parse(time);

		}
		return out;
	}

	public static String createSrtTime(Date dateFrom, Date dateTo) {
		return SrtUtils.format(dateFrom) + SrtUtils.SEP_SRT
				+ SrtUtils.format(dateTo);
	}

	public static Font getFont() {
		return SrtUtils.getSingleton().font;
	}

	public static float getMaxWidth() {
		return SrtUtils.getSingleton().maxWidth;
	}

	public static FontMetrics getFontMetrics() {
		return SrtUtils.getSingleton().fontMetrics;
	}

	public static float getSeparatorWidth() {
		return SrtUtils.getSingleton().separatorWidth;
	}

	public static float getSpaceWidth() {
		return SrtUtils.getSingleton().spaceWidth;
	}

	public static float getHalfWidth() {
		return SrtUtils.getSingleton().halfWidth;
	}

	public static String format(Date date) {
		return SrtUtils.getSingleton().simpleDateFormat.format(date);
	}

	public Date parse(String date) throws ParseException {
		Date out = null;
		try {
			out = SrtUtils.getSingleton().simpleDateFormat.parse(date);
		} catch (ParseException e) {
			out = new SimpleDateFormat("HH:mm:ss.SSS").parse(date);
		}
		return out;
	}

	public static String getBlankLine() {
		return SrtUtils.getSingleton().blankLine;
	}

	public static String getSeparator() {
		return SrtUtils.getSingleton().separator;
	}

	public static String getPadding() {
		return SrtUtils.getSingleton().padding;
	}

	public static boolean isUsingSpace() {
		return SrtUtils.getSingleton().usingSpace;
	}

	public static boolean isUsingSeparator() {
		return SrtUtils.getSingleton().usingSeparator;
	}

	public static String getSpace() {
		return SrtUtils.SPACE;
	}

}