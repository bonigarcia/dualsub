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
package io.github.bonigarcia.dualsub.translate;

import io.github.bonigarcia.dualsub.gui.CaptchaDialog;
import io.github.bonigarcia.dualsub.gui.CaptchaPanel;
import io.github.bonigarcia.dualsub.gui.DualSub;
import io.github.bonigarcia.dualsub.util.Log;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

/**
 * Translator.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Translator {

	private static final String GOOGLE_TRANSLATOR_URL = "http://translate.google.com/translate_a/t";
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String IP4_GOOGLE = "http://ipv4.google.com";
	private static final String SORRY_URL = "/sorry/IndexRedirect?continue="
			+ GOOGLE_TRANSLATOR_URL;
	private static final String CAPTCHA_URL = "/sorry/CaptchaRedirect?continue="
			+ GOOGLE_TRANSLATOR_URL;
	private static final String IMG_HTML = "<img src=";
	private static final String ID_IMG_HTML = "id=";
	private static final String GOOGLE_ABUSE = "google_abuse=";

	private static Translator singleton = null;
	private static Gson gson;
	private static String googleAbuseExceptionCookie;
	private static String captchaSolution;
	private static DualSub parent;
	private static int indexGoogleAbuse = -1;

	public static Translator getInstance() {
		if (singleton == null) {
			singleton = new Translator();
			gson = new Gson();
		}
		return singleton;
	}

	private static void readGoogleAbuseExceptionCookie()
			throws ClientProtocolException, IOException {
		// GET to SORRY_URL
		String resultSorry = sendGet(IP4_GOOGLE + SORRY_URL);

		// Find image and id
		int initImg = resultSorry.indexOf(IMG_HTML) + IMG_HTML.length() + 1;
		int endImg = resultSorry.indexOf("\"", initImg + 1);
		String img = resultSorry.substring(initImg, endImg);

		int initIdImg = img.indexOf(ID_IMG_HTML) + ID_IMG_HTML.length();
		int endIdImg = img.indexOf("&", initIdImg + 1);
		String imgId = img.substring(initIdImg, endIdImg);

		// GET captcha
		BufferedImage captchaImg = getImage(IP4_GOOGLE + img);

		// Draw captcha
		if (parent != null) {
			CaptchaDialog captchaDialog = parent.getCaptchaDialog();
			if (captchaDialog == null) {
				captchaDialog = new CaptchaDialog(parent, true, captchaImg);
			}
			captchaDialog.setVisible();
		} else {
			// Custom frame for captcha
			JFrame frame = new JFrame("Captcha");
			CaptchaPanel captchaPanel = new CaptchaPanel(captchaImg);
			frame.add(captchaPanel);
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			// Ask for captcha solution from the standard input
			System.out.print("Solve captcha: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String captcha = br.readLine();

			// Set captcha and dispose frame
			Translator.getInstance().setCaptchaSolution(captcha);
			frame.dispose();
		}

		// GET to CAPTCHA_URL
		sendGet(IP4_GOOGLE + CAPTCHA_URL + "&id=" + imgId + "&captcha="
				+ captchaSolution);

		// Result
		Log.debug("googleAbuseExceptionCookie " + googleAbuseExceptionCookie);
	}

	private static BufferedImage getImage(String url)
			throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);

		HttpResponse response = client.execute(request);

		googleAbuseExceptionCookie = response.getFirstHeader("Set-Cookie")
				.getValue();
		googleAbuseExceptionCookie = googleAbuseExceptionCookie.substring(0,
				googleAbuseExceptionCookie.indexOf(";"));

		return ImageIO.read(response.getEntity().getContent());
	}

	public String translate(String text, String languageFrom,
			String languageTo, String charset) {

		String trans = "";
		do {
			try {
				if (googleAbuseExceptionCookie == null) {
					// Read captcha in order to get google_abuse cookie
					readGoogleAbuseExceptionCookie();
				}

				if (indexGoogleAbuse == 0) {
					// User has canceled the translation
					googleAbuseExceptionCookie = null;
					indexGoogleAbuse = -1;
					throw new TranslationCanceled();
				}

				StringBuilder stringBuilder = new StringBuilder(
						GOOGLE_TRANSLATOR_URL);
				text = URLEncoder.encode(text, charset);
				stringBuilder.append("?client=p&text=" + text);
				stringBuilder.append("&sl=" + languageFrom);
				stringBuilder.append("&tl=" + languageTo);

				String result = sendGet(stringBuilder.toString());
				Translation translation = gson.fromJson(result,
						Translation.class);

				for (Sentences s : translation.getSentences()) {
					trans += s.getTrans();
				}
				Log.debug(charset + " ** " + stringBuilder.toString()
						+ " ---- " + trans);
			} catch (TranslationCanceled e) {
				googleAbuseExceptionCookie = null;
				throw e;
			} catch (Exception e) {
				googleAbuseExceptionCookie = null;
			}
		} while (indexGoogleAbuse == -1);

		return trans;
	}

	private static String sendGet(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = HttpClientBuilder.create()
				.disableRedirectHandling().build();

		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);

		if (googleAbuseExceptionCookie != null) {
			request.addHeader("Cookie", googleAbuseExceptionCookie);
		}

		HttpResponse response = client.execute(request);

		Log.debug("Sending GET request to URL : " + url);
		Log.debug("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		Header location = response.getFirstHeader("location");
		if (location != null) {
			String locationDecoded = URLDecoder.decode(location.getValue(),
					Charset.defaultCharset().name());

			indexGoogleAbuse = locationDecoded.indexOf(GOOGLE_ABUSE);
			if (indexGoogleAbuse != -1) {
				locationDecoded = locationDecoded.substring(indexGoogleAbuse
						+ GOOGLE_ABUSE.length());
				googleAbuseExceptionCookie = locationDecoded.substring(0,
						locationDecoded.indexOf(";"));
			}
		}

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	public void setGoogleAbuseExceptionCookie(String cookie) {
		googleAbuseExceptionCookie = cookie;
	}

	public void setParent(DualSub dualsub) {
		parent = dualsub;
	}

	public void setCaptchaSolution(String captcha) {
		captchaSolution = captcha;
	}

	public void setIndexGoogleAbuse(int index) {
		indexGoogleAbuse = index;
	}

}
