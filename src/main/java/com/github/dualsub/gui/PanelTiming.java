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
package com.github.dualsub.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
 * PanelTiming.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class PanelTiming extends JPanel {

	private static final long serialVersionUID = 1L;

	// Parent
	private DualSub parent;

	// UI Elements
	private JTextField extensionSeconds;
	private JCheckBox progressiveCheckBox;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	private JComboBox<String> desyncComboBox;

	public PanelTiming(DualSub parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), I18N
				.getHtmlText("PanelTiming.border.text"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		this.setBounds(360, 220, 305, 111);
		this.setLayout(null);
		this.setBackground(parent.getBackground());

		// Persistence
		JLabel lblPersistence = new JLabel(
				I18N.getHtmlText("PanelTiming.persistence.text"));
		lblPersistence.setBounds(12, 23, 100, 23);
		this.add(lblPersistence);

		// Extension
		extensionSeconds = new JTextField();
		extensionSeconds.setBounds(155, 23, 25, 20);
		extensionSeconds.setColumns(10);
		String savedSecobds = parent.getPreferences().get("seconds",
				parent.getProperties().getProperty("extensionSeconds"));
		if (savedSecobds != null) {
			extensionSeconds.setText(savedSecobds);
		}
		this.add(extensionSeconds);

		// Seconds
		JLabel lblSeconds = new JLabel(
				I18N.getHtmlText("PanelTiming.lblSeconds.text"));
		lblSeconds.setBounds(185, 23, 90, 20);
		this.add(lblSeconds);

		// Progressive
		boolean savedProgressive = Boolean.parseBoolean(parent.getPreferences()
				.get("progressive",
						parent.getProperties().getProperty("progressive")));
		progressiveCheckBox = new JCheckBox(
				I18N.getHtmlText("PanelTiming.progressive.text"));
		progressiveCheckBox.setSelected(savedProgressive);
		progressiveCheckBox.setBounds(185, 38, 105, 23);
		progressiveCheckBox.setCursor(parent.getCursor());
		progressiveCheckBox.setBackground(parent.getBackground());
		this.add(progressiveCheckBox);

		// No
		rdbtnNo = new JRadioButton(I18N.getHtmlText("PanelTiming.rdbtnNo.text"));
		rdbtnNo.setBounds(112, 43, 50, 23);
		rdbtnNo.setCursor(parent.getCursor());
		rdbtnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchRadioButton(false);
			}
		});
		rdbtnNo.setBackground(parent.getBackground());
		this.add(rdbtnNo);

		// Yes
		rdbtnYes = new JRadioButton(
				I18N.getHtmlText("PanelTiming.rdbtnYes.text"));
		rdbtnYes.setBounds(112, 23, 50, 23);
		rdbtnYes.setCursor(parent.getCursor());
		rdbtnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchRadioButton(true);
			}
		});
		rdbtnYes.setBackground(parent.getBackground());
		this.add(rdbtnYes);

		ButtonGroup groupExtension = new ButtonGroup();
		groupExtension.add(rdbtnYes);
		groupExtension.add(rdbtnNo);

		boolean savedExtension = Boolean.parseBoolean(parent.getPreferences()
				.get("persistence",
						parent.getProperties().getProperty(
								"selectedPersistence")));
		if (savedExtension) {
			rdbtnYes.setSelected(true);
			switchRadioButton(true);
		} else {
			rdbtnNo.setSelected(true);
			switchRadioButton(false);
		}

		// Synchronization
		JLabel lblDesync = new JLabel(
				I18N.getHtmlText("PanelTiming.desync.text"));
		lblDesync.setBounds(12, 52, 160, 23);
		this.add(lblDesync);

		desyncComboBox = new JComboBox<String>();
		desyncComboBox.setCursor(parent.getCursor());
		desyncComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				I18N.getHtmlText("PanelTiming.left.text"),
				I18N.getHtmlText("PanelTiming.right.text"),
				I18N.getHtmlText("PanelTiming.max.text"),
				I18N.getHtmlText("PanelTiming.min.text") }));
		desyncComboBox.setBounds(80, 75, 220, 20);
		String savedDesync = parent.getPreferences().get("desync",
				parent.getProperties().getProperty("selectedDesyncIndex"));
		if (savedDesync != null) {
			desyncComboBox.setSelectedIndex(Integer.parseInt(savedDesync));
		}
		Object comp = desyncComboBox.getUI().getAccessibleChild(this, 0);
		JPopupMenu popup = (JPopupMenu) comp;
		JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
		scrollPane
				.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(desyncComboBox);

		// Help
		JButton buttonHelpDelay = new JButton(new ImageIcon(
				ClassLoader.getSystemResource("img/help.png")));
		buttonHelpDelay.setBounds(273, 16, 22, 22);
		buttonHelpDelay.setCursor(parent.getCursor());
		buttonHelpDelay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpTimingDialog helpTimingDialog = parent.getHelpTiming();
				if (helpTimingDialog == null) {
					helpTimingDialog = new HelpTimingDialog(parent, true);
				}
				helpTimingDialog.setVisible();
			}
		});

		this.add(buttonHelpDelay);

		// Borders (for debug purposes)
		if (Log.getLevel().equals(Level.FINE)) {
			Border border = BorderFactory.createLineBorder(Color.black);
			lblPersistence.setBorder(border);
			lblDesync.setBorder(border);
			lblSeconds.setBorder(border);
			rdbtnNo.setBorderPainted(true);
			rdbtnYes.setBorderPainted(true);
			progressiveCheckBox.setBorderPainted(true);
			lblDesync.setBorder(border);
		}

	}

	private void switchRadioButton(boolean input) {
		extensionSeconds.setEnabled(input);
		progressiveCheckBox.setEnabled(input);
	}

	public JComboBox<String> getDesyncComboBox() {
		return desyncComboBox;
	}

	public JTextField getExtensionSeconds() {
		return extensionSeconds;
	}

	public JCheckBox getProgressiveCheckBox() {
		return progressiveCheckBox;
	}

	public JRadioButton getRdbtnYes() {
		return rdbtnYes;
	}

	public JRadioButton getRdbtnNo() {
		return rdbtnNo;
	}

}
