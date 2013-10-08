/*
 *  $Id: PWStringFieldEditor.java 2013/09/25 5:44:33 masamitsu $
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

package pw.ui.command.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import pw.core.PWError;
import pw.core.PWField;
import pw.core.PWUtilities;
import pw.core.PWPropertyLoader;
import pw.ui.command.PaperWorker;

/**
 * @author masamitsu
 *
 */
public class PWStringFieldEditor extends PWAbstractFieldEditor {

	/**
	 * @param field
	 * @param captionLength
	 */
	public PWStringFieldEditor(PWField field, int captionLength) {
		super(field, captionLength);
	}
	
	public String prompt() {
		String caption = field.getCaption();
		String format = String.format("%%-%ds >> ", captionLength);
		
		// Single Line
		if (!"text".equals(field.getType())) {
			return PaperWorker.prompt(format, caption);
		}
		
		// Multi Line
		PaperWorker.message(format, caption);
		
		try {
			// Create temporary file
			File tmpFile = File.createTempFile(field.getName(), "tmp");
			
			// Get editor
			String editor = PWPropertyLoader.getValue("command", "editor");
			
			// Create process
			ProcessBuilder pb = new ProcessBuilder(editor, tmpFile.getAbsolutePath());
			Process p = pb.start();
			p.waitFor();
			
			// Read temporary file contents
			FileInputStream stream = new FileInputStream(tmpFile);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(PWUtilities.LINE_SEPARATOR);
				buffer.append(line);
			}
			return buffer.length() == 0 ? null : buffer.substring(PWUtilities.LINE_SEPARATOR.length());
			
		} catch (IOException e) {
			throw new PWError(e, e.getMessage());
		} catch (InterruptedException e) {
			throw new PWError(e, e.getMessage());
		}
	}
}
