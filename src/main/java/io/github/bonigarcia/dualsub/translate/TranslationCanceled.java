/*
 * (C) Copyright 2015 Boni Garcia (http://bonigarcia.github.io/)
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
package io.github.bonigarcia.dualsub.translate;

/**
 * TranslationCanceled.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.1
 */
public class TranslationCanceled extends RuntimeException {

	private static final long serialVersionUID = -7331490683070646382L;

	public TranslationCanceled() {
		super("Translation canceled by user");
	}

}
