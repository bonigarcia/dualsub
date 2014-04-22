/*
 * (C) Copyright 2014 Boni Garcia (http://about.me/boni.garcia)
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
package com.github.dualsub.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.MissingResourceException;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.github.dualsub.util.I18N;
import com.github.dualsub.util.Log;

/**
 * PanelOutput.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class PanelOutput extends JPanel {

	private static final long serialVersionUID = 1L;

	// Parent
	private DualSub parent;

	// UI Elements
	private JComboBox<CharsetItem> charsetComboBox;
	private JRadioButton rdbtnSpace;
	private JRadioButton rdbtnHardSpace;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	private JTextField separator;

	public PanelOutput(DualSub parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), I18N
				.getHtmlText("PanelOutput.border.text"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		this.setBounds(46, 335, 305, 111);
		this.setBackground(parent.getBackground());

		// Charset
		JLabel charsetLabel = new JLabel(
				I18N.getHtmlText("PanelOutput.charset.text"));
		charsetLabel.setBounds(10, 26, 90, 20);
		this.add(charsetLabel);

		charsetComboBox = new JComboBox<CharsetItem>();
		charsetComboBox.setBounds(80, 26, 220, 20);
		charsetComboBox.setCursor(parent.getCursor());
		this.add(charsetComboBox);

		String[] charsets = parent.getProperties().getProperty("charsets")
				.split(",");
		Vector<CharsetItem> charsetVector = new Vector<CharsetItem>();
		CharsetItem item;
		String description;
		for (String s : charsets) {
			try {
				description = I18N.getText("PanelOutput." + s + ".text");
			} catch (MissingResourceException e) {
				description = "";
			}
			item = new CharsetItem(s, description);
			charsetVector.add(item);
		}

		Object comp = charsetComboBox.getUI().getAccessibleChild(this, 0);
		JPopupMenu popup = (JPopupMenu) comp;
		JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
		scrollPane
				.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		charsetComboBox.setModel(new DefaultComboBoxModel<CharsetItem>(
				charsetVector));

		String savedCharset = parent.getPreferences().get("charset",
				parent.getProperties().getProperty("selectedCharsetIndex"));
		if (savedCharset != null) {
			charsetComboBox.setSelectedIndex(Integer.parseInt(savedCharset));
		}

		// Padding
		JLabel paddingLabel = new JLabel(
				I18N.getHtmlText("PanelOutput.padding.text"));
		paddingLabel.setBounds(10, 50, 90, 30);
		this.add(paddingLabel);

		boolean savedPaddingSpace = Boolean.parseBoolean(parent
				.getPreferences().get("padding",
						parent.getProperties().getProperty("paddingSpace")));
		rdbtnSpace = new JRadioButton(
				I18N.getHtmlText("PanelOutput.space.text"));
		rdbtnSpace.setBounds(75, 50, 65, 30);
		rdbtnSpace.setCursor(parent.getCursor());
		rdbtnSpace.setBackground(parent.getBackground());
		rdbtnSpace.setSelected(savedPaddingSpace);
		this.add(rdbtnSpace);

		rdbtnHardSpace = new JRadioButton(
				I18N.getHtmlText("PanelOutput.hardspace.text"));
		rdbtnHardSpace.setBounds(145, 50, 155, 30);
		rdbtnHardSpace.setCursor(parent.getCursor());
		rdbtnHardSpace.setBackground(parent.getBackground());
		rdbtnHardSpace.setSelected(!savedPaddingSpace);
		this.add(rdbtnHardSpace);

		ButtonGroup groupPadding = new ButtonGroup();
		groupPadding.add(rdbtnSpace);
		groupPadding.add(rdbtnHardSpace);

		// Separator
		JLabel separatorLabel = new JLabel(
				I18N.getHtmlText("PanelOutput.separator.text"));
		separatorLabel.setBounds(10, 80, 90, 20);
		this.add(separatorLabel);

		boolean savedSeparator = Boolean.parseBoolean(parent.getPreferences()
				.get("separator",
						parent.getProperties().getProperty("separator")));
		rdbtnYes = new JRadioButton(
				I18N.getHtmlText("PanelTiming.rdbtnYes.text"));
		rdbtnYes.setBounds(75, 80, 40, 23);
		rdbtnYes.setCursor(parent.getCursor());
		rdbtnYes.setBackground(parent.getBackground());
		rdbtnYes.setSelected(savedSeparator);
		this.add(rdbtnYes);

		separator = new JTextField();
		separator.setBounds(115, 80, 25, 23);
		String savedSeparatorCharacter = parent.getPreferences().get(
				"separatorCharacter",
				parent.getProperties().getProperty("separatorCharacter"));
		separator.setDocument(new JTextFieldLimit(1));
		separator.setText(savedSeparatorCharacter);
		this.add(separator);

		rdbtnNo = new JRadioButton(I18N.getHtmlText("PanelTiming.rdbtnNo.text"));
		rdbtnNo.setBounds(145, 80, 60, 23);
		rdbtnNo.setCursor(parent.getCursor());
		rdbtnNo.setBackground(parent.getBackground());
		rdbtnNo.setSelected(!savedSeparator);
		this.add(rdbtnNo);

		ButtonGroup groupExtension = new ButtonGroup();
		groupExtension.add(rdbtnYes);
		groupExtension.add(rdbtnNo);

		// Help
		JButton buttonHelpSub = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/help.png")));
		buttonHelpSub.setBounds(273, 80, 22, 22);
		buttonHelpSub.setCursor(parent.getCursor());
		buttonHelpSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpOutputDialog helpOutputDialog = parent.getHelpOutput();
				if (helpOutputDialog == null) {
					helpOutputDialog = new HelpOutputDialog(parent, true);
				}
				helpOutputDialog.setVisible();
			}
		});
		this.add(buttonHelpSub);

		// Borders (for debug purposes)
		if (Log.getLevel().equals(Level.FINE)) {
			Border border = BorderFactory.createLineBorder(Color.black);
			charsetLabel.setBorder(border);
			paddingLabel.setBorder(border);
			separatorLabel.setBorder(border);
			rdbtnNo.setBorderPainted(true);
			rdbtnYes.setBorderPainted(true);
			rdbtnSpace.setBorderPainted(true);
			rdbtnHardSpace.setBorderPainted(true);
		}
	}

	public JComboBox<CharsetItem> getCharsetComboBox() {
		return charsetComboBox;
	}

	public JRadioButton getRdbtnSpace() {
		return rdbtnSpace;
	}

	public JRadioButton getRdbtnHardSpace() {
		return rdbtnHardSpace;
	}

	public JRadioButton getRdbtnYes() {
		return rdbtnYes;
	}

	public JRadioButton getRdbtnNo() {
		return rdbtnNo;
	}

	public JTextField getSeparator() {
		return separator;
	}

}
