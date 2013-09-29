/*
 *  $Id: Command.java 2013/09/21 3:03:36 Masamitsu Oikawa $
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

package pw.holidaydivision.ui.command;

import java.util.ArrayList;
import java.util.List;

import pw.core.ui.command.PWCommand;
import pw.core.ui.command.operation.PWOperation;
import pw.holidaydivision.core.HolidayDivision;
import pw.holidaydivision.core.HolidayDivisionController;

public class Command extends PWCommand<HolidayDivision, HolidayDivisionController> {
	public Command() {
		super();
	}
	
	protected String getName() {
		return "holidaydivision";
	}

	@Override
	protected List<PWOperation<HolidayDivision, HolidayDivisionController>> getActions() {
		List<PWOperation<HolidayDivision, HolidayDivisionController>> actions = new ArrayList<PWOperation<HolidayDivision, HolidayDivisionController>>();
		actions.add(new ListAction());
		actions.add(new AddAction());
		actions.add(new DetailAction());
		actions.add(new UpdateAction());
		actions.add(new DeleteAction());
		return actions;
	}

	@Override
	protected HolidayDivisionController createController() {
		return new HolidayDivisionController();
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWCommand#getDescription()
	 */
	@Override
	protected String getDescription() {
		return String.format("Maintenance tool for %s master.", getName());
	}
}
