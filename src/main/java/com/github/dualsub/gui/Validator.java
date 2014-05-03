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

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.github.dualsub.srt.SrtUtils;
import com.github.dualsub.util.I18N;

/**
 * Validator.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Validator {

	public static void validateSrt(File file, JList<File> list) {
		if (file != null) {
			if (file.getName().toLowerCase().endsWith(SrtUtils.SRT_EXT)) {
				((DefaultListModel<File>) list.getModel()).addElement(file);
			} else {
				Alert.error(file.getName()
						+ I18N.getHtmlText("Validator.notsrt.alert"));
			}
		}
	}

	public static void deleteSelected(JList<File> list) {
		int[] selected = isSelected(list);
		if (selected.length > 0) {
			for (int i = selected.length - 1; i > -1; i--) {
				((DefaultListModel<File>) list.getModel()).remove(selected[i]);
			}
		}
	}

	public static int[] isSelected(JList<File> list) {
		int[] selected = list.getSelectedIndices();
		if (selected.length < 1) {
			Alert.error(I18N.getHtmlText("Validator.selectOne.alert"));
		}
		return selected;
	}

	public static void goUp(JList<File> list) {
		int[] selected = isSelected(list);
		if (selected.length > 1) {
			Alert.error(I18N.getHtmlText("Validator.onlyOne.alert"));
			return;
		} else if (selected.length == 1 && selected[0] > 0) {
			File removed = ((DefaultListModel<File>) list.getModel())
					.remove(selected[0]);
			File before = ((DefaultListModel<File>) list.getModel())
					.remove(selected[0] - 1);
			((DefaultListModel<File>) list.getModel()).add(selected[0] - 1,
					removed);
			((DefaultListModel<File>) list.getModel()).add(selected[0], before);
			list.setSelectedIndex(selected[0] - 1);
		}
	}

	public static void goDown(JList<File> list) {
		int[] selected = isSelected(list);
		if (selected.length > 1) {
			Alert.error(I18N.getHtmlText("Validator.onlyOne.alert"));
			return;
		} else if (selected.length == 1
				&& selected[0] < list.getModel().getSize() - 1) {
			File after = ((DefaultListModel<File>) list.getModel())
					.remove(selected[0] + 1);
			File removed = ((DefaultListModel<File>) list.getModel())
					.remove(selected[0]);
			((DefaultListModel<File>) list.getModel()).add(selected[0], after);
			((DefaultListModel<File>) list.getModel()).add(selected[0] + 1,
					removed);
			list.setSelectedIndex(selected[0] + 1);
		}
	}
}
