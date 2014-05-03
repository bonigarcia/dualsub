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
package com.github.dualsub.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ErrorManager;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Log.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Log {

	private final Logger LOG = Logger.getLogger(Log.class.getName());
	private static Log singleton = null;
	private static String NULL = "null";

	public Log() {
		LOG.addHandler(new LogHandler());
		LOG.setLevel(Level.INFO);
		LOG.setUseParentHandlers(false);
	}

	public static Log getSingleton() {
		if (singleton == null) {
			singleton = new Log();
		}
		return singleton;
	}

	public static Level getLevel() {
		return Log.getSingleton().LOG.getLevel();
	}

	public static void info(final Object message) {
		Log.getSingleton().LOG.log(Level.INFO,
				message != null ? message.toString() : NULL);
	}

	public static void warn(final Object message) {
		Log.getSingleton().LOG.log(Level.WARNING,
				message != null ? message.toString() : NULL);
	}

	public static void error(final Object message, Throwable t) {
		Level errorLevel = Level.SEVERE;
		Log.getSingleton().LOG.log(errorLevel, message + " (" + t.getMessage()
				+ ")");
		StackTraceElement[] trace = t.getStackTrace();
		for (StackTraceElement element : trace) {
			Log.getSingleton().LOG.log(errorLevel, element.toString());
		}
	}

	public static void error(final Object message) {
		Log.getSingleton().LOG.log(Level.SEVERE,
				message != null ? message.toString() : NULL);
	}

	public static void debug(final Object message) {
		Log.getSingleton().LOG.log(Level.CONFIG,
				message != null ? message.toString() : NULL);
	}

	class LogHandler extends Handler {
		private PrintWriter out;
		private PrintWriter err;

		public LogHandler() {
			out = new PrintWriter(System.out, true);
			err = new PrintWriter(System.err, true);
		}

		@Override
		public void publish(LogRecord record) {
			if (getFormatter() == null) {
				setFormatter(new LogFormatter());
			}

			try {
				String message = getFormatter().format(record);
				if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
					err.println(message);
					err.flush();
				} else {
					out.println(message);
					out.flush();
				}
				// To avoid Eclipse bug
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=32205
				Thread.sleep(10);
			} catch (Exception exception) {
				reportError(null, exception, ErrorManager.FORMAT_FAILURE);
				return;
			}
		}

		@Override
		public void close() throws SecurityException {
		}

		@Override
		public void flush() {
		}
	}

	class LogFormatter extends Formatter {

		@Override
		public String format(LogRecord record) {

			StackTraceElement[] trace = Thread.currentThread().getStackTrace();
			StackTraceElement last = trace[trace.length - 1];
			StringBuilder sb = new StringBuilder();
			try {
				sb.append(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
								.format(new Date())).append(" [")
						.append(last.getClassName()).append("][")
						.append(last.getMethodName()).append(",")
						.append(last.getLineNumber()).append("] ")
						.append(record.getLevel().getName()).append(": ")
						.append(formatMessage(record));

				if (record.getThrown() != null) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					record.getThrown().printStackTrace(pw);
					pw.close();
					sb.append(sw.toString());

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return sb.toString();
		}
	}
}
