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
package io.github.bonigarcia.dualsub.gui;

import io.github.bonigarcia.dualsub.util.I18N;
import io.github.bonigarcia.dualsub.util.I18N.Html;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ReminderSurveyDialog.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.1.0
 */
public class ReminderSurveyDialog extends HelpParent {

	private static final Logger log = LoggerFactory
			.getLogger(ReminderSurveyDialog.class);

	private static final long serialVersionUID = 1L;
	private static final int GRACE_PERIOD = 20;

	private String id;
	private boolean forcedMode;
	private boolean completed = false;

	public ReminderSurveyDialog(DualSub parent, boolean modal, String id,
			int timesPosponed) {
		super(parent, modal);
		this.id = id;
		this.forcedMode = timesPosponed > GRACE_PERIOD;
		log.debug("id {}, timesPosponed {}, GRACE_PERIOD {}, forcedMode {}",
				id, timesPosponed, GRACE_PERIOD, forcedMode);
		setHeight(220);
		initComponents();
	}

	@Override
	protected void initComponents() {
		// Features
		final int marginLeft = 23;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		panel.setBackground(parent.getBackground());
		JScrollPane scroll = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scroll);

		// Title
		final String title = I18N
				.getHtmlText("ReminderSurveyDialog.title.text");
		setTitle(I18N.getText("Window.name.text"));
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida", Font.BOLD, 20));
		lblTitle.setBounds(marginLeft, 20, 440, 25);
		panel.add(lblTitle);

		// Content
		JLabel lblContent01 = new JLabel(
				I18N.getHtmlText("ReminderSurveyDialog.text.01"));
		lblContent01.setBounds(marginLeft, 45, 440, 50);
		panel.add(lblContent01);

		final String surveyUrl = parent.getProperties()
				.getProperty("surveyUrl")
				+ "viewform?"
				+ parent.getProperties().getProperty("idField") + "=" + id;
		JButton lblContent02 = new UrlButton(I18N.getHtmlText(
				"WhatsNewDialog.text.03", Html.LINK), surveyUrl,
				parent.getCursor(), parent.getBackground(), new Rectangle(
						marginLeft + 110, 95, 250, 15));
		panel.add(lblContent02);

		JLabel lblContent03 = new JLabel(
				I18N.getHtmlText("WhatsNewDialog.text.04"));
		lblContent03.setBounds(marginLeft, 120, 440, 20);
		panel.add(lblContent03);

		int shift = forcedMode ? -20 : 60;
		if (!forcedMode) {
			JButton laterButton = new JButton(
					I18N.getHtmlText("ReminderSurveyDialog.text.02"));
			laterButton.setBounds(marginLeft + shift, 150, 150, 20);
			laterButton.setCursor(parent.getCursor());

			laterButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			panel.add(laterButton);
		}

		JButton doneButton = new JButton(
				I18N.getHtmlText("ReminderSurveyDialog.text.03"));
		doneButton.setBounds(marginLeft + shift + 175, 150, 150, 20);
		doneButton.setCursor(parent.getCursor());
		final JDialog parentDialog = this;
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					completed = parent.isSurveyCompleted(id);
				} catch (Exception ex) {
					Alert.info(ex.getMessage());
					log.error(ex.getMessage(), ex);
				} finally {
					if (completed) {
						parentDialog
								.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						Alert.info(I18N
								.getHtmlText("ReminderSurveyDialog.text.06"));
						dispose();
					} else {
						Alert.info(I18N
								.getHtmlText("ReminderSurveyDialog.text.05"));
						UrlOpener.open(surveyUrl);
					}
				}
			}
		});
		panel.add(doneButton);

		if (forcedMode) {
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}

		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				if (!completed) {
					Alert.info(I18N.getHtmlText("ReminderSurveyDialog.text.04"));
					completed = true;
				}
			}
		});

		// Borders (for debug purposes)
		if (log.isTraceEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblTitle.setBorder(border);
			lblContent01.setBorder(border);
			lblContent02.setBorder(border);
			lblContent03.setBorder(border);
			panel.setBorder(border);
		}
	}

}