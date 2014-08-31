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
package com.github.dualsub.srt;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.github.dualsub.util.I18N;
import com.github.dualsub.util.Log;

/**
 * Merger.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Merger {

	private String outputFolder;
	private int extension;
	private boolean extend;
	private boolean progressive;
	private Properties properties;
	private String charset;
	private int desynch;
	private boolean translate;
	private boolean merge;

	public Merger(String outputFolder, boolean extend, int extension,
			boolean progressive, Properties properties, String charset,
			int desynch, boolean translate, boolean merge) {
		this.outputFolder = outputFolder;
		this.extend = extend;
		// Extension should be in ms, and GUI asks for it in seconds
		this.extension = 1000 * extension;
		this.progressive = progressive;
		this.properties = properties;
		this.charset = charset;
		this.desynch = desynch;
		this.translate = translate;
		this.merge = merge;
	}

	public DualSrt mergeSubs(Srt srtLeft, Srt srtRigth) throws ParseException {
		DualSrt dualSrt = new DualSrt(properties, getDesynch(), extend,
				extension, progressive);

		Map<String, Entry> subtitlesLeft = new TreeMap<String, Entry>(
				srtLeft.getSubtitles());
		Map<String, Entry> subtitlesRight = new TreeMap<String, Entry>(
				srtRigth.getSubtitles());

		if (subtitlesLeft.isEmpty() || subtitlesRight.isEmpty()) {
			if (subtitlesLeft.isEmpty()) {
				dualSrt.addAll(subtitlesRight);
			} else if (subtitlesRight.isEmpty()) {
				dualSrt.addAll(subtitlesLeft);
			}
		} else {
			for (String t : subtitlesLeft.keySet()) {
				if (subtitlesRight.containsKey(t)) {
					dualSrt.addEntry(t, new Entry[] { subtitlesLeft.get(t),
							subtitlesRight.get(t) });
					subtitlesRight.remove(t);
				} else {
					dualSrt.addEntry(t, new Entry[] { subtitlesLeft.get(t),
							new Entry() });
				}
			}

			if (!subtitlesRight.isEmpty()) {
				for (String t : subtitlesRight.keySet()) {
					Log.debug("Desynchronization on " + t + " "
							+ subtitlesRight.get(t).subtitleLines);
					dualSrt.processDesync(t, subtitlesRight.get(t));
				}
			}
		}

		return dualSrt;
	}

	public String getMergedFileName(Srt subs1, Srt subs2) {
		String mergedFileName = "";
		if (translate) {
			Srt srt = subs1.getFileName() == null ? subs2 : subs1;
			File file = new File(srt.getFileName());
			String fileName = file.getName();
			int i = fileName.lastIndexOf('.');
			i = i == -1 ? fileName.length() - 1 : i;
			String extension = fileName.substring(i);
			mergedFileName = getOutputFolder() + File.separator
					+ fileName.substring(0, i);
			if (merge) {
				mergedFileName += " " + I18N.getText("Merger.merged.text");
			} else {
				mergedFileName += " " + I18N.getText("Merger.translated.text");
			}
			mergedFileName += extension;
		} else {
			mergedFileName = getOutputFolder() + File.separator
					+ compareNames(subs1.getFileName(), subs2.getFileName());
		}
		return mergedFileName;
	}

	/**
	 * It compares the input names of the SRTs (files) and get the part of those
	 * name which is the same.
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	private String compareNames(String str1, String str2) {
		str1 = str1.substring(str1.lastIndexOf(File.separator) + 1).replaceAll(
				SrtUtils.SRT_EXT, "");
		str2 = str2.substring(str2.lastIndexOf(File.separator) + 1).replaceAll(
				SrtUtils.SRT_EXT, "");

		List<String> set1 = new ArrayList<String>(Arrays.asList(str1
				.split(" |_|\\.")));
		List<String> set2 = new ArrayList<String>(Arrays.asList(str2
				.split(" |_|\\.")));
		set1.retainAll(set2);

		StringBuilder sb = new StringBuilder();
		for (String s : set1) {
			sb.append(s).append(SrtUtils.getSpace());
		}
		String finalName = sb.toString().trim();
		if (finalName.isEmpty()) {
			finalName = I18N.getText("Merger.finalName.text").trim();
		}
		return finalName + SrtUtils.SRT_EXT;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public boolean isProgressive() {
		return progressive;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

	public boolean isTranslate() {
		return translate;
	}

	public boolean isMerge() {
		return merge;
	}

	/**
	 * 0 = left; 1 = right; 2 = max; 3 = min
	 * 
	 * @return
	 */
	public int getDesynch() {
		return desynch;
	}

}
