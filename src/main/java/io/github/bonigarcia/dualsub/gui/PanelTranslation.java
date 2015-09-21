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
package io.github.bonigarcia.dualsub.gui;

import io.github.bonigarcia.dualsub.util.I18N;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PanelTranslation.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class PanelTranslation extends JPanel {

	private static final Logger log = LoggerFactory
			.getLogger(PanelTranslation.class);

	private static final long serialVersionUID = 1L;

	// Parent
	private DualSub parent;

	// UI Elements
	private JCheckBox enableTranslation;
	private JComboBox<LangItem> fromComboBox;
	private JComboBox<LangItem> toComboBox;
	private JRadioButton rdbtnMerged;
	private JRadioButton rdbtnTranslated;

	public PanelTranslation(DualSub parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), I18N
				.getHtmlText("PanelTranslation.border.text"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setBounds(360, 335, 305, 111);
		this.setBackground(parent.getBackground());

		// Enable translation
		enableTranslation = new JCheckBox(
				I18N.getHtmlText("PanelTranslation.enable.text"));
		enableTranslation.setBounds(10, 20, 220, 20);
		enableTranslation.setCursor(parent.getCursor());
		enableTranslation.setBackground(parent.getBackground());
		boolean savedTranslation = Boolean.parseBoolean(parent.getPreferences()
				.get("translation",
						parent.getProperties().getProperty("translation")));
		enableTranslation.setSelected(savedTranslation);
		enableTranslation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchLang(getEnableTranslation().isSelected());
			}
		});
		this.add(enableTranslation);

		// Languages
		JLabel fromLabel = new JLabel(
				I18N.getHtmlText("PanelTranslation.from.text"));
		fromLabel.setBounds(10, 45, 55, 20);
		this.add(fromLabel);
		fromComboBox = new JComboBox<LangItem>();
		fromComboBox.setBounds(65, 45, 95, 20);
		fromComboBox.setCursor(parent.getCursor());
		this.add(fromComboBox);

		String savedLangFrom = parent.getPreferences().get("langFrom",
				parent.getProperties().getProperty("langFrom"));
		String savedLangTo = parent.getPreferences().get("langTo",
				parent.getProperties().getProperty("langTo"));
		LangItem langFrom = null, langTo = null, item = null;
		String[] languages = parent.getProperties().getProperty("languages")
				.split(",");
		Vector<LangItem> langVector = new Vector<LangItem>();
		for (String s : languages) {
			item = new LangItem(s, I18N.getHtmlText("PanelTranslation."
					+ s.toLowerCase() + ".text"));
			langVector.add(item);
			if (s.equals(savedLangFrom)) {
				langFrom = item;
			} else if (s.equals(savedLangTo)) {
				langTo = item;
			}
		}
		Collections.sort(langVector);

		fromComboBox.setModel(new DefaultComboBoxModel<LangItem>(langVector));
		if (langFrom != null) {
			fromComboBox.setSelectedItem(langFrom);
		}

		JLabel toLabel = new JLabel(
				I18N.getHtmlText("PanelTranslation.to.text"));
		toLabel.setBounds(160, 45, 40, 20);
		this.add(toLabel);
		toComboBox = new JComboBox<LangItem>();
		toComboBox.setBounds(200, 45, 95, 20);
		toComboBox.setCursor(parent.getCursor());
		this.add(toComboBox);
		toComboBox.setModel(new DefaultComboBoxModel<LangItem>(langVector));
		if (langTo != null) {
			toComboBox.setSelectedItem(langTo);
		}

		// Output
		JLabel separatorLabel = new JLabel(
				I18N.getHtmlText("PanelTranslation.output.text"));
		separatorLabel.setBounds(10, 73, 70, 20);
		this.add(separatorLabel);

		boolean savedMergeTranslation = Boolean
				.parseBoolean(parent.getPreferences().get("mergeTranslation",
						parent.getProperties().getProperty("mergeTranslation")));
		rdbtnMerged = new JRadioButton(
				I18N.getHtmlText("PanelTranslation.merged.text"));
		rdbtnMerged.setBounds(80, 68, 150, 20);
		rdbtnMerged.setCursor(parent.getCursor());
		rdbtnMerged.setBackground(parent.getBackground());
		rdbtnMerged.setSelected(savedMergeTranslation);
		rdbtnMerged.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchButtonText(getEnableTranslation().isSelected());
			}
		});
		this.add(rdbtnMerged);

		rdbtnTranslated = new JRadioButton(
				I18N.getHtmlText("PanelTranslation.just.text"));
		rdbtnTranslated.setBounds(80, 85, 150, 20);
		rdbtnTranslated.setCursor(parent.getCursor());
		rdbtnTranslated.setBackground(parent.getBackground());
		rdbtnTranslated.setSelected(!savedMergeTranslation);
		rdbtnTranslated.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchButtonText(getEnableTranslation().isSelected());
			}
		});
		this.add(rdbtnTranslated);

		ButtonGroup groupExtension = new ButtonGroup();
		groupExtension.add(rdbtnMerged);
		groupExtension.add(rdbtnTranslated);

		switchLang(getEnableTranslation().isSelected());

		// Help
		JButton buttonHelpSub = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/help.png")));
		buttonHelpSub.setBounds(273, 80, 22, 22);
		buttonHelpSub.setCursor(parent.getCursor());
		buttonHelpSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpTranslationDialog helpTranslationDialog = parent
						.getHelpTranslation();
				if (helpTranslationDialog == null) {
					helpTranslationDialog = new HelpTranslationDialog(parent,
							true);
				}
				helpTranslationDialog.setVisible();
			}
		});
		this.add(buttonHelpSub);

		// Borders (for debug purposes)
		if (log.isTraceEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			fromLabel.setBorder(border);
			toLabel.setBorder(border);
			separatorLabel.setBorder(border);
			enableTranslation.setBorderPainted(true);
			rdbtnMerged.setBorderPainted(true);
			rdbtnTranslated.setBorderPainted(true);
		}
	}

	public JCheckBox getEnableTranslation() {
		return enableTranslation;
	}

	public JComboBox<LangItem> getFromComboBox() {
		return fromComboBox;
	}

	public JComboBox<LangItem> getToComboBox() {
		return toComboBox;
	}

	public JRadioButton getRdbtnMerged() {
		return rdbtnMerged;
	}

	public JRadioButton getRdbtnTranslated() {
		return rdbtnTranslated;
	}

	private void switchLang(boolean enable) {
		getFromComboBox().setEnabled(enable);
		getToComboBox().setEnabled(enable);
		getRdbtnMerged().setEnabled(enable);
		getRdbtnTranslated().setEnabled(enable);

		switchButtonText(enable);
	}

	private void switchButtonText(boolean enable) {
		if (enable) {
			if (rdbtnTranslated.isSelected()) {
				parent.getMergeButton().setText(
						I18N.getHtmlText("Window.translateButton.text"));
			} else {
				parent.getMergeButton().setText(
						I18N.getHtmlText("Window.mergeTranslateButton.text"));
			}
		} else {
			parent.getMergeButton().setText(
					I18N.getHtmlText("Window.mergeButton.text"));
		}
	}

}