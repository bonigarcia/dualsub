package com.github.dualsub.test;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class JLabelLink extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String LABEL_TEXT = "For further information visit:";
	private static final String A_VALID_LINK = "http://stackoverflow.com";
	private static final String A_HREF = "<a href=\"";
	private static final String HREF_CLOSED = "\">";
	private static final String HREF_END = "</a>";
	private static final String HTML = "<html>";
	private static final String HTML_END = "</html>";

	public JLabelLink() {
		setTitle("HTML link via a JLabel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel label = new JLabel(LABEL_TEXT);
		contentPane.add(label);

		label = new JLabel(A_VALID_LINK);
		contentPane.add(label);
		if (isBrowsingSupported()) {
			makeLinkable(label, new LinkMouseListener());
		}

		pack();
	}

	private static void makeLinkable(JLabel c, MouseListener ml) {
		assert ml != null;
		c.setText(htmlIfy(linkIfy(c.getText())));
		c.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		c.addMouseListener(ml);
	}

	private static boolean isBrowsingSupported() {
		if (!Desktop.isDesktopSupported()) {
			return false;
		}
		boolean result = false;
		Desktop desktop = java.awt.Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE)) {
			result = true;
		}
		return result;

	}

	private static class LinkMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			JLabel l = (JLabel) evt.getSource();
			try {
				URI uri = new java.net.URI(JLabelLink.getPlainLink(l.getText()));
				(new LinkRunner(uri)).execute();
			} catch (URISyntaxException use) {
				throw new AssertionError(use + ": " + l.getText()); // NOI18N
			}
		}
	}

	private static class LinkRunner extends SwingWorker<Void, Void> {

		private final URI uri;

		private LinkRunner(URI u) {
			if (u == null) {
				throw new NullPointerException();
			}
			uri = u;
		}

		@Override
		protected Void doInBackground() throws Exception {
			Desktop desktop = java.awt.Desktop.getDesktop();
			desktop.browse(uri);
			return null;
		}

		@Override
		protected void done() {
			try {
				get();
			} catch (ExecutionException ee) {
				handleException(uri, ee);
			} catch (InterruptedException ie) {
				handleException(uri, ie);
			}
		}

		private static void handleException(URI u, Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Sorry, a problem occurred while trying to open this link in your system's standard browser.",
							"A problem occured", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static String getPlainLink(String s) {
		return s.substring(s.indexOf(A_HREF) + A_HREF.length(),
				s.indexOf(HREF_CLOSED));
	}

	// WARNING
	// This method requires that s is a plain string that requires
	// no further escaping
	private static String linkIfy(String s) {
		return A_HREF.concat(s).concat(HREF_CLOSED).concat(s).concat(HREF_END);
	}

	// WARNING
	// This method requires that s is a plain string that requires
	// no further escaping
	private static String htmlIfy(String s) {
		return HTML.concat(s).concat(HTML_END);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JLabelLink().setVisible(true);
			}
		});
	}
}