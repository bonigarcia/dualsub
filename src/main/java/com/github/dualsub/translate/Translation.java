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
package com.github.dualsub.translate;

/**
 * Translation.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Translation {
	private Sentences[] sentences;
	private String en;
	private Integer server_time;

	public Sentences[] getSentences() {
		return sentences;
	}

	public String getEn() {
		return en;
	}

	public Integer getServer_time() {
		return server_time;
	}

}

/**
 * Sentences.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
class Sentences {
	private String trans;
	private String orig;
	private String translit;
	private String src_translit;

	public String getTrans() {
		return trans;
	}

	public String getOrig() {
		return orig;
	}

	public String getTranslit() {
		return translit;
	}

	public String getSrc_translit() {
		return src_translit;
	}

}
