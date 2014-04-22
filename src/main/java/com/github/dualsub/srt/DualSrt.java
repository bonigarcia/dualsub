/*
 * (C) Copyright 2014 Boni Garcia (http://about.me/boni.garcia)
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
package com.github.dualsub.srt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.github.dualsub.util.I18N;
import com.github.dualsub.util.Log;

/**
 * DualSrt.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class DualSrt {

	private TreeMap<String, Entry[]> subtitles;
	private int signatureGap;
	private int signatureTime;
	private int gap;
	private int desync;
	private int extension;
	private boolean extend;
	private boolean progressive;

	public DualSrt(Properties properties, int desync, boolean extend,
			int extension, boolean progressive) {
		this.extend = extend;
		this.extension = extension;
		this.progressive = progressive;

		this.subtitles = new TreeMap<String, Entry[]>();

		this.signatureGap = Integer.parseInt(properties
				.getProperty("signatureGap")); // seconds
		this.signatureTime = Integer.parseInt(properties
				.getProperty("signatureTime")); // seconds
		this.gap = Integer.parseInt(properties.getProperty("gap")); // milliseconds
		this.desync = desync;
	}

	public void addEntry(String time, Entry[] entries) {
		this.subtitles.put(time, entries);
	}

	public void addAll(Map<String, Entry> allSubs) {
		for (String t : allSubs.keySet()) {
			addEntry(t, new Entry[] { allSubs.get(t) });
		}
	}

	public void log() {
		String left = null, right = null, line = "";
		for (String time : subtitles.keySet()) {
			for (int i = 0; i < Math.max(subtitles.get(time)[0].size(),
					subtitles.get(time)[1].size()); i++) {
				left = i < subtitles.get(time)[0].size() ? subtitles.get(time)[0]
						.get(i) : SrtUtils.getBlankLine();
				right = i < subtitles.get(time)[1].size() ? subtitles.get(time)[1]
						.get(i) : SrtUtils.getBlankLine();
				line = left + right;
				Log.info(time + " " + line + " " + SrtUtils.getWidth(line));
			}
			Log.info("");
		}
	}

	private void addPadding() {
		Map<String, Entry[]> output = new TreeMap<String, Entry[]>();
		Entry[] newEntry;

		for (String t : subtitles.keySet()) {
			newEntry = new Entry[2];
			for (int i = 0; i < 2; i++) {
				if (isLong(subtitles.get(t)[i])) {
					newEntry[i] = this.splitEntry(subtitles.get(t)[i]);
				} else {
					newEntry[i] = this.noSplitEntry(subtitles.get(t)[i]);
				}
				output.put(t, newEntry);
			}
		}
		subtitles = new TreeMap<String, Entry[]>(output);
	}

	/**
	 * It checks whether or not an entry (collection of entries) is long (width
	 * upper than half_width)
	 * 
	 * @param entry
	 * @return
	 */
	private boolean isLong(Entry entry) {
		boolean split = false;
		float width;
		for (int i = 0; i < entry.size(); i++) {
			width = SrtUtils.getWidth(entry.get(i));
			if (width > SrtUtils.getHalfWidth()) {
				split = true;
				break;
			}
		}
		return split;
	}

	/**
	 * It converts and entry adding the padding and splitting lines.
	 * 
	 * @param entry
	 * @return
	 */
	private Entry splitEntry(Entry entry) {
		Entry newEntry = new Entry();

		String append = "";
		for (int i = 0; i < entry.size(); i++) {
			append += entry.get(i) + SrtUtils.getSpace();
		}
		append = append.trim();
		String[] words = append.split(SrtUtils.getSpace());

		List<String> ensuredArray = ensureArray(words);

		String newLine = "";
		for (int i = 0; i < ensuredArray.size(); i++) {
			if (SrtUtils.getWidth(newLine + ensuredArray.get(i)) < SrtUtils
					.getHalfWidth()) {
				newLine += ensuredArray.get(i) + SrtUtils.getSpace();
			} else {
				newEntry.add(convertLine(newLine.trim()));
				newLine = ensuredArray.get(i) + SrtUtils.getSpace();
			}
		}
		if (!newLine.isEmpty()) {
			newEntry.add(convertLine(newLine.trim()));
		}

		return newEntry;
	}

	/**
	 * It converts and entry adding the padding but without splitting lines.
	 * 
	 * @param entry
	 * @return
	 */
	private Entry noSplitEntry(Entry entry) {
		Entry newEntry = new Entry(entry);
		for (int i = 0; i < entry.size(); i++) {
			newEntry.set(i, convertLine(entry.get(i)));
		}
		return newEntry;
	}

	/**
	 * Ensures that each word in the arrays in no longer than MAXWIDTH
	 * 
	 * @param words
	 * @return
	 */
	private List<String> ensureArray(String[] words) {
		List<String> ensured = new LinkedList<String>();
		for (String s : words) {
			if (SrtUtils.getWidth(s) <= SrtUtils.getHalfWidth()) {
				ensured.add(s);
			} else {
				// TODO: improve
				int sLength = s.length();
				ensured.add(s.substring(0, sLength / 2));
				ensured.add(s.substring(sLength / 2, sLength / 2));
			}
		}
		return ensured;
	}

	/**
	 * It adds the padding (SEPARATOR + SPACEs) to a single line.
	 * 
	 * @param line
	 * @return
	 */
	private String convertLine(String line) {
		float width = SrtUtils.getWidth(line);
		double diff = ((SrtUtils.getHalfWidth() - width) / SrtUtils
				.getSpaceWidth()) / 2;
		double rest = diff % 1;
		int numSpaces = (int) Math.floor(diff);
		String additional = (rest >= 0.5) ? SrtUtils.getPadding() : "";

		// TODO: Improve
		if (numSpaces < 0) {
			numSpaces = 0;
		}
		String newLine = SrtUtils.getSeparator()
				+ SrtUtils.repeat(SrtUtils.getPadding(), numSpaces + 1)
				+ additional + line
				+ SrtUtils.repeat(SrtUtils.getPadding(), numSpaces + 1)
				+ SrtUtils.getSeparator();

		return newLine;
	}

	public void processDesync(String time, Entry desyncEntry)
			throws ParseException {
		TreeMap<String, Entry[]> newSubtitles = new TreeMap<String, Entry[]>();

		long initTime = SrtUtils.getInitTime(time).getTime();
		long endTime = SrtUtils.getEndTime(time).getTime();
		long iTime, jTime;
		int top = 0, down = subtitles.keySet().size();
		boolean topOver = false, downOver = false;
		int from, to;

		for (String t : subtitles.keySet()) {
			iTime = SrtUtils.getInitTime(t).getTime();
			jTime = SrtUtils.getEndTime(t).getTime();
			if (iTime <= initTime) {
				top++;
			}
			if (jTime >= endTime) {
				down--;
			}
			if (iTime <= initTime && jTime >= initTime) {
				topOver = true;
			}
			if (iTime <= endTime && jTime >= endTime) {
				downOver = true;
			}
		}
		from = top - 1 + (topOver ? 0 : 1);
		to = down - (downOver ? 0 : 1);

		Log.debug(time + " TOP " + top + " DOWN " + down + " TOPOVER "
				+ topOver + " DOWNOVER " + downOver + " SIZE "
				+ subtitles.size() + " FROM " + from + " TO " + to + " "
				+ desyncEntry.getSubtitleLines());
		String mixedTime = mixTime(initTime, endTime, from, to);
		Log.debug(mixedTime);

		Entry newEntryLeft = new Entry();
		Entry newEntryRight = new Entry();
		for (int i = from; i <= to; i++) {
			newEntryLeft
					.addAll(subtitles.get(subtitles.keySet().toArray()[i])[0]);
			newEntryRight
					.addAll(subtitles.get(subtitles.keySet().toArray()[i])[1]);
		}
		newEntryRight.addAll(desyncEntry);

		if (top != 0) {
			newSubtitles.putAll(subtitles.subMap(subtitles.firstKey(), true,
					(String) subtitles.keySet().toArray()[top - 1], !topOver));
		}
		newSubtitles
				.put(mixedTime, new Entry[] { newEntryLeft, newEntryRight });
		if (down != subtitles.size()) {
			newSubtitles.putAll(subtitles.subMap((String) subtitles.keySet()
					.toArray()[down], !downOver, subtitles.lastKey(), true));
		}

		subtitles = newSubtitles;
	}

	private String mixTime(long initTime, long endTime, int from, int to)
			throws ParseException {

		long initCandidate = initTime;
		long endCandidate = endTime;
		long initFromTime = initTime;
		long endToTime = endTime;

		if (to > 0 && to >= from) {
			if (from < subtitles.size()) {
				initFromTime = SrtUtils.getInitTime(
						(String) subtitles.keySet().toArray()[from]).getTime();
			}
			if (to < subtitles.size()) {
				endToTime = SrtUtils.getEndTime(
						(String) subtitles.keySet().toArray()[to]).getTime();
			}

			switch (getDesync()) {
			case 0:
				// Left
				initCandidate = initFromTime;
				endCandidate = endToTime;
				break;
			case 1:
				// Right
				initCandidate = initTime;
				endCandidate = endTime;
				break;
			case 2:
				// Max
				initCandidate = Math.min(initTime, initFromTime);
				endCandidate = Math.max(endTime, endToTime);
				break;
			case 3:
				// Min
				initCandidate = Math.max(initTime, initFromTime);
				endCandidate = Math.min(endTime, endToTime);
				break;
			}
		}

		return SrtUtils.createSrtTime(new Date(initCandidate), new Date(
				endCandidate));
	}

	/**
	 * It writes a new subtitle file (SRT) from a subtitle map.
	 * 
	 * @param subs
	 * @param fileOuput
	 * @throws IOException
	 * @throws ParseException
	 */
	public void writeSrt(String fileOuput, String charsetStr,
			boolean translate, boolean merge) throws IOException,
			ParseException {
		if (!translate || (translate & merge)) {
			addPadding();
		}
		if (extend) {
			shiftSubs(extension, progressive);
		}

		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				fileOuput));
		FileChannel fileChannel = fileOutputStream.getChannel();

		Charset charset = Charset.forName(charsetStr);
		CharsetEncoder encoder = charset.newEncoder();
		encoder.onMalformedInput(CodingErrorAction.IGNORE);
		encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

		CharBuffer uCharBuffer;
		ByteBuffer byteBuffer;

		String left = "", right = "", time = "";
		for (int j = 0; j < subtitles.keySet().size(); j++) {
			time = (String) subtitles.keySet().toArray()[j];
			byteBuffer = ByteBuffer.wrap((String.valueOf(j + 1) + SrtUtils.EOL)
					.getBytes(Charset.forName(charsetStr)));
			fileChannel.write(byteBuffer);
			byteBuffer = ByteBuffer.wrap((time + SrtUtils.EOL).getBytes(Charset
					.forName(charsetStr)));
			fileChannel.write(byteBuffer);

			int limit = subtitles.get(time).length > 1 ? Math.max(
					subtitles.get(time)[0].size(),
					subtitles.get(time)[1].size()) : subtitles.get(time)[0]
					.size();
			for (int i = 0; i < limit; i++) {
				left = i < subtitles.get(time)[0].size() ? subtitles.get(time)[0]
						.get(i) : SrtUtils.getBlankLine();
				if (subtitles.get(time).length > 1) {
					right = i < subtitles.get(time)[1].size() ? subtitles
							.get(time)[1].get(i) : SrtUtils.getBlankLine();
				}
				uCharBuffer = CharBuffer.wrap(left + right + SrtUtils.EOL);
				byteBuffer = encoder.encode(uCharBuffer);

				Log.debug(new String(byteBuffer.array(), Charset
						.forName(charsetStr)));

				fileChannel.write(byteBuffer);
			}
			byteBuffer = ByteBuffer.wrap(SrtUtils.EOL.getBytes(Charset
					.forName(charsetStr)));
			fileChannel.write(byteBuffer);
		}

		for (String s : signature(translate, merge)) {
			byteBuffer = ByteBuffer.wrap((s + SrtUtils.EOL).getBytes(Charset
					.forName(charsetStr)));
			fileChannel.write(byteBuffer);
		}
		byteBuffer = ByteBuffer.wrap(SrtUtils.EOL.getBytes(Charset
				.forName(charsetStr)));
		fileChannel.write(byteBuffer);
		fileChannel.close();

		fileOutputStream.close();
	}

	/**
	 * Adds an entry in the end of the merged subtitles with the program author.
	 * 
	 * @param subs
	 * @throws ParseException
	 */
	private List<String> signature(boolean translate, boolean merge)
			throws ParseException {
		String lastEntryTime = (String) subtitles.keySet().toArray()[subtitles
				.keySet().size() - 1];
		Date end = SrtUtils.getEndTime(lastEntryTime);
		final Date newDateInit = new Date(end.getTime() + signatureGap);
		final Date newDateEnd = new Date(end.getTime() + signatureTime);
		String newTime = SrtUtils.createSrtTime(newDateInit, newDateEnd);
		List<String> signature = new LinkedList<String>();
		signature.add(String.valueOf(subtitles.size() + 1));
		signature.add(newTime);
		String signatureText = "";
		if (translate & merge) {
			signatureText = I18N.getText("Merger.signatureboth.text");
		} else if (translate & !merge) {
			signatureText = I18N.getText("Merger.signaturetranslated.text");
		} else {
			signatureText = I18N.getText("Merger.signature.text");
		}
		signature.add(signatureText);
		signature.add(I18N.getText("Merger.signature.url"));
		return signature;
	}

	/**
	 * This method extends the duration of each subtitle 1 second (EXTENSION).
	 * If the following subtitle is located inside that extension, the extension
	 * will be only until the beginning of this next subtitle minus 20
	 * milliseconds (GAP).
	 * 
	 * @throws ParseException
	 */
	public void shiftSubs(int extension, boolean progressive)
			throws ParseException {
		TreeMap<String, Entry[]> newSubtitles = new TreeMap<String, Entry[]>();

		String timeBefore = "";
		Date init;
		Date tsBeforeInit = new Date();
		Date tsBeforeEnd = new Date();
		String newTime = "";
		Entry[] entries;
		int shiftTime = extension;

		for (String t : subtitles.keySet()) {
			if (!timeBefore.isEmpty()) {
				init = SrtUtils.getInitTime(t);
				entries = subtitles.get(timeBefore);
				if (progressive) {
					shiftTime = entries.length > 1 ? extension
							* Math.max(entries[0].size(), entries[1].size())
							: entries[0].size();
				}
				if (tsBeforeEnd.getTime() + shiftTime < init.getTime()) {
					newTime = SrtUtils.createSrtTime(tsBeforeInit, new Date(
							tsBeforeEnd.getTime() + shiftTime));
					Log.debug("Shift " + timeBefore + " to " + newTime
							+ " ... extension " + shiftTime);
				} else {
					newTime = SrtUtils.createSrtTime(tsBeforeInit, new Date(
							init.getTime() - gap));
					Log.debug("Shift " + timeBefore + " to " + newTime);
				}
				newSubtitles.put(newTime, entries);

			}
			timeBefore = t;
			tsBeforeInit = SrtUtils.getInitTime(timeBefore);
			tsBeforeEnd = SrtUtils.getEndTime(timeBefore);
		}

		// Last entry
		entries = subtitles.get(timeBefore);
		if (entries != null) {
			if (progressive) {
				extension *= entries.length > 1 ? Math.max(entries[0].size(),
						entries[1].size()) : entries[0].size();
			}
			newTime = SrtUtils.createSrtTime(tsBeforeInit,
					new Date(tsBeforeEnd.getTime() + extension));
			newSubtitles.put(newTime, entries);
		}

		subtitles = newSubtitles;
	}

	public int size() {
		return subtitles.size();
	}

	public int getDesync() {
		return desync;
	}

}
