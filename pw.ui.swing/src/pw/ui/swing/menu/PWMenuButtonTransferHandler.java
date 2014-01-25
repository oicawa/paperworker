/*
 *  $Id: PWMenuButtonTransferHandler.java 2013/12/08 17:00:31 masamitsu $
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

package pw.ui.swing.menu;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * @author masamitsu
 *
 */
public class PWMenuButtonTransferHandler extends TransferHandler {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2638913018326103142L;
	
    public boolean importData(JComponent c, Transferable t) {
    	return canImport(c, t.getTransferDataFlavors());
//        if (canImport(c, t.getTransferDataFlavors())) {
//            try {
//                String str = (String)t.getTransferData(DataFlavor.stringFlavor);
//                importString(c, str);
//                return true;
//            } catch (UnsupportedFlavorException ufe) {
//            } catch (IOException ioe) {
//            }
//        }

//        return false;
    }

	public boolean canImport(JComponent c, DataFlavor[] flavors) {
//        for (int i = 0; i < flavors.length; i++) {
//            if (DataFlavor.stringFlavor.equals(flavors[i])) {
//                return true;
//            }
//        }
        return true;
    }
}
