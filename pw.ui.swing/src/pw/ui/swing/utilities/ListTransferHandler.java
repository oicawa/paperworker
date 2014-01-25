/*
 *  $Id: ListTransferHandler.java 2013/12/08 16:05:16 masamitsu $
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

package pw.ui.swing.utilities;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;

/**
 * @author masamitsu
 *
 */
public class ListTransferHandler extends StringTransferHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 522765330951460934L;
	private int s_index = -1;
    private int addIndex = -1;

	/* (non-Javadoc)
	 * @see pw.ui.swing.utilities.StringTransferHandler#exportString(javax.swing.JComponent)
	 */
	@Override
	protected String exportString(JComponent c) {
        JList list = (JList)c;
        s_index = list.getSelectedIndex();
        return (String)list.getSelectedValue();
	}

	/* (non-Javadoc)
	 * @see pw.ui.swing.utilities.StringTransferHandler#importString(javax.swing.JComponent, java.lang.String)
	 */
	@Override
	protected void importString(JComponent c, String str) {
        JList target = (JList)c;
        DefaultListModel listModel = (DefaultListModel)target.getModel();
        int index = target.getSelectedIndex();

        int max = listModel.getSize();
        if (index < 0) {
            index = max;
        } else {
            index++;
            if (index > max) {
                index = max;
            }
        }
        addIndex = index;
        listModel.add(index++, str);
	}

	/* (non-Javadoc)
	 * @see pw.ui.swing.utilities.StringTransferHandler#cleanup(javax.swing.JComponent, boolean)
	 */
	@Override
	protected void cleanup(JComponent c, boolean remove) {
        if (remove && s_index != -1 && addIndex != -1) {
            JList source = (JList) c;
            DefaultListModel model = (DefaultListModel) source.getModel();
            if (s_index > addIndex) {
                s_index += 1;
            }
            model.remove(s_index);
            s_index = -1;
            addIndex = -1;
        }
	}

}
