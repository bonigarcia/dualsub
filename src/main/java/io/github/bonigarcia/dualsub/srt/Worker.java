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
package io.github.bonigarcia.dualsub.srt;

import io.github.bonigarcia.dualsub.gui.Alert;
import io.github.bonigarcia.dualsub.gui.CharsetItem;
import io.github.bonigarcia.dualsub.gui.DualSub;
import io.github.bonigarcia.dualsub.gui.ExceptionDialog;
import io.github.bonigarcia.dualsub.gui.LangItem;
import io.github.bonigarcia.dualsub.translate.TranslationCanceled;
import io.github.bonigarcia.dualsub.util.I18N;

import java.io.File;

import javax.swing.ListModel;
import javax.swing.SwingWorker;

/**
 * Worker.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class Worker extends SwingWorker<Integer, String> {

	// Parent
	private DualSub parent;

	public Worker(DualSub parent) {
		this.parent = parent;
	}

	@Override
	protected Integer doInBackground() {
		// Core is here
		ListModel<File> leftSrtModel = parent.getLeftSubtitles().getModel();
		ListModel<File> rightSrtModel = parent.getRightSubtitles().getModel();

		boolean translate = parent.getPanelTranslation().getEnableTranslation()
				.isSelected();
		boolean merge = parent.getPanelTranslation().getRdbtnMerged()
				.isSelected();

		// Input validations (left and right list, output folder)
		if (leftSrtModel.getSize() == 0 && rightSrtModel.getSize() == 0) {
			Alert.error(I18N.getHtmlText("MergeButton.selectFile.error"));
			return 0;
		}
		if (!translate && leftSrtModel.getSize() != rightSrtModel.getSize()) {
			Alert.error(I18N.getHtmlText("MergeButton.listSize.error"));
			return 0;
		}
		if (translate
				&& (leftSrtModel.getSize() != 0 && rightSrtModel.getSize() != 0)) {
			Alert.error(I18N.getHtmlText("MergeButton.translateSize.error"));
			return 0;
		}
		File outputFolderFile = new File(parent.getOutputFolder().getText());
		if (!outputFolderFile.exists() || !outputFolderFile.isDirectory()) {
			Alert.error(I18N.getHtmlText("MergeButton.outputFolder.error"));
			return 0;
		}

		parent.getProgressBar().setVisible(true);

		Merger merger = new Merger(outputFolderFile.getAbsolutePath(), parent
				.getPanelTiming().getRdbtnYes().isSelected(),
				Integer.parseInt(parent.getPanelTiming().getExtensionSeconds()
						.getText()), parent.getPanelTiming()
						.getProgressiveCheckBox().isSelected(),
				parent.getProperties(), ((CharsetItem) parent.getPanelOutput()
						.getCharsetComboBox().getSelectedItem()).getId(),
				parent.getPanelTiming().getDesyncComboBox().getSelectedIndex(),
				translate, merge);

		SrtUtils.init(
				parent.getPanelPlayer().getSizePx().getText(),
				parent.getPanelPlayer().getFontComboBox().getSelectedItem()
						.toString(),
				Integer.parseInt((String) parent.getPanelPlayer()
						.getSizeComboBox().getSelectedItem()), parent
						.getPanelOutput().getRdbtnSpace().isSelected(), parent
						.getPanelOutput().getRdbtnYes().isSelected(), parent
						.getPanelOutput().getSeparator().getText(),
				Integer.parseInt(parent.getProperties().getProperty("guard")),
				parent.getPanelOutput().getRdbtnHorizontal().isSelected(),
				SrtUtils.getLeftColor(), SrtUtils.getRightColor());

		Srt leftSrt = null, rightSrt = null;
		String mergedFileName = null;
		DualSrt dualSrt;
		boolean error = false;

		for (int i = 0; i < Math.max(leftSrtModel.getSize(),
				rightSrtModel.getSize()); i++) {
			try {
				if (i < leftSrtModel.getSize()) {
					leftSrt = new Srt(leftSrtModel.getElementAt(i)
							.getAbsolutePath());
				}
				if (i < rightSrtModel.getSize()) {
					rightSrt = new Srt(rightSrtModel.getElementAt(i)
							.getAbsolutePath());
				}

				if (translate) {
					String fromLang = ((LangItem) parent.getPanelTranslation()
							.getFromComboBox().getSelectedItem()).getId();
					String toLang = ((LangItem) parent.getPanelTranslation()
							.getToComboBox().getSelectedItem()).getId();
					if (rightSrt != null && leftSrt == null) {
						leftSrt = new Srt(rightSrt, fromLang, toLang,
								rightSrt.getCharset(), parent);
						if (!merge) {
							rightSrt.resetSubtitles();
						}
					} else if (leftSrt != null && rightSrt == null) {
						// line that translate
						rightSrt = new Srt(leftSrt, fromLang, toLang,
								leftSrt.getCharset(), parent);
						if (!merge) {
							leftSrt.resetSubtitles();
						}
					}
				}

				mergedFileName = merger.getMergedFileName(leftSrt, rightSrt);
				dualSrt = merger.mergeSubs(leftSrt, rightSrt);

				dualSrt.writeSrt(mergedFileName, merger.getCharset(),
						translate, merge);

				leftSrt = null;
				rightSrt = null;

			} catch (TranslationCanceled tc) {
				error = true;
				break;
			} catch (Throwable exc) {
				// exc.printStackTrace();
				ExceptionDialog exceptionDialog = parent.getException();
				if (exceptionDialog == null) {
					exceptionDialog = new ExceptionDialog(parent, true, exc);
					parent.setException(exceptionDialog);
				}
				exceptionDialog.setVisible();
				error = true;
				break;
			}
		}

		parent.getProgressBar().setVisible(false);

		if (!error) {
			Alert.info(I18N.getHtmlText("MergeButton.merged.ok"));
		}

		return 0;
	}
}
