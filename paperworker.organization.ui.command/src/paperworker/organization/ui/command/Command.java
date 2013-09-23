/*
 *  $Id: Command.java 2013/09/23 15:02:33 masamitsu $
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

package paperworker.organization.ui.command;

import java.util.ArrayList;
import java.util.List;

import paperworker.core.PWError;
import paperworker.core.PWWarning;
import paperworker.core.ui.command.PWAction;
import paperworker.core.ui.command.PWCommand;
import paperworker.organization.core.Organization;
import paperworker.organization.core.OrganizationController;

/**
 * @author masamitsu
 *
 */
public class Command extends PWCommand<Organization, OrganizationController> {

	/**
	 * @throws PWError
	 * @throws PWWarning
	 */
	public Command() throws PWError, PWWarning {
		super();
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWCommand#getName()
	 */
	@Override
	protected String getName() {
		return "organization";
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWCommand#createController()
	 */
	@Override
	protected OrganizationController createController() throws PWError, PWWarning {
		return new OrganizationController();
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWCommand#getActions()
	 */
	@Override
	protected List<PWAction<Organization, OrganizationController>> getActions() {
		List<PWAction<Organization, OrganizationController>> actions = new ArrayList<PWAction<Organization, OrganizationController>>();
		actions.add(new ListAction());
		actions.add(new AddAction());
		actions.add(new DetailAction());
		actions.add(new UpdateAction());
		actions.add(new DeleteAction());
		return actions;
	}

	/* (non-Javadoc)
	 * @see paperworker.core.ui.command.PWCommand#getDescription()
	 */
	@Override
	protected String getDescription() {
		return String.format("Maintenance tool for %s master.", getName());
	}

}
