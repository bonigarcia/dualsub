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

import java.util.LinkedList;
import java.util.List;

import com.github.dualsub.util.Log;

/**
 * Entry.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Entry {

	public List<String> subtitleLines;

	public Entry() {
		this.subtitleLines = new LinkedList<String>();
	}

	public Entry(Entry entry) {
		this.subtitleLines = entry.getSubtitleLines();
	}

	public List<String> getSubtitleLines() {
		return subtitleLines;
	}

	public int size() {
		return subtitleLines.size();
	}

	public String get(int index) {
		return subtitleLines.get(index);
	}

	public void add(String line) {
		subtitleLines.add(line);
	}

	public void set(int index, String element) {
		subtitleLines.set(index, element);
	}

	public void log() {
		for (String s : subtitleLines) {
			Log.info(s);
		}
	}

	public void addAll(Entry newEntry) {
		subtitleLines.addAll(newEntry.getSubtitleLines());
	}

}
