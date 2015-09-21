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

import io.github.bonigarcia.dualsub.util.Font;
import io.github.bonigarcia.dualsub.util.I18N;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PanelPlayer.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class PanelPlayer extends JPanel {

	private static final Logger log = LoggerFactory
			.getLogger(PanelPlayer.class);

	private static final long serialVersionUID = 1L;

	// Parent
	private DualSub parent;

	// UI Elements
	private JTextField sizePx;
	private JComboBox<String> fontComboBox;
	private JComboBox<String> sizeComboBox;

	public PanelPlayer(DualSub parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), I18N
				.getHtmlText("PanelPlayer.border.text"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		this.setBounds(46, 220, 305, 111);
		this.setBackground(parent.getBackground());

		// Width
		JLabel lblWitdh = new JLabel(
				I18N.getHtmlText("PanelPlayer.lblWidth.text"));
		lblWitdh.setBounds(10, 26, 140, 20);
		this.add(lblWitdh);
		sizePx = new JTextField();
		sizePx.setColumns(10);
		sizePx.setBounds(150, 26, 60, 20);
		String savedWitdth = parent.getPreferences().get("width",
				parent.getProperties().getProperty("width"));
		sizePx.setText(savedWitdth);
		this.add(sizePx);
		JLabel lblPx = new JLabel(I18N.getHtmlText("PanelPlayer.lblPx.text"));
		lblPx.setBounds(215, 26, 40, 20);
		this.add(lblPx);

		// Font
		JLabel lblFont = new JLabel(
				I18N.getHtmlText("PanelPlayer.lblFont.text"));
		lblFont.setBounds(10, 50, 140, 20);
		this.add(lblFont);
		fontComboBox = new JComboBox<String>();
		fontComboBox.setBounds(150, 49, 150, 20);
		fontComboBox.setModel(new DefaultComboBoxModel<String>(Font
				.getFontList()));
		fontComboBox.setCursor(parent.getCursor());

		String savedFont = parent.getPreferences().get("font",
				parent.getProperties().getProperty("selectedFont"));
		if (savedFont != null) {
			fontComboBox.setSelectedItem(savedFont);
		}
		this.add(fontComboBox);

		// Size
		JLabel lblSize = new JLabel(
				I18N.getHtmlText("PanelPlayer.lblSize.text"));
		lblSize.setBounds(10, 73, 140, 20);
		this.add(lblSize);
		sizeComboBox = new JComboBox<String>();

		// VLC: Very small: 17pt - Small: 20pt
		// Normal: 22pt - Big: 29pt - Very big: 58pt
		sizeComboBox.setModel(new DefaultComboBoxModel<String>(Font
				.getWidthList()));
		sizeComboBox.setBounds(150, 72, 60, 20);
		sizeComboBox.setCursor(parent.getCursor());
		String savedSize = parent.getPreferences().get("size",
				parent.getProperties().getProperty("selectedSizeIndex"));
		if (savedSize != null) {
			sizeComboBox.setSelectedIndex(Integer.parseInt(savedSize));
		}
		this.add(sizeComboBox);
		JLabel lblPt = new JLabel(I18N.getHtmlText("PanelPlayer.lblPt.text"));
		lblPt.setBounds(215, 72, 40, 20);
		this.add(lblPt);

		// Help
		JButton buttonHelpSub = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/help.png")));
		buttonHelpSub.setBounds(273, 16, 22, 22);
		buttonHelpSub.setCursor(parent.getCursor());
		buttonHelpSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpPlayerDialog helpPlayerDialog = parent.getHelpPlayer();
				if (helpPlayerDialog == null) {
					helpPlayerDialog = new HelpPlayerDialog(parent, true);
				}
				helpPlayerDialog.setVisible();
			}
		});
		this.add(buttonHelpSub);

		// Borders (for debug purposes)
		if (log.isTraceEnabled()) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblWitdh.setBorder(border);
			lblFont.setBorder(border);
			lblSize.setBorder(border);
			lblPx.setBorder(border);
			lblPt.setBorder(border);
		}

	}

	public JTextField getSizePx() {
		return sizePx;
	}

	public JComboBox<String> getFontComboBox() {
		return fontComboBox;
	}

	public JComboBox<String> getSizeComboBox() {
		return sizeComboBox;
	}

}
