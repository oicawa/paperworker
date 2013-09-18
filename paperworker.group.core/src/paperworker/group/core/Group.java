package paperworker.group.core;

import paperworker.core.PWError;
import paperworker.core.PWItem;
import paperworker.core.annotation.PWFieldBasicInfo;
import paperworker.core.annotation.PWItemBasicInfo;
import paperworker.master.core.MasterItem;

@PWItemBasicInfo(caption = "Groups", tableName = "Groups")
public class Group extends MasterItem {

	@PWFieldBasicInfo(caption = "Group ID", type = "varchar(100)", primary = true)
	private String groupId;

	@PWFieldBasicInfo(caption = "Parent Group ID", type = "varchar(100)")
	private String parentGroupId;
	
	@PWFieldBasicInfo(caption = "Group Name", type = "varchar(100)")
	private String name;
	
	@PWFieldBasicInfo(caption = "Group Short Name", type = "varchar(100)")
	private String shortName;
	
	@PWFieldBasicInfo(caption = "Group Leader ID", type = "varchar(100)")
	private String leaderId;
	
	@PWFieldBasicInfo(caption = "Group Subleader ID", type = "varchar(100)")
	private String subleaderId;
	
	@PWFieldBasicInfo(caption = "Division", type = "varchar(100)")
	private GroupDivision division;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getSubleaderId() {
		return subleaderId;
	}

	public void setSubleaderId(String subleaderId) {
		this.subleaderId = subleaderId;
	}

	public GroupDivision getDivision() {
		return division;
	}

	public void setDivision(GroupDivision division) {
		this.division = division;
	}
	
	@Override
	public String getId() {
		return groupId;
	}

	@Override
	public void setId(String itemId) {
		groupId = itemId;
	}

	@Override
	public Object getValue(String fieldName) throws PWError {
		return PWItem.getValue(this, fieldName);
	}

	@Override
	public void setValue(String fieldName, Object value) throws PWError {
		PWItem.setValue(this, fieldName, value);
	}

}
