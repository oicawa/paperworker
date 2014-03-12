/*
 *  $Id: PWPrintAction.java 2013/10/09 0:47:32 masamitsu $
 *
 *  ===============================================================================
 *
 *   Copyright (C) 2013  Masamitsu Oikawa  <oicawa@gmail.com>
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 *  ===============================================================================
 */

package pw.action.print;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

import pw.core.PWAction;
import pw.core.PWError;

/**
 * @author masamitsu
 *
 */
public class PWPrintAction extends PWAction {

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#parseArguments(java.lang.String[])
	 */
	@Override
	protected void parseSettingParameters(String[] arguments) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pw.core.action.PWAction#run(java.lang.Object[])
	 */
	@Override
	public Object run(Object... objects) {
		if (objects.length != 4) {
			throw new PWError("[PWPrintAction] Too few arguments.(require 4 arguments)");
		}
		
		if (!(objects[0] instanceof DocFlavor)) {
			throw new PWError("[PWPrintAction] 1st argument is not DocFlavor");
		}
		
		if (!(objects[1] instanceof String)) {
			throw new PWError("[PWPrintAction] 2st argument is not String");
		}
		
		if (!(objects[2] instanceof Integer)) {
			throw new PWError("[PWPrintAction] 2st argument is not Integer");
		}
		
		if (!(objects[3] instanceof MediaSizeName)) {
			throw new PWError("[PWPrintAction] 2st argument is not MediaSizeName");
		}
		
		print((DocFlavor)objects[0], (String)objects[1], (Integer)objects[2], (MediaSizeName)objects[3]);
		
		return null;
	}
	
	public void print(DocFlavor docFlavor, String path, int documentCount, MediaSizeName mediaSizeName) {
		
		File file = new File(path);
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new PWError(e, "The File is not found. [%s]", path);
		}
		Doc doc = new SimpleDoc(stream, docFlavor, null);

		// Set print request attributes
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		printRequestAttributeSet.add(new Copies(documentCount));// Document Count
		printRequestAttributeSet.add(mediaSizeName);// Paper Size
		//printRequestAttributeSet.add(Chromaticity.COLOR);
		//printRequestAttributeSet.add(MediaSize.Other.JAPANESE_POSTCARD);
		//printRequestAttributeSet.add(MediaSize.getMediaSizeForName(mediaSizeName));
		//printRequestAttributeSet.add(MediaTray.TOP);

		// Get the print service list which supports the selected DocFlavor and PrintRequestAttributes
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(docFlavor, printRequestAttributeSet);

		//
		PrinterJob.getPrinterJob().pageDialog(printRequestAttributeSet);
		
		// Show Print Dialog and Get Selected Print service
		PrintService printService = ServiceUI.printDialog(null, 100, 100, printServices, printServices[0], docFlavor, printRequestAttributeSet);
		if (printService == null) {
			return;
		}
		
		
		
		
		// Do print
		try {
			DocPrintJob printJob = printService.createPrintJob();
			printJob.print(doc, printRequestAttributeSet);
		} catch (PrintException e) {
			throw new PWError(e, "Print Failed. [%s]", path);
		}				
		return;
	}

}
