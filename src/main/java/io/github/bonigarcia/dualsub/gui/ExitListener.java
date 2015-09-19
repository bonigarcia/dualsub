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

import io.github.bonigarcia.dualsub.srt.SrtUtils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ExitListener.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class ExitListener extends WindowAdapter {

	// Parent
	private DualSub parent;

	public ExitListener(DualSub parent) {
		this.parent = parent;
	}

	@Override
	public void windowClosing(WindowEvent e) {

		// Output panel
		parent.getPreferences().put(
				"charset",
				String.valueOf(parent.getPanelOutput().getCharsetComboBox()
						.getSelectedIndex()));
		parent.getPreferences().put(
				"padding",
				String.valueOf(parent.getPanelOutput().getRdbtnSpace()
						.isSelected()));
		parent.getPreferences().put(
				"separator",
				String.valueOf(parent.getPanelOutput().getRdbtnYes()
						.isSelected()));
		parent.getPreferences()
				.put("separatorCharacter",
						String.valueOf(parent.getPanelOutput().getSeparator()
								.getText()));

		// Player panel
		parent.getPreferences().put("width",
				String.valueOf(parent.getPanelPlayer().getSizePx().getText()));
		parent.getPreferences().put(
				"font",
				(String) parent.getPanelPlayer().getFontComboBox()
						.getSelectedItem());
		parent.getPreferences().put(
				"size",
				String.valueOf(parent.getPanelPlayer().getSizeComboBox()
						.getSelectedIndex()));

		// Timing panel
		parent.getPreferences().put(
				"persistence",
				String.valueOf(parent.getPanelTiming().getRdbtnYes()
						.isSelected()));
		parent.getPreferences().put("seconds",
				parent.getPanelTiming().getExtensionSeconds().getText());
		parent.getPreferences().put(
				"progressive",
				String.valueOf(parent.getPanelTiming().getProgressiveCheckBox()
						.isSelected()));
		parent.getPreferences().put(
				"desync",
				String.valueOf(parent.getPanelTiming().getDesyncComboBox()
						.getSelectedIndex()));

		// Translation panel
		parent.getPreferences().put(
				"translation",
				String.valueOf(parent.getPanelTranslation()
						.getEnableTranslation().isSelected()));
		parent.getPreferences().put(
				"langFrom",
				String.valueOf(((LangItem) parent.getPanelTranslation()
						.getFromComboBox().getSelectedItem()).getId()));
		parent.getPreferences().put(
				"langTo",
				String.valueOf(((LangItem) parent.getPanelTranslation()
						.getToComboBox().getSelectedItem()).getId()));
		parent.getPreferences().put(
				"mergeTranslation",
				String.valueOf(parent.getPanelTranslation().getRdbtnMerged()
						.isSelected()));

		// Output folder
		parent.getPreferences().put("output",
				parent.getOutputFolder().getText());

		// Color
		String leftColor = SrtUtils.getLeftColor() != null ? SrtUtils
				.getLeftColor() : "";
		String rightColor = SrtUtils.getRightColor() != null ? SrtUtils
				.getRightColor() : "";
		parent.getPreferences().put("leftColor", leftColor);
		parent.getPreferences().put("rightColor", rightColor);

		// Layout
		parent.getPreferences().put(
				"horizontal",
				String.valueOf(parent.getPanelOutput().getRdbtnHorizontal()
						.isSelected()));
		System.exit(0);
	}
}
