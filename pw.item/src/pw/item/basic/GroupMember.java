/*
 *  $Id: GroupMember.java 2013/11/02 15:39:01 masamitsu $
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

package pw.item.basic;

import java.util.UUID;

import pw.core.PWUtilities;
import pw.core.annotation.PWFieldBasicInfo;
import pw.core.annotation.PWItemBasicInfo;
import pw.core.item.PWItem;

/**
 * @author masamitsu
 *
 */
@PWItemBasicInfo(caption = "Group Members", tableName = "GroupMembers")
public class GroupMember extends PWItem {
	
	@PWFieldBasicInfo(caption = "Group UUID", type = "uuid", primary = true)
	private UUID groupUuid;
	
	@PWFieldBasicInfo(caption = "User UUID", type = "uuid", primary = true)
	private UUID userUuid;
	
	@PWFieldBasicInfo(caption = "Role", type = "varchar(100)")
	private String role;

	public UUID getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(UUID groupUuid) {
		this.groupUuid = groupUuid;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	static {
		PWUtilities.prepareTable(GroupMember.class);
	}

}
