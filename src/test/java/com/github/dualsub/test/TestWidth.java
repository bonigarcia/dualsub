package com.github.dualsub.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.github.dualsub.srt.SrtUtils;
import com.github.dualsub.util.Log;

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
