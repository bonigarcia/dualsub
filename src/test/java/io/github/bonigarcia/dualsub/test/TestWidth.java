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

import io.github.bonigarcia.dualsub.srt.SrtUtils;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestWidth.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class TestWidth {

	private static final Logger log = LoggerFactory.getLogger(TestWidth.class);

	@Before
	public void setup() throws IOException {
		SrtUtils.init("624", "Tahoma", 17, true, true, ".", 50, false, null,
				null);
	}

	@Test
	public void calculateWidhtArial() {
		log.info("Arial very small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789012345678901"));
		log.info("Arial small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789041"));
		log.info("Arial normal "
				+ SrtUtils.getWidth("12345678901234567890123456789012345678"));
		log.info("Arial big "
				+ SrtUtils.getWidth("1234567890123456789012345678"));
		log.info("Arial very big " + SrtUtils.getWidth("12345678901234"));
		log.info("-----------");

	}

	@Test
	public void calculateWidhtTahoma() {
		log.info("Tahoma very small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789012345678901"));
		log.info("Tahoma small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789041345"));
		log.info("Tahoma normal "
				+ SrtUtils.getWidth("12345678901234567890123456789012345678"));
		log.info("Tahoma big "
				+ SrtUtils.getWidth("1234567890123456789012345678"));
		log.info("Tahoma very big " + SrtUtils.getWidth("12345678901234"));
		log.info("-----------");

	}

	@Test
	public void calculateWidhtVerdana() {
		log.info("Verdana very small "
				+ SrtUtils
						.getWidth("12345678901234567890123456789012345678901"));
		log.info("Verdana small "
				+ SrtUtils.getWidth("12345678901234567890123456789012345678"));
		log.info("Verdana normal "
				+ SrtUtils.getWidth("123456789012345678901234567890123"));
		log.info("Verdana big "
				+ SrtUtils.getWidth("1234567890123456789012345"));
		log.info("Verdana very big " + SrtUtils.getWidth("123456789012"));
		log.info("-----------");

		// TV Samsung : Custom 370 px
	}

}
