/*
 *  $Id: PWSingleSelector.java 2013/09/24 23:04:48 masamitsu $
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

package paperworker.core.ui.command;

import java.util.ArrayList;
import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWItem;

/**
 * @author masamitsu
 *
 */
public class PWSingleSelector<TItem extends PWItem, TLabel extends PWLabel<TItem>> implements PWSelector {

	private List<TLabel> labels = new ArrayList<TLabel>();
	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWSelector#prompt(java.lang.String)
	 */
	@Override
	public String prompt(String prompt) throws PWError {
		int size = labels.size();
		int decimal = String.format("%d", size).length();
		String format = String.format("  %%%dd. %%s", decimal);
		
		PaperWorker.message("Select a number.");
		for (int i = 0; i < size; i++) {
			TLabel label = labels.get(i);
			PaperWorker.message(format, i, label.getText());
		}
		
		int index = -1;
		while (true) {
			String input = PaperWorker.prompt(prompt);
			index = Integer.valueOf(input);
			if (0 <= index && index < size) {
				break;
			}
			PaperWorker.message("* Input number from 0 to %d.", size - 1);
		}
		
		return labels.get(index).getId();
	}
	
	public void add(TLabel label) {
		labels.add(label);
	}

}
