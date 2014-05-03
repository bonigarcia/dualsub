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
package com.github.dualsub.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.github.dualsub.srt.SrtUtils;
import com.github.dualsub.util.Log;

/**
 * TestWidth.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class TestWidth {

	@Before
	public void setup() throws IOException {
		SrtUtils.init("624", "Tahoma", 17, true, true, ".", 50);
	}

	@Test
	public void calculateWidhtArial() {
		Log.info("Arial very small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789012345678901"));
		Log.info("Arial small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789041"));
		Log.info("Arial normal "
				+ SrtUtils.getWidth("12345678901234567890123456789012345678"));
		Log.info("Arial big "
				+ SrtUtils.getWidth("1234567890123456789012345678"));
		Log.info("Arial very big " + SrtUtils.getWidth("12345678901234"));
		Log.info("-----------");

	}

	@Test
	public void calculateWidhtTahoma() {
		Log.info("Tahoma very small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789012345678901"));
		Log.info("Tahoma small "
				+ SrtUtils
						.getWidth("123456789012345678901234567890123456789041345"));
		Log.info("Tahoma normal "
				+ SrtUtils.getWidth("12345678901234567890123456789012345678"));
		Log.info("Tahoma big "
				+ SrtUtils.getWidth("1234567890123456789012345678"));
		Log.info("Tahoma very big " + SrtUtils.getWidth("12345678901234"));
		Log.info("-----------");

	}

	@Test
	public void calculateWidhtVerdana() {
		Log.info("Verdana very small "
				+ SrtUtils
						.getWidth("12345678901234567890123456789012345678901"));
		Log.info("Verdana small "
				+ SrtUtils.getWidth("12345678901234567890123456789012345678"));
		Log.info("Verdana normal "
				+ SrtUtils.getWidth("123456789012345678901234567890123"));
		Log.info("Verdana big "
				+ SrtUtils.getWidth("1234567890123456789012345"));
		Log.info("Verdana very big " + SrtUtils.getWidth("123456789012"));
		Log.info("-----------");

		// TV Samsung : Custom 370 px
	}

}
