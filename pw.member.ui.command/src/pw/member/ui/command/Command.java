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

package pw.member.ui.command;

import java.util.ArrayList;
import java.util.List;

import pw.core.PWError;
import pw.core.PWWarning;
import pw.core.ui.command.PWAction;
import pw.core.ui.command.PWCommand;
import pw.member.core.Member;
import pw.member.core.MemberController;

public class Command extends PWCommand<Member, MemberController> {
	public Command() throws PWError, PWWarning {
		super();
	}
	
	protected String getName() {
		return "member";
	}

	@Override
	protected List<PWAction<Member, MemberController>> getActions() {
		List<PWAction<Member, MemberController>> actions = new ArrayList<PWAction<Member, MemberController>>();
		actions.add(new ListAction());
		actions.add(new AddAction());
		actions.add(new DetailAction());
		actions.add(new UpdateAction());
		actions.add(new DeleteAction());
		return actions;
	}

	@Override
	protected MemberController createController() throws PWError, PWWarning {
		return new MemberController();
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWCommand#getDescription()
	 */
	@Override
	protected String getDescription() {
		return String.format("Maintenance tool for %s master.", getName());
	}
}
