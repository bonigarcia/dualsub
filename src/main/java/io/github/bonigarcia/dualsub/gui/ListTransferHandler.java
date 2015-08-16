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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 * ListTransferHandler.
 * 
 * @author Boni Garcia (boni.gg@gmail.com)
 * @since 1.0.0
 */
public class ListTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 1L;

	private JList<File> list;

	public ListTransferHandler(JList<File> list) {
		this.list = list;
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport info) {
		if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
			return false;
		}
		if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}
		Transferable t = info.getTransferable();
		List<File> data;
		try {
			data = (List<File>) t
					.getTransferData(DataFlavor.javaFileListFlavor);
		} catch (Exception e) {
			return false;
		}
		for (File object : data) {
			Validator.validateSrt(object, list);
		}
		return true;
	}
}