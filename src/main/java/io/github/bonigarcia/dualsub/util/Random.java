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
package io.github.bonigarcia.dualsub.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Random utility.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.1.0
 */
public class Random {

	private static final Logger log = LoggerFactory.getLogger(Random.class);

	private static String[] secureRndNames = new String[] {
			System.getProperty("boticelli.securerandom"), "SHA1PRNG",
			"IBMSecureRandom" };

	private static SecureRandom createSecureRandom() {
		SecureRandom secureRnd = null;
		try {

			for (int i = 0; i < secureRndNames.length; i++) {
				try {
					if (secureRndNames[i] != null) {
						secureRnd = SecureRandom.getInstance(secureRndNames[i]);
						break;
					}
				} catch (NoSuchAlgorithmException nsae) {
					log.error("no secure random algorithm named \""
							+ secureRndNames[i] + "\"", nsae);
				}
			}
			if (secureRnd == null) {
				throw new IllegalStateException(
						"no secure random algorithm found. (tried "
								+ Arrays.asList(secureRndNames) + ")");
			}
			secureRnd.setSeed(System.currentTimeMillis());
		} catch (Exception e) {
			log.error("error initializing secure random", e);
		}

		return secureRnd;
	}

	public static String createWord(int len) {
		return createWord(len, null);
	}

	public static String createWord(int len, char[] alphabet) {
		SecureRandom random = createSecureRandom();

		if (alphabet == null) {
			alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
					.toCharArray();
		}

		StringBuffer out = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			out.append(alphabet[random.nextInt(alphabet.length)]);
		}

		return out.toString();
	}
}
