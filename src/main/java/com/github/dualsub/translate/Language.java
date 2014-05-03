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
 * Language.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Language {
	public static final String AFRIKAANS = "af";
	public static final String ALBANIAN = "sq";
	public static final String ARABIC = "ar";
	public static final String ARMENIAN = "hy";
	public static final String AZERBAIJANI = "az";
	public static final String BASQUE = "eu";
	public static final String BELARUSIAN = "be";
	public static final String BENGALI = "bn";
	public static final String BULGARIAN = "bg";
	public static final String CATALAN = "ca";
	public static final String CHINESE = "zh-CN";
	public static final String CROATIAN = "hr";
	public static final String CZECH = "cs";
	public static final String DANISH = "da";
	public static final String DUTCH = "nl";
	public static final String ENGLISH = "en";
	public static final String ESTONIAN = "et";
	public static final String FILIPINO = "tl";
	public static final String FINNISH = "fi";
	public static final String FRENCH = "fr";
	public static final String GALICIAN = "gl";
	public static final String GEORGIAN = "ka";
	public static final String GERMAN = "de";
	public static final String GREEK = "el";
	public static final String GUJARATI = "gu";
	public static final String HAITIAN_CREOLE = "ht";
	public static final String HEBREW = "iw";
	public static final String HINDI = "hi";
	public static final String HUNGARIAN = "hu";
	public static final String ICELANDIC = "is";
	public static final String INDONESIAN = "id";
	public static final String IRISH = "ga";
	public static final String ITALIAN = "it";
	public static final String JAPANESE = "ja";
	public static final String KANNADA = "kn";
	public static final String KOREAN = "ko";
	public static final String LATIN = "la";
	public static final String LATVIAN = "lv";
	public static final String LITHUANIAN = "lt";
	public static final String MACEDONIAN = "mk";
	public static final String MALAY = "ms";
	public static final String MALTESE = "mt";
	public static final String NORWEGIAN = "no";
	public static final String PERSIAN = "fa";
	public static final String POLISH = "pl";
	public static final String PORTUGUESE = "pt";
	public static final String ROMANIAN = "ro";
	public static final String RUSSIAN = "ru";
	public static final String SERBIAN = "sr";
	public static final String SLOVAK = "sk";
	public static final String SLOVENIAN = "sl";
	public static final String SPANISH = "es";
	public static final String SWAHILI = "sw";
	public static final String SWEDISH = "sv";
	public static final String TAMIL = "ta";
	public static final String TELUGU = "te";
	public static final String THAI = "th";
	public static final String TURKISH = "tr";
	public static final String UKRAINIAN = "uk";
	public static final String URDU = "ur";
	public static final String VIETNAMESE = "vi";
	public static final String WELSH = "cy";
	public static final String YIDDISH = "yi";
	public static final String CHINESE_SIMPLIFIED = "zh-CN";
	public static final String CHINESE_TRADITIONAL = "zh-TW";

	private static Language singleton;

	public static Language getInstance() {
		if (singleton == null) {
			singleton = new Language();
		}
		return singleton;
	}

}