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
package com.github.dualsub.gui;

import com.github.dualsub.util.I18N;

/**
 * CharsetItem.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class CharsetItem implements Comparable<Object> {
	private String id;
	private String description;

	public CharsetItem(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		String output = id;
		if (!description.isEmpty()) {
			output += " - " + description;
		}
		return I18N.HTML_INIT_TAG + output + I18N.HTML_END_TAG;
	}

	@Override
	public int compareTo(Object arg) {
		return -((CharsetItem) arg).getDescription().compareTo(
				this.getDescription());
	}
}
